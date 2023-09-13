package org.service.membership.application.port.out;

import org.service.membership.adapter.out.persistence.MembershipJpaEntity;
import org.service.membership.domain.Membership;

public interface RegisterMembershipPort {

	MembershipJpaEntity createMembership(
		Membership.MembershipName nameValue,
		Membership.MembershipEmail emailValue,
		Membership.MembershipAddress addressValue,
		Membership.MembershipIsValid isValidValue,
		Membership.MembershipIsCorp isCorpValue
	);
}
