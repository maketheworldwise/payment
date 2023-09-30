package org.service.money.domain;

import java.util.Date;
import java.util.UUID;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Value;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public class MoneyChangingRequest {

	private final String moneyChangingRequestId;

	private final String targetMembershipId; // requested member

	private final int changingType; // 0: request for increase, 1: request for decrease

	private final int changingMoneyAmount;

	private final int changingMoneyStatus; // 0: request, 1: succeeded, 2: failed, 3: canceled

	private final UUID uuid;

	private final Date createdAt;

	public static MoneyChangingRequest generateMoneyChangingRequest(
		MoneyChangingRequestId moneyChangingRequestId,
		TargetMembershipId targetMembershipId,
		MoneyChangingType moneyChangingType,
		MoneyChangingAmount moneyChangingAmount,
		MoneyChangingStatus moneyChangingStatus,
		Uuid uuid
	) {
		return new MoneyChangingRequest(
			moneyChangingRequestId.moneyChangingRequestId,
			targetMembershipId.targetMembershipId,
			moneyChangingType.changingType,
			moneyChangingAmount.changingMoneyAmount,
			moneyChangingStatus.changingMoneyStatus,
			uuid.uuid,
			new Date()
		);
	}

	@Value
	public static class MoneyChangingRequestId {
		String moneyChangingRequestId;

		public MoneyChangingRequestId(String value) {
			this.moneyChangingRequestId = value;
		}
	}

	@Value
	public static class TargetMembershipId {
		String targetMembershipId;

		public TargetMembershipId(String value) {
			this.targetMembershipId = value;
		}
	}

	@Value
	public static class MoneyChangingType {
		int changingType;

		public MoneyChangingType(int value) {
			this.changingType = value;
		}
	}

	@Value
	public static class MoneyChangingAmount {
		int changingMoneyAmount;

		public MoneyChangingAmount(int value) {
			this.changingMoneyAmount = value;
		}
	}

	@Value
	public static class MoneyChangingStatus {
		int changingMoneyStatus;

		public MoneyChangingStatus(int value) {
			this.changingMoneyStatus = value;
		}
	}

	@Value
	public static class Uuid {
		UUID uuid;

		public Uuid(UUID value) {
			this.uuid = value;
		}
	}
}
