package org.service.remittance.application.port.in;

import javax.validation.constraints.NotNull;

import org.service.common.SelfValidating;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Builder
@Data
@EqualsAndHashCode(callSuper = false)
public class FindRemittanceHistoryCommand extends SelfValidating<FindRemittanceHistoryCommand> {

	@NotNull
	private final String membershipId;

	public FindRemittanceHistoryCommand(String membershipId) {
		this.membershipId = membershipId;

		this.validateSelf();
	}
}
