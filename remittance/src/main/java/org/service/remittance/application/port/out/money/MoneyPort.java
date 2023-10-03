package org.service.remittance.application.port.out.money;

public interface MoneyPort {

	MoneyInfo getMoneyInfo(String membershipId);

	boolean requestMoneyRecharging(String membership, int moneyAmount);

	boolean requestMoneyIncrease(String membership, int moneyAmount);

	boolean requestMoneyDecrease(String membership, int moneyAmount);
}
