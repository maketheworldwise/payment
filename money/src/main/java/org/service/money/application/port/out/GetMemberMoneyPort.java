package org.service.money.application.port.out;

import org.service.money.adapter.out.persistence.MemberMoneyJpaEntity;
import org.service.money.domain.MemberMoney;

public interface GetMemberMoneyPort {
	MemberMoneyJpaEntity getMemberMoney(MemberMoney.MembershipId membershipId);
}
