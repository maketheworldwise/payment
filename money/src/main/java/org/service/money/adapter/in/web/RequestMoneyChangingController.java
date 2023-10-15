package org.service.money.adapter.in.web;

import org.service.common.WebAdapter;
import org.service.money.application.port.in.CreateMemberMoneyCommand;
import org.service.money.application.port.in.CreateMemberMoneyUsecase;
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

	private final CreateMemberMoneyUsecase createMemberMoneyUsecase;

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

	@PostMapping(path = "/money/increase/request/async")
	public ResponseEntity<MoneyChangingResultDetail> increaseMoneyRequestAsync(
		@RequestBody IncreaseMoneyChangingRequest request
	) {
		IncreaseMoneyRequestCommand command = IncreaseMoneyRequestCommand.builder()
			.targetMembershipId(request.getTargetMembershipId())
			.amount(request.getAmount())
			.build();

		MoneyChangingRequest moneyChangingRequest = increaseMoneyRequestUsecase.increaseMoneyRequestAsync(command);

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

	@PostMapping(path = "/money/create-member-money")
	void createMemberMoney(@RequestBody CreateMemberMoneyRequest request) {
		createMemberMoneyUsecase.createMemberMoney(CreateMemberMoneyCommand.builder()
			.membershipId(request.getTargetMembershipId())
			.build());
	}

	@PostMapping(path = "/money/increase-eda")
	void increaseMoneyChangingRequestByEvent(@RequestBody IncreaseMoneyChangingRequest request) {
		IncreaseMoneyRequestCommand command = IncreaseMoneyRequestCommand.builder()
			.targetMembershipId(request.getTargetMembershipId())
			.amount(request.getAmount())
			.build();

		increaseMoneyRequestUsecase.increaseMoneyChangingRequestByEvent(command);
	}

}
