package org.service.banking.application.port.in;

import org.service.banking.domain.FirmBankingRequest;

public interface RequestFirmBankingUsecase {
	FirmBankingRequest requestFirmBanking(RequestFirmBankingCommand command);
}
