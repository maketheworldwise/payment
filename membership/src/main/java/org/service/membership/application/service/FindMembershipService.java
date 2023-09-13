package org.service.membership.application.service;

import org.service.membership.adapter.out.persistence.MembershipJpaEntity;
import org.service.membership.adapter.out.persistence.MembershipMapper;
import org.service.membership.application.port.in.FindMembershipCommand;
import org.service.membership.application.port.out.FindMembershipPort;
import org.service.membership.domain.Membership;

import common.Usecase;
import lombok.RequiredArgsConstructor;

@Usecase
@RequiredArgsConstructor
public class FindMembershipService implements FindMembershipUsecase {

	private final FindMembershipPort findMembershipPort;
	private final MembershipMapper membershipMapper;

	@Override
	public Membership findMembership(FindMembershipCommand command) {
		MembershipJpaEntity jpaEntity = this.findMembershipPort.findMembership(
			new Membership.MembershipId(command.getMembershipId()));

		return this.membershipMapper.toDomain(jpaEntity);
	}
}
