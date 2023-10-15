package org.service.money.application.service;

import org.axonframework.commandhandling.gateway.CommandGateway;
import org.service.common.Usecase;
import org.service.money.adapter.axon.command.MemberMoneyCreatedCommand;
import org.service.money.application.port.in.CreateMemberMoneyCommand;
import org.service.money.application.port.in.CreateMemberMoneyUsecase;
import org.service.money.application.port.out.CreateMemberMoneyPort;
import org.service.money.domain.MemberMoney;

import lombok.RequiredArgsConstructor;

@Usecase
@RequiredArgsConstructor
public class CreateMemberMoneyService implements CreateMemberMoneyUsecase {

	private final CommandGateway commandGateway;

	private final CreateMemberMoneyPort createMemberMoneyPort;

	@Override
	public void createMemberMoney(CreateMemberMoneyCommand command) {
		MemberMoneyCreatedCommand axonCommand = new MemberMoneyCreatedCommand(command.getMembershipId());
		commandGateway.send(axonCommand).whenComplete((result, throwable)-> {
			// after event sourcing handler is executed
			if (throwable != null) {
				System.out.println(throwable.getMessage());
				throw new RuntimeException(throwable);
			}

			System.out.println(result.toString());

			createMemberMoneyPort.createMemberMoney(
				new MemberMoney.MembershipId(command.getMembershipId()),
				new MemberMoney.MoneyAggregateIdentifier(result.toString())
			);
		});
	}
}
