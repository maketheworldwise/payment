package org.service.banking.domain;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Value;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public class RegisterBankAccount {

	private final String registerBankAccountId;

	private final String membershipId;

	private final String bankName; // todo: enum

	private final String bankAccountNumber;

	private final boolean linkedStatusIsValid;

	public static RegisterBankAccount generateRegisterBankAccount(
		RegisterBankAccountId registerBankAccountId,
		MembershipId membershipId,
		BankName bankName,
		BankAccountNumber bankAccountNumber,
		LinkedStatusIsValid linkedStatusIsValid
	) {
		return new RegisterBankAccount(
			registerBankAccountId.registerBankAccountId,
			membershipId.membershipId,
			bankName.bankName,
			bankAccountNumber.bankAccountNumber,
			linkedStatusIsValid.linkedStatusIsValid
		);
	}

	@Value
	public static class RegisterBankAccountId {
		String registerBankAccountId;

		public RegisterBankAccountId(String value) {
			this.registerBankAccountId = value;
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
	public static class BankName {
		String bankName;

		public BankName(String value) {
			this.bankName = value;
		}
	}

	@Value
	public static class BankAccountNumber {
		String bankAccountNumber;

		public BankAccountNumber(String value) {
			this.bankAccountNumber = value;
		}
	}

	@Value
	public static class LinkedStatusIsValid {
		boolean linkedStatusIsValid;

		public LinkedStatusIsValid(boolean value) {
			this.linkedStatusIsValid = value;
		}
	}
}
