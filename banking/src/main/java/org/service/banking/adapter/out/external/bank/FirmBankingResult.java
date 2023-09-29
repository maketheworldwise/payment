package org.service.banking.adapter.out.external.bank;

import lombok.Data;

@Data
public class FirmBankingResult {
	private int resultCode; // 1: requested, 1: completed, 2: failed

	public FirmBankingResult(int resultCode) {
		this.resultCode = resultCode;
	}
}
