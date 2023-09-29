package org.service.banking.application.query.ro;

import org.service.banking.adapter.out.persistence.RegisteredBankAccountJpaEntity;

import lombok.Data;

@Data
public class RegisteredBankAccountRo {
	private final String registeredBankAccountId;
	private final String membershipId;
	private final String bankName;

	private final String bankAccountNumber;
	private final boolean isValid;

	public RegisteredBankAccountRo(RegisteredBankAccountJpaEntity entity) {
		this.registeredBankAccountId = entity.getRegisteredBankAccountId() + "";
		this.membershipId = entity.getMembershipId();
		this.bankName = entity.getBankName();
		this.bankAccountNumber = entity.getBankAccountNumber();
		this.isValid = entity.isLinkedStatusIsValid();
	}
}
