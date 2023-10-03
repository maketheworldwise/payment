package org.service.remittance.application.port.in;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.service.common.SelfValidating;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@Builder
@EqualsAndHashCode(callSuper = false)
public class RequestRemittanceCommand extends SelfValidating<RequestRemittanceCommand> {
	@NotNull
	public String fromMembershipId;

	public String toMembershipId;

	public String toBankName;

	public String toBankAccountNumber;

	@NotNull
	private int remittanceType; // 0: membership (internal), 1: bank (external)

	@NotNull
	@NotBlank
	public int moneyAmount;

	public RequestRemittanceCommand(
		String fromMembershipId,
		String toMembershipId,
		String toBankName,
		String toBankAccountNumber,
		int remittanceType,
		int moneyAmount
	) {
		this.fromMembershipId = fromMembershipId;
		this.toMembershipId = toMembershipId;
		this.toBankName = toBankName;
		this.toBankAccountNumber = toBankAccountNumber;
		this.remittanceType = remittanceType;
		this.moneyAmount = moneyAmount;

		this.validateSelf();
	}
}
