package org.service.remittance.adapter.in.web;

import org.service.common.WebAdapter;
import org.service.remittance.application.port.in.RequestRemittanceCommand;
import org.service.remittance.application.port.in.RequestRemittanceUsecase;
import org.service.remittance.domain.RemittanceRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

@WebAdapter
@RestController
@RequiredArgsConstructor
public class RequestRemittanceController {

	private final RequestRemittanceUsecase requestRemittanceUsecase;

	@PostMapping(path = "/remittance/request")
	public ResponseEntity<RemittanceRequest> requestRemittance(@RequestBody RequestRemittanceRequest request) {
		RequestRemittanceCommand command = RequestRemittanceCommand.builder()
			.fromMembershipId(request.fromMembershipId)
			.toMembershipId(request.toMembershipId)
			.toBankName(request.toBankName)
			.toBankAccountNumber(request.toBankAccountNumber)
			.remittanceType(request.getRemittanceType())
			.moneyAmount(request.moneyAmount)
			.build();

		return ResponseEntity.ok(requestRemittanceUsecase.requestRemittance(command));
	}
}
