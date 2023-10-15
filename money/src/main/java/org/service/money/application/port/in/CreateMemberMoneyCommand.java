package org.service.money.application.port.in;

import javax.validation.constraints.NotNull;

import org.service.common.SelfValidating;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Builder
@Data
@EqualsAndHashCode(callSuper = false)
public class CreateMemberMoneyCommand extends SelfValidating<CreateMemberMoneyCommand> {

	@NotNull
	private final String membershipId;

	public CreateMemberMoneyCommand(String targetMembershipId) {
		this.membershipId = targetMembershipId;
		this.validateSelf();
	}
}
