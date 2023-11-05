package org.service.banking.domain;

import java.util.UUID;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Value;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public class FirmBankingRequest {
	private final String firmBankingRequestId;

	private final String fromBankName;

	private final String fromBankAccountNumber;

	private final String toBankName;

	private final String toBankAccountNumber;

	private final int moneyAmount; // won

	private final int firmBankingStatus; // 0: requested, 1: completed, 2: failed

	private final UUID uuid;

	private final String aggregateIdentifier;

	public static FirmBankingRequest generateFirmBanking(
		FirmBankingRequestId firmBankingRequestId,
		FromBankName fromBankName,
		FromBankAccountNumber fromBankAccountNumber,
		ToBankName toBankName,
		ToBankAccountNumber toBankAccountNumber,
		MoneyAmount moneyAmount,
		FirmBankingStatus firmBankingStatus,
		UUID uuid,
		AggregateIdentifier aggregateIdentifier
	) {
		return new FirmBankingRequest(
			firmBankingRequestId.firmBankingRequestId,
			fromBankName.fromBankName,
			fromBankAccountNumber.fromBankAccountNumber,
			toBankName.toBankName,
			toBankAccountNumber.toBankAccountNumber,
			moneyAmount.moneyAmount,
			firmBankingStatus.firmBankingStatus,
			uuid,
			aggregateIdentifier.aggregateIdentifier
		);
	}

	@Value
	public static class FirmBankingRequestId {
		String firmBankingRequestId;

		public FirmBankingRequestId(String value) {
			this.firmBankingRequestId = value;
		}
	}

	@Value
	public static class FromBankName {
		String fromBankName;

		public FromBankName(String value) {
			this.fromBankName = value;
		}
	}

	@Value
	public static class FromBankAccountNumber {
		String fromBankAccountNumber;

		public FromBankAccountNumber(String value) {
			this.fromBankAccountNumber = value;
		}
	}

	@Value
	public static class ToBankName {
		String toBankName;

		public ToBankName(String value) {
			this.toBankName = value;
		}
	}

	@Value
	public static class ToBankAccountNumber {
		String toBankAccountNumber;

		public ToBankAccountNumber(String value) {
			this.toBankAccountNumber = value;
		}
	}

	@Value
	public static class MoneyAmount {
		int moneyAmount;

		public MoneyAmount(int value) {
			this.moneyAmount = value;
		}
	}

	@Value
	public static class FirmBankingStatus {
		int firmBankingStatus;

		public FirmBankingStatus(int value) {
			this.firmBankingStatus = value;
		}
	}

	@Value
	public static class AggregateIdentifier {
		String aggregateIdentifier;

		public AggregateIdentifier(String value) {
			this.aggregateIdentifier = value;
		}
	}
}
