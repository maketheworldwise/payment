package org.service.remittance.application.port.out;

import org.service.remittance.adapter.out.persistence.RemittanceRequestJpaEntity;
import org.service.remittance.domain.RemittanceRequest;

public interface RequestRemittancePort {
	RemittanceRequestJpaEntity createRemittanceRequestHistory(
		RemittanceRequest.FromMembershipId fromMembershipId,
		RemittanceRequest.ToMembershipId toMembershipId,
		RemittanceRequest.ToBankName toBankName,
		RemittanceRequest.ToBankAccountNumber toBankAccountNumber,
		RemittanceRequest.RemittanceType remittanceType,
		RemittanceRequest.MoneyAmount moneyAmount,
		RemittanceRequest.Status status
	);

	boolean saveRemittanceRequestHistory(RemittanceRequestJpaEntity entity);
}
