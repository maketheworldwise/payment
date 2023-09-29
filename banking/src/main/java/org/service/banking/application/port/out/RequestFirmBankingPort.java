package org.service.banking.application.port.out;

import org.service.banking.adapter.out.persistence.FirmBankingRequestJpaEntity;
import org.service.banking.domain.FirmBankingRequest;

public interface RequestFirmBankingPort {
	FirmBankingRequestJpaEntity createRequestFirmBanking(
		FirmBankingRequest.FromBankName fromBankName,
		FirmBankingRequest.FromBankAccountNumber fromBankAccountNumber,
		FirmBankingRequest.ToBankName toBankName,
		FirmBankingRequest.ToBankAccountNumber toBankAccountNumber,
		FirmBankingRequest.MoneyAmount moneyAmount,
		FirmBankingRequest.FirmBankingStatus firmBankingStatus
	);

	FirmBankingRequestJpaEntity modifyRequestFirmBanking(FirmBankingRequestJpaEntity jpaEntity);
}
