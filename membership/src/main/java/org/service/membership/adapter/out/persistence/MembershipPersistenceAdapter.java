package org.service.membership.adapter.out.persistence;

import org.service.membership.application.port.out.FindMembershipPort;
import org.service.membership.application.port.out.RegisterMembershipPort;
import org.service.membership.domain.Membership;

import common.PersistenceAdapter;
import lombok.RequiredArgsConstructor;

@PersistenceAdapter
@RequiredArgsConstructor
public class MembershipPersistenceAdapter implements RegisterMembershipPort, FindMembershipPort {

	private final SpringDataMembershipRepository membershipRepository;

	@Override
	public MembershipJpaEntity createMembership(
		Membership.MembershipName nameValue,
		Membership.MembershipEmail emailValue,
		Membership.MembershipAddress addressValue,
		Membership.MembershipIsValid isValidValue,
		Membership.MembershipIsCorp isCorpValue
	) {
		return this.membershipRepository.save(
			new MembershipJpaEntity(
				nameValue.getNameValue(),
				emailValue.getEmailValue(),
				addressValue.getAddressValue(),
				isValidValue.isValidValue(),
				isCorpValue.isCorpValue()
			)
		);
	}

	@Override
	public MembershipJpaEntity findMembership(Membership.MembershipId membershipId) {
		return this.membershipRepository.getById(Long.parseLong(membershipId.getMembershipId()));
	}
}
