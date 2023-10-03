package org.service.remittance.application.port.out;

import java.util.List;

import org.service.remittance.adapter.out.persistence.RemittanceRequestJpaEntity;

public interface FindRemittanceHistoryPort {

	List<RemittanceRequestJpaEntity> findRemittanceHistory(String membershipId);
}
