package org.service.remittance.adapter.out.service.money;

import org.service.common.CommonHttpClient;
import org.service.common.ExternalAdapter;
import org.service.remittance.application.port.out.money.MoneyInfo;
import org.service.remittance.application.port.out.money.MoneyPort;
import org.springframework.beans.factory.annotation.Value;

import lombok.RequiredArgsConstructor;

@ExternalAdapter
@RequiredArgsConstructor
public class MoneyServiceAdapter implements MoneyPort {

	private final CommonHttpClient moneyServiceHttpClient;

	@Value("${service.money.url}")
	private String moneyServiceEndpoint;

	@Override
	public MoneyInfo getMoneyInfo(String membershipId) {
		return new MoneyInfo(membershipId, 999999999);
	}

	@Override
	public boolean requestMoneyRecharging(String membership, int moneyAmount) {
		return true;
	}

	@Override
	public boolean requestMoneyIncrease(String membership, int moneyAmount) {
		return true;
	}

	@Override
	public boolean requestMoneyDecrease(String membership, int moneyAmount) {
		return true;
	}
}
