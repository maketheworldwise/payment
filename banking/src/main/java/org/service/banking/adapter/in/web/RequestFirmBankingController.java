package org.service.banking.adapter.in.web;

import org.service.banking.application.port.in.RequestFirmBankingCommand;
import org.service.banking.application.port.in.RequestFirmBankingUsecase;
import org.service.banking.application.port.in.UpdateFirmBankingCommand;
import org.service.banking.application.port.in.UpdateFirmBankingUsecase;
import org.service.banking.domain.FirmBankingRequest;
import org.service.common.WebAdapter;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

@WebAdapter
@RestController
@RequiredArgsConstructor
public class RequestFirmBankingController {

	private final RequestFirmBankingUsecase requestFirmBankingUsecase;
	private final UpdateFirmBankingUsecase updateFirmBankingUsecase;

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

	@PostMapping(path = "/firm/banking/request-eda")
	public ResponseEntity<Void> requestFirmBankingByEvent(@RequestBody RequestFirmBankingRequest request) {
		RequestFirmBankingCommand command = RequestFirmBankingCommand.builder()
			.fromBankName(request.getFromBankName())
			.fromBankAccountNumber(request.getFromBankAccountNumber())
			.toBankName(request.getToBankName())
			.toBankAccountNumber(request.getToBankAccountNumber())
			.build();

		requestFirmBankingUsecase.requestFirmBankingByEvent(command);

		return ResponseEntity.ok().build();
	}

	@PutMapping(path = "/firm/banking/update-eda")
	public ResponseEntity<Void> updateFirmBankingByEvent(@RequestBody UpdateFirmBankingRequest request) {
		UpdateFirmBankingCommand command = UpdateFirmBankingCommand.builder()
			.firmBankingRequestAggregateIdentifier(request.getFirmBankingRequestAggregateIdentifier())
			.status(request.getStatus())
			.build();

		updateFirmBankingUsecase.updateFirmBankingByEvent(command);

		return ResponseEntity.ok().build();
	}
}
