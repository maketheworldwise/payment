package org.service.banking.adapter.out.persistence;

import java.util.UUID;

import org.service.banking.application.port.out.RequestFirmBankingPort;
import org.service.banking.domain.FirmBankingRequest;
import org.service.common.PersistenceAdapter;

import lombok.RequiredArgsConstructor;

@PersistenceAdapter
@RequiredArgsConstructor
public class FirmBankingRequestPersistenceAdapter implements RequestFirmBankingPort {

	private final SpringDataFirmBankingRequestRepository firmBankingRequestRepository;

	@Override
	public FirmBankingRequestJpaEntity createRequestFirmBanking(
		FirmBankingRequest.FromBankName fromBankName,
		FirmBankingRequest.FromBankAccountNumber fromBankAccountNumber,
		FirmBankingRequest.ToBankName toBankName,
		FirmBankingRequest.ToBankAccountNumber toBankAccountNumber,
		FirmBankingRequest.MoneyAmount moneyAmount,
		FirmBankingRequest.FirmBankingStatus firmBankingStatus
	) {
		return firmBankingRequestRepository.save(
			new FirmBankingRequestJpaEntity(
				fromBankName.getFromBankName(),
				fromBankAccountNumber.getFromBankAccountNumber(),
				toBankName.getToBankName(),
				toBankAccountNumber.getToBankAccountNumber(),
				moneyAmount.getMoneyAmount(),
				firmBankingStatus.getFirmBankingStatus(),
				UUID.randomUUID()
			)
		);
	}

	@Override
	public FirmBankingRequestJpaEntity modifyRequestFirmBanking(FirmBankingRequestJpaEntity jpaEntity) {
		return firmBankingRequestRepository.save(jpaEntity);
	}
}
