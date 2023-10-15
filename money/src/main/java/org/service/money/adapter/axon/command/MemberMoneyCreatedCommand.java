package org.service.money.adapter.axon.command;

import javax.validation.constraints.NotNull;

import org.service.common.SelfValidating;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Builder
@Data
@EqualsAndHashCode(callSuper = false)
public class MemberMoneyCreatedCommand extends SelfValidating<MemberMoneyCreatedCommand> {

	@NotNull
	private String membershipId;

	public MemberMoneyCreatedCommand() {
	}

	public MemberMoneyCreatedCommand(String targetMembershipId) {
		this.membershipId = targetMembershipId;
		this.validateSelf();
	}
}
