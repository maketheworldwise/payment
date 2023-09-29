package org.service.banking.application.service;

import org.jetbrains.annotations.NotNull;
import org.service.banking.adapter.out.external.bank.BankAccount;
import org.service.banking.adapter.out.external.bank.GetBankAccountRequest;
import org.service.banking.adapter.out.persistence.RegisteredBankAccountJpaEntity;
import org.service.banking.adapter.out.persistence.RegisteredBankAccountMapper;
import org.service.banking.application.port.in.RegisterBankAccountCommand;
import org.service.banking.application.port.in.RegisterBankAccountUsecase;
import org.service.banking.application.port.out.RegisteredBankAccountPort;
import org.service.banking.application.port.out.RequestBankAccountInfoPort;
import org.service.banking.domain.RegisterBankAccount;
import org.service.common.Usecase;

import lombok.RequiredArgsConstructor;

@Usecase
@RequiredArgsConstructor
public class RegisterBankAccountService implements RegisterBankAccountUsecase {

	private final RequestBankAccountInfoPort requestBankAccountInfoPort;

	private final RegisteredBankAccountPort registeredBankAccountPort;

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

		isValidMembershipId();

		BankAccount accountInfo = getBankAccount(command);

		RegisteredBankAccountJpaEntity jpaEntity = registeredBankAccountPort.createRegisteredBankAccount(
			new RegisterBankAccount.MembershipId(command.getMembershipId()),
			new RegisterBankAccount.BankName(accountInfo.getBankName()),
			new RegisterBankAccount.BankAccountNumber(accountInfo.getBankAccountNumber()),
			new RegisterBankAccount.LinkedStatusIsValid(accountInfo.isValid())
		);

		return registeredBankAccountMapper.toDomain(jpaEntity);
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

	private static void isValidMembershipId() {
		boolean membershipIsValid = true;
		if (!membershipIsValid) {
			throw new RuntimeException("invalid membership id");
		}
	}
}
