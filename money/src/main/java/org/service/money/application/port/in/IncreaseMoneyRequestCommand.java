package org.service.money.application.port.in;

import javax.validation.constraints.NotNull;

import org.service.common.SelfValidating;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Builder
@Data
@EqualsAndHashCode(callSuper = false)
public class IncreaseMoneyRequestCommand extends SelfValidating<IncreaseMoneyRequestCommand> {

	@NotNull
	private final String targetMembershipId;

	@NotNull
	private final int amount;

	public IncreaseMoneyRequestCommand(String targetMembershipId, int amount) {
		this.targetMembershipId = targetMembershipId;
		this.amount = amount;

		this.validateSelf();
	}
}
