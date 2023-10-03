package org.service.remittance.adapter.out.persistence;

import org.service.remittance.domain.RemittanceRequest;
import org.springframework.stereotype.Component;

@Component
public class RemittanceRequestMapper {

	public RemittanceRequest toDomain(RemittanceRequestJpaEntity remittanceRequestJpaEntity) {
		return RemittanceRequest.generateRemittanceRequest(
			new RemittanceRequest.RemittanceRequestId(remittanceRequestJpaEntity.getFromMembershipId()),
			new RemittanceRequest.FromMembershipId(remittanceRequestJpaEntity.getFromMembershipId()),
			new RemittanceRequest.ToMembershipId(remittanceRequestJpaEntity.getToMembershipId()),
			new RemittanceRequest.ToBankName(remittanceRequestJpaEntity.getToBankName()),
			new RemittanceRequest.ToBankAccountNumber(remittanceRequestJpaEntity.getToBankAccountNumber()),
			new RemittanceRequest.RemittanceType(remittanceRequestJpaEntity.getRemittanceType()),
			new RemittanceRequest.MoneyAmount(remittanceRequestJpaEntity.getMoneyAmount()),
			new RemittanceRequest.Status(remittanceRequestJpaEntity.getStatus())
		);
	}
}
