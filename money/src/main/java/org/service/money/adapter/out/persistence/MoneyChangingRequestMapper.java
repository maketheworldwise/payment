package org.service.money.adapter.out.persistence;

import org.service.money.domain.MoneyChangingRequest;
import org.springframework.stereotype.Component;

@Component
public class MoneyChangingRequestMapper {
	public MoneyChangingRequest toDomain(
		MoneyChangingRequestJpaEntity moneyChangingRequestJpaEntity
	) {
		return MoneyChangingRequest.generateMoneyChangingRequest(
			new MoneyChangingRequest.MoneyChangingRequestId(
				moneyChangingRequestJpaEntity.getMoneyChangingRequestId() + ""),
			new MoneyChangingRequest.TargetMembershipId(moneyChangingRequestJpaEntity.getTargetMembershipId()),
			new MoneyChangingRequest.MoneyChangingType(moneyChangingRequestJpaEntity.getChangingType()),
			new MoneyChangingRequest.MoneyChangingAmount(moneyChangingRequestJpaEntity.getChangingMoneyAmount()),
			new MoneyChangingRequest.MoneyChangingStatus(moneyChangingRequestJpaEntity.getChangingMoneyStatus()),
			new MoneyChangingRequest.Uuid(moneyChangingRequestJpaEntity.getUuid())
		);
	}
}
