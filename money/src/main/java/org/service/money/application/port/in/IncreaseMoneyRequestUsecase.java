package org.service.money.application.port.in;

import org.service.money.domain.MoneyChangingRequest;

public interface IncreaseMoneyRequestUsecase {
	MoneyChangingRequest increaseMoneyRequest(IncreaseMoneyRequestCommand command);

	MoneyChangingRequest increaseMoneyRequestAsync(IncreaseMoneyRequestCommand command);

	void increaseMoneyChangingRequestByEvent(IncreaseMoneyRequestCommand command);
}
