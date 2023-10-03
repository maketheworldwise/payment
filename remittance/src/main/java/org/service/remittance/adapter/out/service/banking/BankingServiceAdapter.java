package org.service.remittance.adapter.out.service.banking;

import org.service.common.CommonHttpClient;
import org.service.common.ExternalAdapter;
import org.service.remittance.application.port.out.banking.BankingInfo;
import org.service.remittance.application.port.out.banking.BankingPort;
import org.springframework.beans.factory.annotation.Value;

import lombok.RequiredArgsConstructor;

@ExternalAdapter
@RequiredArgsConstructor
public class BankingServiceAdapter implements BankingPort {

	private final CommonHttpClient bankingServiceHttpClient;

	@Value("${service.banking.url}")
	private String bankingServiceEndpoint;

	@Override
	public BankingInfo getMembershipBankingInfo(String bankName, String bankAccountNumber) {
		return new BankingInfo(bankName, bankAccountNumber, true);
	}

	@Override
	public boolean requestFirmBanking(String bankName, String bankAccountNumber, int moneyAmount) {
		return true;
	}
}
