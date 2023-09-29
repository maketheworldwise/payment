package org.service.banking.adapter.in.web;

import org.service.banking.application.port.in.RequestFirmBankingCommand;
import org.service.banking.application.port.in.RequestFirmBankingUsecase;
import org.service.banking.domain.FirmBankingRequest;
import org.service.common.WebAdapter;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

@WebAdapter
@RestController
@RequiredArgsConstructor
public class RequestFirmBankingController {

	private final RequestFirmBankingUsecase requestFirmBankingUsecase;

	@PostMapping(path = "/firm/banking/request")
	public ResponseEntity<FirmBankingRequest> requestFirmBanking(@RequestBody RequestFirmBankingRequest request) {
		RequestFirmBankingCommand command = RequestFirmBankingCommand.builder()
			.fromBankName(request.getFromBankName())
			.fromBankAccountNumber(request.getFromBankAccountNumber())
			.toBankName(request.getToBankName())
			.toBankAccountNumber(request.getToBankAccountNumber())
			.build();

		return ResponseEntity.ok(requestFirmBankingUsecase.requestFirmBanking(command));
	}
}
