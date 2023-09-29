package org.service.membership.application.port.in;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import common.SelfValidating;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Builder
@Data
@EqualsAndHashCode(callSuper = false)
public class ModifyMembershipCommand extends SelfValidating<ModifyMembershipCommand> {

	@NotNull
	private final String membershipId;

	@NotNull
	private final String name;

	@NotNull
	private final String email;

	@NotNull
	@NotBlank
	private final String address;

	private final boolean isValid;

	private final boolean isCorp;

	public ModifyMembershipCommand(
		String membershipId,
		String name,
		String email,
		String address,
		boolean isValid,
		boolean isCorp
	) {
		this.membershipId = membershipId;
		this.name = name;
		this.email = email;
		this.address = address;
		this.isValid = isValid;
		this.isCorp = isCorp;

		this.validateSelf();
	}
}
