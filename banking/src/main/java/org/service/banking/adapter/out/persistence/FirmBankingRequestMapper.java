package org.service.banking.adapter.out.persistence;

import java.util.UUID;

import org.service.banking.domain.FirmBankingRequest;
import org.springframework.stereotype.Component;

@Component
public class FirmBankingRequestMapper {

	public FirmBankingRequest toDomain(FirmBankingRequestJpaEntity requestFirmBankingJpaEntity, UUID uuid) {
		return FirmBankingRequest.generateFirmBanking(
			new FirmBankingRequest.FirmBankingRequestId(requestFirmBankingJpaEntity.getRequestFirmBankingId() + ""),
			new FirmBankingRequest.FromBankName(requestFirmBankingJpaEntity.getFromBankName()),
			new FirmBankingRequest.FromBankAccountNumber(requestFirmBankingJpaEntity.getToBankAccountNumber()),
			new FirmBankingRequest.ToBankName(requestFirmBankingJpaEntity.getToBankName()),
			new FirmBankingRequest.ToBankAccountNumber(requestFirmBankingJpaEntity.getToBankAccountNumber()),
			new FirmBankingRequest.MoneyAmount(requestFirmBankingJpaEntity.getMoneyAmount()),
			new FirmBankingRequest.FirmBankingStatus(requestFirmBankingJpaEntity.getFirmBankingStatus()),
			uuid
		);
	}
}
