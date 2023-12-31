package org.service.membership.application.port.in;

import javax.validation.constraints.NotNull;

import org.service.common.SelfValidating;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Builder
@Data
@EqualsAndHashCode(callSuper = false)
public class FindMembershipCommand extends SelfValidating<FindMembershipCommand> {

	@NotNull
	private final String membershipId;

	public FindMembershipCommand(String membershipId) {
		this.membershipId = membershipId;
	}
}
