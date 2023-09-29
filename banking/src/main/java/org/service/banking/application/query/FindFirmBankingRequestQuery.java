package org.service.banking.application.query;

import org.service.banking.adapter.out.persistence.SpringDataFirmBankingRequestQueryRepository;
import org.service.banking.application.query.ro.FirmBankingRequestRo;
import org.service.common.Query;

import lombok.RequiredArgsConstructor;

@Query
@RequiredArgsConstructor
public class FindFirmBankingRequestQuery {

	private final SpringDataFirmBankingRequestQueryRepository firmBankingRequestQuery;

	public FirmBankingRequestRo getFirmBankingRequest(String firmBankingRequestId) {
		return firmBankingRequestQuery.findById(Long.parseLong(firmBankingRequestId))
			.map(FirmBankingRequestRo::new)
			.orElseThrow(() -> new IllegalArgumentException("firm banking request not found"));
	}
}
