package org.service.remittance.domain;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Value;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public class RemittanceRequest { // information of remittance request status

	private final String remittanceRequestId;

	private final String fromMembershipId;

	private final String toMembershipId;

	private final String toBankName;

	private final String toBankAccountNumber;

	private final int remittanceType; // 0: membership (internal), 1: bank (external)

	private final int moneyAmount;

	private final String status;

	public static RemittanceRequest generateRemittanceRequest(
		RemittanceRequest.RemittanceRequestId remittanceRequestId,
		RemittanceRequest.FromMembershipId fromMembershipId,
		RemittanceRequest.ToMembershipId toMembershipId,
		RemittanceRequest.ToBankName toBankName,
		RemittanceRequest.ToBankAccountNumber toBankAccountNumber,
		RemittanceRequest.RemittanceType remittanceType,
		RemittanceRequest.MoneyAmount moneyAmount,
		RemittanceRequest.Status status
	) {
		return new RemittanceRequest(
			remittanceRequestId.remittanceRequestId,
			fromMembershipId.fromMembershipId,
			toMembershipId.toMembershipId,
			toBankName.toBankName,
			toBankAccountNumber.tobBankAccountNumber,
			remittanceType.remittanceType,
			moneyAmount.moneyAmount,
			status.status
		);
	}

	@Value()
	public static class RemittanceRequestId {
		String remittanceRequestId;

		public RemittanceRequestId(String value) {
			this.remittanceRequestId = value;
		}
	}

	@Value()
	public static class FromMembershipId {
		String fromMembershipId;

		public FromMembershipId(String value) {
			this.fromMembershipId = value;
		}
	}

	@Value()
	public static class ToMembershipId {
		String toMembershipId;

		public ToMembershipId(String value) {
			this.toMembershipId = value;
		}
	}

	@Value()
	public static class ToBankName {
		String toBankName;

		public ToBankName(String value) {
			this.toBankName = value;
		}
	}

	@Value()
	public static class ToBankAccountNumber {
		String tobBankAccountNumber;

		public ToBankAccountNumber(String value) {
			this.tobBankAccountNumber = value;
		}
	}

	@Value()
	public static class RemittanceType {
		int remittanceType;

		public RemittanceType(int value) {
			this.remittanceType = value;
		}
	}

	@Value()
	public static class MoneyAmount {
		int moneyAmount;

		public MoneyAmount(int value) {
			this.moneyAmount = value;
		}
	}

	@Value()
	public static class Status {
		String status;

		public Status(String value) {
			this.status = value;
		}
	}
}
