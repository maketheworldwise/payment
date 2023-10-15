package org.service.money.adapter.axon.event;

import javax.validation.constraints.NotNull;

import org.service.common.SelfValidating;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Builder
@Data
@EqualsAndHashCode(callSuper = false)
public class IncreaseMemberMoneyEvent extends SelfValidating<IncreaseMemberMoneyEvent> {

	private final String aggregateIdentifier;

	@NotNull
	private final String targetMembershipId;

	private final int amount;

	public IncreaseMemberMoneyEvent(String aggregateIdentifier, String targetMembershipId, int amount) {
		this.aggregateIdentifier = aggregateIdentifier;
		this.targetMembershipId = targetMembershipId;
		this.amount = amount;
		this.validateSelf();
	}
}
