package org.service.membership.adapter.out.persistence;

import org.service.membership.domain.Membership;
import org.springframework.stereotype.Component;

@Component
public class MembershipMapper {

	public Membership toDomain(MembershipJpaEntity entity) {
		return Membership.generateMember(
			new Membership.MembershipId(entity.getMembershipId()+""),
			new Membership.MembershipName(entity.getName()),
			new Membership.MembershipEmail(entity.getEmail()),
			new Membership.MembershipAddress(entity.getAddress()),
			new Membership.MembershipIsValid(entity.isValid()),
			new Membership.MembershipIsCorp(entity.isCorp())
		);
	}
}
