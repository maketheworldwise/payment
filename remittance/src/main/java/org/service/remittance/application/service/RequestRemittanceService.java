package org.service.remittance.application.service;

import org.service.common.Usecase;
import org.service.remittance.adapter.out.persistence.RemittanceRequestJpaEntity;
import org.service.remittance.adapter.out.persistence.RemittanceRequestMapper;
import org.service.remittance.application.port.out.banking.BankingPort;
import org.service.remittance.application.port.out.membership.MembershipStatus;
import org.service.remittance.application.port.out.money.MoneyInfo;
import org.service.remittance.application.port.out.money.MoneyPort;
import org.service.remittance.application.port.in.RequestRemittanceCommand;
import org.service.remittance.application.port.in.RequestRemittanceUsecase;
import org.service.remittance.application.port.out.RequestRemittancePort;
import org.service.remittance.application.port.out.membership.MembershipPort;
import org.service.remittance.domain.RemittanceRequest;

import lombok.RequiredArgsConstructor;

@Usecase
@RequiredArgsConstructor
public class RequestRemittanceService implements RequestRemittanceUsecase {

	private final RequestRemittancePort requestRemittancePort;

	private final RemittanceRequestMapper remittanceRequestMapper;

	private final MembershipPort membershipPort;

	private final MoneyPort moneyPort;

	private final BankingPort bankingPort;

	@Override
	public RemittanceRequest requestRemittance(RequestRemittanceCommand command) {

		/*
		  <biz logic>

		  0. record remittance request history in request status
		  1. check membershipId (from, to)
		  2. check from membership bank account
		  		2-1. if account has not enough money than request recharge
		  3. branch to each process
		  		3-1. member to member: from member money decrease, to member money  increase
		  		3-2. member to external bank: check if external bank account valid, then send money from corporate account to external to membership account
		  4. record remittance request history in succeeded status

		 */

		// 0.
		RemittanceRequestJpaEntity entity = requestRemittancePort.createRemittanceRequestHistory(
			new RemittanceRequest.FromMembershipId(command.getFromMembershipId()),
			new RemittanceRequest.ToMembershipId(command.getToMembershipId()),
			new RemittanceRequest.ToBankName(command.getToBankName()),
			new RemittanceRequest.ToBankAccountNumber(command.getToBankAccountNumber()),
			new RemittanceRequest.RemittanceType(command.getRemittanceType()),
			new RemittanceRequest.MoneyAmount(command.getMoneyAmount()),
			new RemittanceRequest.Status("request")
		);

		// 1.
		MembershipStatus fromMembershipStatus = membershipPort.getMembershipStatus(command.getToMembershipId());
		if (!fromMembershipStatus.isValid()) {
			throw new RuntimeException("invalid membership (from)");
		}
		if (command.getToMembershipId() != null) {
			MembershipStatus toMembershipStatus = membershipPort.getMembershipStatus(command.getToMembershipId());
			if (!toMembershipStatus.isValid()) {
				throw new RuntimeException("invalid membership (to)");
			}
		}

		// 2.
		MoneyInfo moneyInfo = moneyPort.getMoneyInfo(command.getToMembershipId());
		if (moneyInfo.getBalance() < command.getMoneyAmount()) {
			// not enough money in bank
			// needs recharging request

			int rechargeAmount = (int)Math.ceil(command.getMoneyAmount() - moneyInfo.getBalance() / 10000.0) * 10000;
			boolean moneyResult = moneyPort.requestMoneyRecharging(command.getFromMembershipId(), rechargeAmount);
			if (!moneyResult) {
				throw new RuntimeException("money recharging failed");
			}
		}

		// 3.
		if (command.getRemittanceType() == 0) {
			// 3-1.
			boolean decreaseResult = moneyPort.requestMoneyDecrease(
				command.getFromMembershipId(),
				command.getMoneyAmount()
			);
			boolean increaseResult = moneyPort.requestMoneyIncrease(
				command.getToMembershipId(),
				command.getMoneyAmount()
			);
			if (!decreaseResult || !increaseResult) {
				throw new RuntimeException("money decrease or increase failed");
			}
		}
		if (command.getRemittanceType() == 1) {
			// 3-2.
			boolean decreaseResult = moneyPort.requestMoneyDecrease(
				command.getFromMembershipId(),
				command.getMoneyAmount()
			);
			boolean firmBankingResult = bankingPort.requestFirmBanking(
				command.getToBankName(),
				command.getToBankAccountNumber(), command.getMoneyAmount()
			);
			if (!decreaseResult || !firmBankingResult) {
				throw new RuntimeException("money decrease or firm banking request failed");
			}
		}

		// 4.
		entity.setStatus("success");

		boolean result = requestRemittancePort.saveRemittanceRequestHistory(entity);
		if (!result) {
			throw new RuntimeException("remittance request history status change failed");
		}

		return remittanceRequestMapper.toDomain(entity);
	}
}
