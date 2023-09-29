package org.service.banking.adapter.out.persistence;

import org.service.banking.domain.RegisterBankAccount;
import org.springframework.stereotype.Component;

@Component
public class RegisteredBankAccountMapper {

	public RegisterBankAccount toDomain(RegisteredBankAccountJpaEntity entity) {
		return RegisterBankAccount.generateRegisterBankAccount(
			new RegisterBankAccount.RegisterBankAccountId(entity.getRegisteredBankAccountId() + ""),
			new RegisterBankAccount.MembershipId(entity.getMembershipId()),
			new RegisterBankAccount.BankName(entity.getBankName()),
			new RegisterBankAccount.BankAccountNumber(entity.getBankAccountNumber()),
			new RegisterBankAccount.LinkedStatusIsValid(entity.isLinkedStatusIsValid())
		);
	}
}
