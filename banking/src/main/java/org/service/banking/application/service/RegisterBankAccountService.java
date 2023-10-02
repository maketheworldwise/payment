package org.service.banking.application.service;

import org.jetbrains.annotations.NotNull;
import org.service.banking.adapter.out.external.bank.BankAccount;
import org.service.banking.adapter.out.external.bank.GetBankAccountRequest;
import org.service.banking.adapter.out.persistence.RegisteredBankAccountJpaEntity;
import org.service.banking.adapter.out.persistence.RegisteredBankAccountMapper;
import org.service.banking.application.port.in.RegisterBankAccountCommand;
import org.service.banking.application.port.in.RegisterBankAccountUsecase;
import org.service.banking.application.port.out.GetMembershipPort;
import org.service.banking.application.port.out.MembershipStatus;
import org.service.banking.application.port.out.RegisteredBankAccountPort;
import org.service.banking.application.port.out.RequestBankAccountInfoPort;
import org.service.banking.application.query.ValidateRegisteredBankAccountQuery;
import org.service.banking.domain.RegisterBankAccount;
import org.service.common.Usecase;

import lombok.RequiredArgsConstructor;

@Usecase
@RequiredArgsConstructor
public class RegisterBankAccountService implements RegisterBankAccountUsecase {

	private final RequestBankAccountInfoPort requestBankAccountInfoPort;

	private final RegisteredBankAccountPort registeredBankAccountPort;

	private final GetMembershipPort getMembershipPort;

	private final ValidateRegisteredBankAccountQuery validateRegisteredBankAccountQuery;

	private final RegisteredBankAccountMapper registeredBankAccountMapper;

	@Override
	public RegisterBankAccount registerBankAccount(RegisterBankAccountCommand command) {

		/*
		  <biz logic>

		  0. check membershipId (skip)
		  1. check if bank account available
		  		1-1. if not, throw exception
		  2. check if register available
		  		2-1. if not, throw exception
		 */

		isValidMembershipId(command.getMembershipId());

		BankAccount accountInfo = getBankAccount(command);

		isRegisterBankAccountValid(accountInfo);

		RegisteredBankAccountJpaEntity jpaEntity = registeredBankAccountPort.createRegisteredBankAccount(
			new RegisterBankAccount.MembershipId(command.getMembershipId()),
			new RegisterBankAccount.BankName(accountInfo.getBankName()),
			new RegisterBankAccount.BankAccountNumber(accountInfo.getBankAccountNumber()),
			new RegisterBankAccount.LinkedStatusIsValid(accountInfo.isValid())
		);

		return registeredBankAccountMapper.toDomain(jpaEntity);
	}

	private void isValidMembershipId(String membershipId) {
		MembershipStatus membershipStatus = getMembershipPort.getMembership(membershipId);
		if (!membershipStatus.isValid()) {
			throw new RuntimeException("invalid membership id");
		}
	}

	private void isRegisterBankAccountValid(BankAccount accountInfo) {
		boolean isRegisteredBankAccountExists = validateRegisteredBankAccountQuery.isValidRegisteredBankAccount(
			accountInfo.getBankName(),
			accountInfo.getBankAccountNumber()
		);
		if(isRegisteredBankAccountExists) {
			throw new RuntimeException("already exists registered bank account");
		}
	}

	@NotNull
	private BankAccount getBankAccount(RegisterBankAccountCommand command) {
		BankAccount accountInfo = requestBankAccountInfoPort.getBankAccountInfo(new GetBankAccountRequest(
			command.getBankName(),
			command.getBankAccountNumber()
		));
		if (!accountInfo.isValid()) {
			throw new RuntimeException("invalid external bank account");
		}
		return accountInfo;
	}
}
