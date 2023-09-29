package org.service.banking.adapter.out.external.bank;

import org.service.banking.application.port.out.RequestBankAccountInfoPort;
import org.service.common.ExternalAdapter;

import lombok.RequiredArgsConstructor;

@ExternalAdapter
@RequiredArgsConstructor
public class ExternalBankAccountAdapter implements RequestBankAccountInfoPort {
	@Override
	public BankAccount getBankAccountInfo(GetBankAccountRequest request) {
		/*
		  <biz logic>

		  0. http communication
		  1. get account information
		  2. convert to BankAccount
		 */

		return new BankAccount(request.getBankName(), request.getBankAccountNumber(), true);
	}
}
