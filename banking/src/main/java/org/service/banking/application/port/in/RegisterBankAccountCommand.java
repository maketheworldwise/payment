package org.service.banking.application.port.in;

import javax.validation.constraints.NotNull;

import org.service.common.SelfValidating;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Builder
@Data
@EqualsAndHashCode(callSuper = false)
public class RegisterBankAccountCommand extends SelfValidating<RegisterBankAccountCommand> {

	@NotNull
	private final String membershipId;

	@NotNull
	private final String bankName;

	@NotNull
	private final String bankAccountNumber;

	@NotNull
	private final boolean linkedStatusIsValid;

	public RegisterBankAccountCommand(String membershipId, String bankName, String bankAccountNumber, boolean linkedStatusIsValid) {
		this.membershipId = membershipId;
		this.bankName = bankName;
		this.bankAccountNumber = bankAccountNumber;
		this.linkedStatusIsValid = linkedStatusIsValid;

		this.validateSelf();
	}
}
