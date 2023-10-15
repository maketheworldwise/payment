package org.service.money.adapter.out.persistence;

import org.service.common.PersistenceAdapter;
import org.service.money.application.port.out.CreateMemberMoneyPort;
import org.service.money.domain.MemberMoney;

import lombok.RequiredArgsConstructor;

@PersistenceAdapter
@RequiredArgsConstructor
public class CreateMemberMoneyPersistenceAdapter implements CreateMemberMoneyPort {

	private final SpringDataMemberMoneyRequestRepository memberMoneyRequestRepository;
	@Override
	public void createMemberMoney(
		MemberMoney.MembershipId membershipId,
		MemberMoney.MoneyAggregateIdentifier aggregateIdentifier
	) {
		MemberMoneyJpaEntity entity = new MemberMoneyJpaEntity(
			membershipId.getMembershipId(),
			0,
			aggregateIdentifier.getAggregateIdentifier()
		);

		memberMoneyRequestRepository.save(entity);
	}
}
