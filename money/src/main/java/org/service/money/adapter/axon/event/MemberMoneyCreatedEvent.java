package org.service.money.adapter.axon.event;

import javax.validation.constraints.NotNull;

import org.service.common.SelfValidating;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Builder
@Data
@EqualsAndHashCode(callSuper = false)
public class MemberMoneyCreatedEvent extends SelfValidating<MemberMoneyCreatedEvent> {

	@NotNull
	private final String membershipId;

	public MemberMoneyCreatedEvent(String targetMembershipId) {
		this.membershipId = targetMembershipId;
		this.validateSelf();
	}
}
