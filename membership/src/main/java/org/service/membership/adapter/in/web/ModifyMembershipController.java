package org.service.membership.adapter.in.web;

import org.service.common.WebAdapter;
import org.service.membership.application.port.in.ModifyMembershipCommand;
import org.service.membership.application.port.in.ModifyMembershipUsecase;
import org.service.membership.domain.Membership;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

@WebAdapter
@RestController
@RequiredArgsConstructor
public class ModifyMembershipController {
	private final ModifyMembershipUsecase modifyMembershipUsecase;

	@PostMapping("/membership/modify")
	ResponseEntity<Membership> modifyMembership(@RequestBody ModifyMembershipRequest request) {
		ModifyMembershipCommand command = ModifyMembershipCommand.builder()
			.membershipId(request.getMembershipId())
			.name(request.getName())
			.address(request.getAddress())
			.email(request.getEmail())
			.isValid(request.isValid())
			.isCorp(request.isCorp())
			.build();

		return ResponseEntity.ok(this.modifyMembershipUsecase.modifyMembership(command));
	}
}
