package org.service.membership.adapter.out.persistence;

import org.service.membership.application.port.out.FindMembershipPort;
import org.service.membership.application.port.out.ModifyMembershipPort;
import org.service.membership.application.port.out.RegisterMembershipPort;
import org.service.membership.domain.Membership;

import common.PersistenceAdapter;
import lombok.RequiredArgsConstructor;

@PersistenceAdapter
@RequiredArgsConstructor
public class MembershipPersistenceAdapter implements RegisterMembershipPort, FindMembershipPort, ModifyMembershipPort {

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

	@Override
	public MembershipJpaEntity modifyMembership(
		Membership.MembershipId membershipId,
		Membership.MembershipName nameValue,
		Membership.MembershipEmail emailValue,
		Membership.MembershipAddress addressValue,
		Membership.MembershipIsValid isValidValue,
		Membership.MembershipIsCorp isCorpValue
	) {
		MembershipJpaEntity entity = this.membershipRepository.getById(Long.parseLong(membershipId.getMembershipId()));

		entity.setName(nameValue.getNameValue());
		entity.setEmail(emailValue.getEmailValue());
		entity.setAddress(addressValue.getAddressValue());
		entity.setValid(isValidValue.isValidValue());
		entity.setCorp(isCorpValue.isCorpValue());

		return this.membershipRepository.save(entity);
	}
}
