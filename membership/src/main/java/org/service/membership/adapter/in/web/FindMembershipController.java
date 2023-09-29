package org.service.membership.adapter.in.web;

import org.service.common.WebAdapter;
import org.service.membership.application.port.in.FindMembershipCommand;
import org.service.membership.application.service.FindMembershipUsecase;
import org.service.membership.domain.Membership;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

@WebAdapter
@RestController
@RequiredArgsConstructor
public class FindMembershipController {

	private final FindMembershipUsecase findMembershipUsecase;

	@GetMapping(path = "/membership/{membershipId}")
	ResponseEntity<Membership> findMemberByMembershipId(@PathVariable String membershipId) {
		FindMembershipCommand command = FindMembershipCommand.builder()
			.membershipId(membershipId)
			.build();

		return ResponseEntity.ok(this.findMembershipUsecase.findMembership(command));
	}
}
