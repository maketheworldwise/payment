package org.service.membership.application.port.out;

import org.service.membership.adapter.out.persistence.MembershipJpaEntity;
import org.service.membership.domain.Membership;

public interface FindMembershipPort {

	MembershipJpaEntity findMembership(Membership.MembershipId membershipId);
}
