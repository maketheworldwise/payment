package org.service.remittance.application.port.in;

import org.service.remittance.domain.RemittanceRequest;

public interface RequestRemittanceUsecase {
	RemittanceRequest requestRemittance(RequestRemittanceCommand command);
}
