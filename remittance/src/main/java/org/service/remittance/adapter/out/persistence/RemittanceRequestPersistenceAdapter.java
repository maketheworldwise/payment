package org.service.remittance.adapter.out.persistence;

import java.util.List;

import org.service.common.PersistenceAdapter;
import org.service.remittance.application.port.out.FindRemittanceHistoryPort;
import org.service.remittance.application.port.out.RequestRemittancePort;
import org.service.remittance.domain.RemittanceRequest;

import lombok.RequiredArgsConstructor;

@PersistenceAdapter
@RequiredArgsConstructor
public class RemittanceRequestPersistenceAdapter implements RequestRemittancePort, FindRemittanceHistoryPort {

	private final SpringDataRemittanceRequestRepository remittanceRequestRepository;

	@Override
	public RemittanceRequestJpaEntity createRemittanceRequestHistory(
		RemittanceRequest.FromMembershipId fromMembershipId,
		RemittanceRequest.ToMembershipId toMembershipId,
		RemittanceRequest.ToBankName toBankName,
		RemittanceRequest.ToBankAccountNumber toBankAccountNumber,
		RemittanceRequest.RemittanceType remittanceType,
		RemittanceRequest.MoneyAmount moneyAmount,
		RemittanceRequest.Status status
	) {
		return remittanceRequestRepository.save(
			new RemittanceRequestJpaEntity(
				fromMembershipId.getFromMembershipId(),
				toMembershipId.getToMembershipId(),
				toBankName.getToBankName(),
				toBankAccountNumber.getTobBankAccountNumber(),
				remittanceType.getRemittanceType(),
				moneyAmount.getMoneyAmount(),
				status.getStatus()
			)
		);
	}

	@Override
	public boolean saveRemittanceRequestHistory(RemittanceRequestJpaEntity entity) {
		remittanceRequestRepository.save(entity);
		return true;
	}

	@Override
	public List<RemittanceRequestJpaEntity> findRemittanceHistory(String membershipId) {
		// todo
		return null;
	}
}
