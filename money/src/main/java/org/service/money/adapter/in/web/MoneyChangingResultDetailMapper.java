package org.service.money.adapter.in.web;

import org.service.money.domain.MoneyChangingRequest;
import org.springframework.stereotype.Component;

@Component
public class MoneyChangingResultDetailMapper {

	public MoneyChangingResultDetail toDetail(MoneyChangingRequest moneyChangingRequest) {
		// 0: succeeded, 1: failed, 2: not enough money, 3: membership not exists, 4: money changing request not exists

		int status = 0; // succeeded
		switch(moneyChangingRequest.getChangingMoneyStatus()) {
			case 0:
			case 1:
				break;
			case 2:
			case 3:
				status = 2; // failed
				break;
			default:
		}

		return new MoneyChangingResultDetail(
			moneyChangingRequest.getMoneyChangingRequestId(),
			moneyChangingRequest.getChangingType(),
			moneyChangingRequest.getChangingMoneyAmount(),
			status
		);
	}
}
