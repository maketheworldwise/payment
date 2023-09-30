package org.service.money.adapter.in.web;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MoneyChangingResultDetail {

	private String moneyChangingRequestId;

	private int moneyChangingType; // 0: increase, 1: decrease

	private int amount;

	private int moneyChangingResultStatus; // 0: succeeded, 1: failed, 2: not enough money, 3: membership not exists, 4: money changing request not exists
}
