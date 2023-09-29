package org.service.banking.application.query;

import org.service.banking.adapter.out.persistence.SpringDataRegisteredBankAccountQueryRepository;
import org.service.common.Query;

import lombok.RequiredArgsConstructor;

@Query
@RequiredArgsConstructor
public class ValidateRegisteredBankAccountQuery {

	private final SpringDataRegisteredBankAccountQueryRepository registeredBankAccountQueryRepository;

	public boolean isValidRegisteredBankAccount(String bankName, String bankAccountNumber) {
		return registeredBankAccountQueryRepository.existsByBankNameAndBankAccountNumber(bankName, bankAccountNumber);
	}
}
