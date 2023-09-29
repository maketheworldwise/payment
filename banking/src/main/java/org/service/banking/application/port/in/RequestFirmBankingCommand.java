package org.service.banking.application.port.in;

import javax.validation.constraints.NotNull;

import org.service.common.SelfValidating;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Builder
@Data
@EqualsAndHashCode(callSuper = false)
public class RequestFirmBankingCommand extends SelfValidating<RequestFirmBankingCommand> {
	@NotNull
	private final String fromBankName;

	@NotNull
	private final String fromBankAccountNumber;

	@NotNull
	private final String toBankName;

	@NotNull
	private final String toBankAccountNumber;

	@NotNull
	private final int moneyAmount;

	public RequestFirmBankingCommand(
		String fromBankName,
		String fromBankAccountNumber,
		String toBankName,
		String toBankAccountNumber,
		int moneyAmount
	) {
		this.fromBankName = fromBankName;
		this.fromBankAccountNumber = fromBankAccountNumber;
		this.toBankName = toBankName;
		this.toBankAccountNumber = toBankAccountNumber;
		this.moneyAmount = moneyAmount;

		this.validateSelf();
	}
}
