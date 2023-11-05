package org.service.banking.adapter.axon.command;

import javax.validation.constraints.NotNull;

import org.axonframework.modelling.command.TargetAggregateIdentifier;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode(callSuper = false)
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class UpdateFirmBankingRequestCommand {

	@NotNull
	@TargetAggregateIdentifier
	private String aggregateIdentifier;

	private int firmBankingStatus;
}
