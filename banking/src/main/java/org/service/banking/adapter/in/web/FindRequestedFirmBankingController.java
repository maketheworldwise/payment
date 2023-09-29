package org.service.banking.adapter.in.web;

import org.service.banking.application.query.FindFirmBankingRequestQuery;
import org.service.banking.application.query.ro.FirmBankingRequestRo;
import org.service.common.WebAdapter;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

@WebAdapter
@RestController
@RequiredArgsConstructor
public class FindRequestedFirmBankingController {

	private final FindFirmBankingRequestQuery findFirmBankingRequestQuery;

	@GetMapping(path = "/firm/banking/request/{firmBankingRequestId}")
	public ResponseEntity<FirmBankingRequestRo> getFirmBankingRequest(@PathVariable String firmBankingRequestId) {
		return ResponseEntity.ok(findFirmBankingRequestQuery.getFirmBankingRequest(firmBankingRequestId));
	}
}
