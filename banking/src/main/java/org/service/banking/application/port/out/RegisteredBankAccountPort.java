package org.service.banking.application.port.out;

import org.service.banking.adapter.out.persistence.RegisteredBankAccountJpaEntity;
import org.service.banking.domain.RegisterBankAccount;

public interface RegisteredBankAccountPort {
	RegisteredBankAccountJpaEntity createRegisteredBankAccount(
		RegisterBankAccount.MembershipId membershipId,
		RegisterBankAccount.BankName bankName,
		RegisterBankAccount.BankAccountNumber bankAccountNumber,
		RegisterBankAccount.LinkedStatusIsValid linkedStatusIsValid
	);
}
