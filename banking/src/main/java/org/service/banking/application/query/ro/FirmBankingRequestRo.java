package org.service.banking.application.query.ro;

import java.util.UUID;

import org.service.banking.adapter.out.persistence.FirmBankingRequestJpaEntity;

import lombok.Data;

@Data
public class FirmBankingRequestRo {
	private final String firmBankingRequestId;

	private final String fromBankName;

	private final String fromBankAccountNumber;

	private final String toBankName;

	private final String toBankAccountNumber;

	private final int moneyAmount;

	private final int firmBankingStatus;

	private final UUID uuid;

	public FirmBankingRequestRo(FirmBankingRequestJpaEntity entity) {
		this.firmBankingRequestId = entity.getRequestFirmBankingId() + "";
		this.fromBankName = entity.getFromBankName();
		this.fromBankAccountNumber = entity.getFromBankAccountNumber();
		this.toBankName = entity.getToBankName();
		this.toBankAccountNumber = entity.getToBankAccountNumber();
		this.moneyAmount = entity.getMoneyAmount();
		this.firmBankingStatus = entity.getFirmBankingStatus();
		this.uuid = entity.getUuid();
	}

}
