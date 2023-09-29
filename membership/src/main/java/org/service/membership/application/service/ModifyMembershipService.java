package org.service.membership.application.service;

import javax.transaction.Transactional;

import org.service.membership.adapter.out.persistence.MembershipJpaEntity;
import org.service.membership.adapter.out.persistence.MembershipMapper;
import org.service.membership.application.port.in.ModifyMembershipCommand;
import org.service.membership.application.port.in.ModifyMembershipUsecase;
import org.service.membership.application.port.out.ModifyMembershipPort;
import org.service.membership.domain.Membership;

import common.Usecase;
import lombok.RequiredArgsConstructor;

@Usecase
@RequiredArgsConstructor
@Transactional
public class ModifyMembershipService implements ModifyMembershipUsecase {

	private final ModifyMembershipPort modifyMembershipPort;

	private final MembershipMapper membershipMapper;

	@Override
	public Membership modifyMembership(ModifyMembershipCommand command) {

		MembershipJpaEntity jpaEntity = modifyMembershipPort.modifyMembership(
			new Membership.MembershipId(command.getMembershipId()),
			new Membership.MembershipName(command.getName()),
			new Membership.MembershipEmail(command.getEmail()),
			new Membership.MembershipAddress(command.getAddress()),
			new Membership.MembershipIsValid(command.isValid()),
			new Membership.MembershipIsCorp(command.isCorp())
		);

		return membershipMapper.toDomain(jpaEntity);
	}
}
