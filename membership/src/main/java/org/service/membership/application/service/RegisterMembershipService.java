package org.service.membership.application.service;

import javax.transaction.Transactional;

import org.service.membership.adapter.out.persistence.MembershipJpaEntity;
import org.service.membership.adapter.out.persistence.MembershipMapper;
import org.service.membership.application.port.in.RegisterMembershipCommand;
import org.service.membership.application.port.in.RegisterMembershipUsecase;
import org.service.membership.application.port.out.RegisterMembershipPort;
import org.service.membership.domain.Membership;

import common.Usecase;
import lombok.RequiredArgsConstructor;

@Usecase
@RequiredArgsConstructor
@Transactional
public class RegisterMembershipService implements RegisterMembershipUsecase {

	private final RegisterMembershipPort registerMembershipPort;

	private final MembershipMapper membershipMapper;

	@Override
	public Membership registerMembership(RegisterMembershipCommand command) {

		// biz logic

		MembershipJpaEntity jpaEntity = this.registerMembershipPort.createMembership(
			new Membership.MembershipName(command.getName()),
			new Membership.MembershipEmail(command.getEmail()),
			new Membership.MembershipAddress(command.getAddress()),
			new Membership.MembershipIsValid(command.isValid()),
			new Membership.MembershipIsCorp(command.isCorp())
		);

		return this.membershipMapper.toDomain(jpaEntity);
	}
}
