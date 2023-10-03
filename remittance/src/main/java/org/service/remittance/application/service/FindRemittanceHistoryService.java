package org.service.remittance.application.service;

import java.util.List;

import org.service.common.Usecase;
import org.service.remittance.adapter.out.persistence.RemittanceRequestMapper;
import org.service.remittance.application.port.out.FindRemittanceHistoryPort;
import org.service.remittance.application.port.in.FindRemittanceHistoryCommand;
import org.service.remittance.application.port.in.FindRemittanceHistoryUsecase;
import org.service.remittance.domain.RemittanceRequest;

import lombok.RequiredArgsConstructor;

@Usecase
@RequiredArgsConstructor
public class FindRemittanceHistoryService implements FindRemittanceHistoryUsecase {

	private final FindRemittanceHistoryPort findRemittanceHistoryPort;

	private final RemittanceRequestMapper remittanceRequestMapper;

	@Override
	public List<RemittanceRequest> findRemittanceHistory(FindRemittanceHistoryCommand command) {
		// todo

		findRemittanceHistoryPort.findRemittanceHistory(command.getMembershipId());

		return null;
	}
}
