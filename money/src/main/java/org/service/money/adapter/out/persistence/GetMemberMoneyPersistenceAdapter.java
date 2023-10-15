package org.service.money.adapter.out.persistence;

import org.service.common.PersistenceAdapter;
import org.service.money.application.port.out.GetMemberMoneyPort;
import org.service.money.domain.MemberMoney;

import lombok.RequiredArgsConstructor;

@PersistenceAdapter
@RequiredArgsConstructor
public class GetMemberMoneyPersistenceAdapter implements GetMemberMoneyPort {

	private final SpringDataMemberMoneyRequestRepository memberMoneyRepository;

	@Override
	public MemberMoneyJpaEntity getMemberMoney(MemberMoney.MembershipId membershipId) {
		return memberMoneyRepository.findByMembershipId(membershipId.getMembershipId())
			.orElseThrow(() -> new RuntimeException("member money not exists"));
	}
}
