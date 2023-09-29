package org.service.banking.application.port.out;

import org.service.banking.adapter.out.external.bank.ExternalFirmBankingRequest;
import org.service.banking.adapter.out.external.bank.FirmBankingResult;

public interface RequestExternalFirmBankingPort {

	FirmBankingResult requestExternalFirmBanking(ExternalFirmBankingRequest request);
}
