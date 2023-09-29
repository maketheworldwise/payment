package org.service.membership.application.port.out;

import org.service.membership.adapter.out.persistence.MembershipJpaEntity;
import org.service.membership.domain.Membership;

public interface ModifyMembershipPort {
	MembershipJpaEntity modifyMembership(
		Membership.MembershipId membershipId,
		Membership.MembershipName nameValue,
		Membership.MembershipEmail emailValue,
		Membership.MembershipAddress addressValue,
		Membership.MembershipIsValid isValidValue,
		Membership.MembershipIsCorp isCorpValue
	);
}
