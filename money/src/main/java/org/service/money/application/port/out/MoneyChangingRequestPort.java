package org.service.money.application.port.out;

import org.service.money.adapter.out.persistence.MemberMoneyJpaEntity;
import org.service.money.adapter.out.persistence.MoneyChangingRequestJpaEntity;
import org.service.money.domain.MemberMoney;
import org.service.money.domain.MoneyChangingRequest;

public interface MoneyChangingRequestPort {

	MoneyChangingRequestJpaEntity createMoneyChangingRequest(
		MoneyChangingRequest.TargetMembershipId targetMembershipId,
		MoneyChangingRequest.MoneyChangingType moneyChangingType,
		MoneyChangingRequest.MoneyChangingAmount moneyChangingAmount,
		MoneyChangingRequest.MoneyChangingStatus moneyChangingStatus,
		MoneyChangingRequest.Uuid uuid
	);

	MoneyChangingRequestJpaEntity modifyMoneyChangingRequest(MoneyChangingRequestJpaEntity requestedEntity);

	MemberMoneyJpaEntity increaseMemberMoney(
		MemberMoney.MembershipId membershipId,
		int increaseMoneyAmount
	);
}
