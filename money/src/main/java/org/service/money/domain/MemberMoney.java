package org.service.money.domain;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Value;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public class MemberMoney {

	private final String memberMoneyId;

	private final String membershipId;

	private final int balance; // won

	public static MemberMoney generateMemberMoney(
		MemberMoneyId memberMoneyId,
		MembershipId membershipId,
		Balance balance
	) {
		return new MemberMoney(
			memberMoneyId.memberMoneyId,
			membershipId.membershipId,
			balance.balance
		);
	}

	@Value
	public static class MemberMoneyId {
		String memberMoneyId;

		public MemberMoneyId(String value) {
			this.memberMoneyId = value;
		}
	}

	@Value
	public static class MembershipId {
		String membershipId;

		public MembershipId(String value) {
			this.membershipId = value;
		}
	}

	@Value
	public static class Balance {
		int balance;

		public Balance(int value) {
			this.balance = value;
		}
	}
}
