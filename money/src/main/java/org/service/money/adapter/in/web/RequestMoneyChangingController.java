package org.service.money.adapter.in.web;

import org.service.common.WebAdapter;
import org.service.money.application.port.in.IncreaseMoneyRequestCommand;
import org.service.money.application.port.in.IncreaseMoneyRequestUsecase;
import org.service.money.domain.MoneyChangingRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

@WebAdapter
@RestController
@RequiredArgsConstructor
public class RequestMoneyChangingController {

	private final IncreaseMoneyRequestUsecase increaseMoneyRequestUsecase;

	// private final DecreaseMoneyRequestUsecase decreaseMoneyRequestUsecase;

	private final MoneyChangingResultDetailMapper moneyChangingResultDetailMapper;

	@PostMapping(path = "/money/increase/request")
	public ResponseEntity<MoneyChangingResultDetail> increaseMoneyRequest(
		@RequestBody IncreaseMoneyChangingRequest request
	) {
		IncreaseMoneyRequestCommand command = IncreaseMoneyRequestCommand.builder()
			.targetMembershipId(request.getTargetMembershipId())
			.amount(request.getAmount())
			.build();

		MoneyChangingRequest moneyChangingRequest = increaseMoneyRequestUsecase.increaseMoneyRequest(command);

		return ResponseEntity.ok(moneyChangingResultDetailMapper.toDetail(moneyChangingRequest));
	}

	// @PostMapping(path = "/money/decrease/request")
	// public ResponseEntity<MoneyChangingResultDetail> decreaseMoneyRequest(@RequestBody DecreaseMoneyChangingRequest request) {
	// 	DecreaseMoneyRequestCommand command = DecreaseMoneyRequestCommand.builder()
	// 		.targetMembershipId(request.getTargetMembershipId())
	// 		.amount(request.getAmount())
	// 		.build();
	//
	// 	MoneyChangingRequest moneyChangingRequest = decreaseMoneyRequestUsecase.decreaseMoneyRequest(command);
	//
	// 	return ResponseEntity.ok(moneyChangingResultDetailMapper.toDetail(moneyChangingRequest));
	// }

}