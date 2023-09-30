package org.service.money.adapter.out.persistence;

import java.util.Date;

import org.service.common.PersistenceAdapter;
import org.service.money.application.port.out.MoneyChangingRequestPort;
import org.service.money.domain.MemberMoney;
import org.service.money.domain.MoneyChangingRequest;

import lombok.RequiredArgsConstructor;

@PersistenceAdapter
@RequiredArgsConstructor
public class MoneyChangingRequestPersistenceAdapter
	implements MoneyChangingRequestPort {

	private final SpringDataMoneyChangingRequestRepository moneyChangingRequestRepository;

	private final SpringDataMemberMoneyRequestRepository memberMoneyRequestRepository;

	@Override
	public MoneyChangingRequestJpaEntity createMoneyChangingRequest(
		MoneyChangingRequest.TargetMembershipId targetMembershipId,
		MoneyChangingRequest.MoneyChangingType moneyChangingType,
		MoneyChangingRequest.MoneyChangingAmount moneyChangingAmount,
		MoneyChangingRequest.MoneyChangingStatus moneyChangingStatus,
		MoneyChangingRequest.Uuid uuid
	) {
		return moneyChangingRequestRepository.save(
			new MoneyChangingRequestJpaEntity(
				targetMembershipId.getTargetMembershipId(),
				moneyChangingType.getChangingType(),
				moneyChangingAmount.getChangingMoneyAmount(),
				moneyChangingStatus.getChangingMoneyStatus(),
				uuid.getUuid(),
				new Date()
			)
		);
	}

	@Override
	public MoneyChangingRequestJpaEntity modifyMoneyChangingRequest(MoneyChangingRequestJpaEntity requestedEntity) {
		return moneyChangingRequestRepository.save(requestedEntity);
	}

	@Override
	public MemberMoneyJpaEntity increaseMemberMoney(
		MemberMoney.MembershipId membershipId,
		int increaseMoneyAmount
	) {
		MemberMoneyJpaEntity jpaEntity = memberMoneyRequestRepository
			.findByMembershipId(membershipId.getMembershipId())
			.orElse(new MemberMoneyJpaEntity(membershipId.getMembershipId(), 0));

		jpaEntity.setBalance(jpaEntity.getBalance() + increaseMoneyAmount);

		return memberMoneyRequestRepository.save(jpaEntity);
	}
}
