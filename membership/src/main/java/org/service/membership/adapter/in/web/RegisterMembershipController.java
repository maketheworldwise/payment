package org.service.membership.adapter.in.web;

import org.service.membership.application.port.in.RegisterMembershipCommand;
import org.service.membership.application.port.in.RegisterMembershipUsecase;
import org.service.membership.domain.Membership;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import common.WebAdapter;
import lombok.RequiredArgsConstructor;

@WebAdapter
@RestController
@RequiredArgsConstructor
public class RegisterMembershipController {

	private final RegisterMembershipUsecase registerMembershipUsecase;

	@PostMapping(path = "/membership/register")
	Membership registerMembership(@RequestBody RegisterMembershipRequest request) {
		RegisterMembershipCommand command = RegisterMembershipCommand.builder()
			.name(request.getName())
			.email(request.getEmail())
			.address(request.getAddress())
			.isValid(true)
			.isCorp(request.isCorp())
			.build();

		return this.registerMembershipUsecase.registerMembership(command);
	}

}