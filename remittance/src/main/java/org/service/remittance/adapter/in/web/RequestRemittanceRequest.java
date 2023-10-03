package org.service.remittance.adapter.in.web;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RequestRemittanceRequest {

	public String fromMembershipId;

	public String toMembershipId;

	public String toBankName;

	public String toBankAccountNumber;

	private int remittanceType; // 0: membership (internal), 1: bank (external)

	public int moneyAmount;
}
