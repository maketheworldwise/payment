package org.service.banking.adapter.out.persistence;

import org.service.banking.application.port.out.RegisteredBankAccountPort;
import org.service.banking.domain.RegisterBankAccount;
import org.service.common.PersistenceAdapter;

import lombok.RequiredArgsConstructor;

@PersistenceAdapter
@RequiredArgsConstructor
public class RegisteredBankAccountPersistenceAdapter implements RegisteredBankAccountPort {

	private final SpringDataRegisteredBankAccountRepository registeredBankAccountRepository;

	public RegisteredBankAccountJpaEntity createRegisteredBankAccount(
		RegisterBankAccount.MembershipId membershipId,
		RegisterBankAccount.BankName bankName,
		RegisterBankAccount.BankAccountNumber bankAccountNumber,
		RegisterBankAccount.LinkedStatusIsValid linkedStatusIsValid
	) {
		return this.registeredBankAccountRepository.save(
			new RegisteredBankAccountJpaEntity(
				membershipId.getMembershipId(),
				bankName.getBankName(),
				bankAccountNumber.getBankAccountNumber(),
				linkedStatusIsValid.isLinkedStatusIsValid()
			)
		);
	}
}
