package org.service.banking.adapter.out.persistence;

import org.springframework.data.jpa.repository.JpaRepository;

// todo: cqrs read db
public interface SpringDataRegisteredBankAccountQueryRepository extends JpaRepository<RegisteredBankAccountJpaEntity, Long> {
	boolean existsByBankNameAndBankAccountNumber(String bankName, String bankAccountNumber);
}
