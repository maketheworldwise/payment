package org.service.money.application.port.out;

import org.service.money.domain.MemberMoney;

public interface CreateMemberMoneyPort {

	void createMemberMoney(
		MemberMoney.MembershipId membershipId,
		MemberMoney.MoneyAggregateIdentifier aggregateIdentifier
	);
}
