package org.service.banking.adapter.axon.command;

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
public class CreateFirmBankingRequestCommand {

	private String fromBankName;

	private String fromBankAccountNumber;

	private String toBankName;

	private String toBankAccountNumber;

	private int moneyAmount;

}
