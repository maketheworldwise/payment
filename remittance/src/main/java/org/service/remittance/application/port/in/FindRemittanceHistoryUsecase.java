package org.service.remittance.application.port.in;

import java.util.List;

import org.service.remittance.domain.RemittanceRequest;

public interface FindRemittanceHistoryUsecase {
	List<RemittanceRequest> findRemittanceHistory(FindRemittanceHistoryCommand command);
}
