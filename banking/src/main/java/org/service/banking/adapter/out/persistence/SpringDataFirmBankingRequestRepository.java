package org.service.banking.adapter.out.persistence;

import java.util.Optional;

import org.service.banking.domain.FirmBankingRequest;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SpringDataFirmBankingRequestRepository extends JpaRepository<FirmBankingRequestJpaEntity, Long> {
	Optional<FirmBankingRequestJpaEntity> findByAggregateIdentifier(String aggregateIdentifier);
}
