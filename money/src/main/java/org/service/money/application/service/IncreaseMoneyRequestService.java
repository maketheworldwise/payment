package org.service.money.application.service;

import java.util.UUID;

import org.service.common.Usecase;
import org.service.money.adapter.out.persistence.MemberMoneyJpaEntity;
import org.service.money.adapter.out.persistence.MoneyChangingRequestJpaEntity;
import org.service.money.adapter.out.persistence.MoneyChangingRequestMapper;
import org.service.money.application.port.in.IncreaseMoneyRequestCommand;
import org.service.money.application.port.in.IncreaseMoneyRequestUsecase;
import org.service.money.application.port.out.MoneyChangingRequestPort;
import org.service.money.domain.MemberMoney;
import org.service.money.domain.MoneyChangingRequest;

import lombok.RequiredArgsConstructor;

@Usecase
@RequiredArgsConstructor
public class IncreaseMoneyRequestService implements IncreaseMoneyRequestUsecase {

	private final MoneyChangingRequestPort moneyChangingRequestPort;

	private final MoneyChangingRequestMapper moneyChangingRequestMapper;

	@Override
	public MoneyChangingRequest increaseMoneyRequest(IncreaseMoneyRequestCommand command) {

		/*
		  <biz logic>

		  0. check membership (membership service)
		  1. check if membership bank account available (banking service)
		  2. check if membership bank account has enough money (banking service)
		  3. check if corporate bank account available (banking service)
		  4. create money changing request in request status (money service)
		  5. record increase request action to money request history (money service)
		  6. request firm banking service (membership bank account to corporate bank account) (banking service)
		  7. update money change request status according to the result of firm banking (money service)
		  8. increase members money

		 */

		// 4.
		MoneyChangingRequestJpaEntity requestedEntity = moneyChangingRequestPort.createMoneyChangingRequest(
			new MoneyChangingRequest.TargetMembershipId(command.getTargetMembershipId()),
			new MoneyChangingRequest.MoneyChangingType(0), // increase
			new MoneyChangingRequest.MoneyChangingAmount(command.getAmount()),
			new MoneyChangingRequest.MoneyChangingStatus(0), // requested
			new MoneyChangingRequest.Uuid(UUID.randomUUID())
		);

		// 7.
		requestedEntity.setChangingMoneyStatus(1); // succeeded
		MoneyChangingRequestJpaEntity jpaEntity = moneyChangingRequestPort.modifyMoneyChangingRequest(requestedEntity);

		// 8.
		MemberMoneyJpaEntity memberMoneyJpaEntity = moneyChangingRequestPort.increaseMemberMoney(
			new MemberMoney.MembershipId(command.getTargetMembershipId()),
			command.getAmount()
		);
		if(memberMoneyJpaEntity == null) {
			throw new RuntimeException("member money increase failed");
		}

		return moneyChangingRequestMapper.toDomain(jpaEntity);
	}
}
