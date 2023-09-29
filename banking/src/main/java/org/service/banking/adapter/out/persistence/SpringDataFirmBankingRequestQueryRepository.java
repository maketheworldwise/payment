package org.service.banking.adapter.out.persistence;

import org.springframework.data.jpa.repository.JpaRepository;

// todo: cqrs read db
public interface SpringDataFirmBankingRequestQueryRepository extends JpaRepository<FirmBankingRequestJpaEntity, Long> {
}
