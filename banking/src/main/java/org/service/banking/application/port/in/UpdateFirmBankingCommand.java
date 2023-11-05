package org.service.banking.application.port.in;

import javax.validation.constraints.NotNull;

import org.service.common.SelfValidating;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Builder
@Data
@EqualsAndHashCode(callSuper = false)
public class UpdateFirmBankingCommand extends SelfValidating<UpdateFirmBankingCommand> {

	@NotNull
	private String firmBankingRequestAggregateIdentifier;

	@NotNull
	private int status;

	public UpdateFirmBankingCommand(
		String firmBankingRequestAggregateIdentifier,
		int status
	) {
		this.firmBankingRequestAggregateIdentifier = firmBankingRequestAggregateIdentifier;
		this.status = status;

		this.validateSelf();
	}
}
