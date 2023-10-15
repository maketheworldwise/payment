package org.service.money.adapter.axon.command;

import javax.validation.constraints.NotNull;

import org.axonframework.modelling.command.TargetAggregateIdentifier;
import org.service.common.SelfValidating;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Builder
@Data
@EqualsAndHashCode(callSuper = false)
public class IncreaseMemberMoneyCommand extends SelfValidating<IncreaseMemberMoneyCommand> {

	@NotNull
	@TargetAggregateIdentifier
	private String aggregateIdentifier;

	@NotNull
	private String membershipId;

	@NotNull
	private int amount;

	public IncreaseMemberMoneyCommand() {
	}

	public IncreaseMemberMoneyCommand(String aggregateIdentifier, String membershipId, int amount) {
		this.aggregateIdentifier = aggregateIdentifier;
		this.membershipId = membershipId;
		this.amount = amount;
		this.validateSelf();
	}
}
