package org.service.remittance.adapter.in.web;

import java.util.List;

import org.service.common.WebAdapter;
import org.service.remittance.application.port.in.FindRemittanceHistoryCommand;
import org.service.remittance.application.port.in.FindRemittanceHistoryUsecase;
import org.service.remittance.domain.RemittanceRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

@WebAdapter
@RestController
@RequiredArgsConstructor
public class FindRemittanceHistoryController {

	private final FindRemittanceHistoryUsecase findRemittanceHistoryUsecase;

	@GetMapping(path = "/remittance/{membershipId}")
	public ResponseEntity<List<RemittanceRequest>> findRemittanceHistory(
		@PathVariable("membershipId") String membershipId
	) {
		FindRemittanceHistoryCommand command = FindRemittanceHistoryCommand.builder()
			.membershipId(membershipId)
			.build();

		return ResponseEntity.ok(findRemittanceHistoryUsecase.findRemittanceHistory(command));
	}
}
