package org.service.banking.application.query;

import org.service.banking.adapter.out.persistence.SpringDataRegisteredBankAccountQueryRepository;
import org.service.banking.application.query.ro.RegisteredBankAccountRo;
import org.service.common.Query;

import lombok.RequiredArgsConstructor;

@Query
@RequiredArgsConstructor
public class FindRegisteredBankAccountQuery {

	private final SpringDataRegisteredBankAccountQueryRepository registeredBankAccountQueryRepository;

	public RegisteredBankAccountRo getRegisteredBankAccount(String registeredBankAccountId) {
		return registeredBankAccountQueryRepository.findById(Long.parseLong(registeredBankAccountId))
			.map(RegisteredBankAccountRo::new)
			.orElseThrow(() -> new IllegalArgumentException("registered bank not found"));
	}
}
