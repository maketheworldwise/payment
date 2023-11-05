package org.service.banking.application.service;

import java.util.UUID;

import org.axonframework.commandhandling.gateway.CommandGateway;
import org.service.banking.adapter.axon.command.CreateFirmBankingRequestCommand;
import org.service.banking.adapter.axon.command.UpdateFirmBankingRequestCommand;
import org.service.banking.adapter.out.external.bank.ExternalFirmBankingRequest;
import org.service.banking.adapter.out.external.bank.FirmBankingResult;
import org.service.banking.adapter.out.persistence.FirmBankingRequestJpaEntity;
import org.service.banking.adapter.out.persistence.FirmBankingRequestMapper;
import org.service.banking.application.port.in.RequestFirmBankingCommand;
import org.service.banking.application.port.in.RequestFirmBankingUsecase;
import org.service.banking.application.port.in.UpdateFirmBankingCommand;
import org.service.banking.application.port.in.UpdateFirmBankingUsecase;
import org.service.banking.application.port.out.RequestExternalFirmBankingPort;
import org.service.banking.application.port.out.RequestFirmBankingPort;
import org.service.banking.domain.FirmBankingRequest;
import org.service.common.Usecase;

import lombok.RequiredArgsConstructor;

@Usecase
@RequiredArgsConstructor
public class RequestFirmBankingService implements RequestFirmBankingUsecase, UpdateFirmBankingUsecase {

	private final RequestExternalFirmBankingPort requestExternalFirmBankingPort;

	private final RequestFirmBankingPort requestFirmBankingPort;

	private final FirmBankingRequestMapper firmBankingRequestMapper;

	private final CommandGateway commandGateway;

	@Override
	public FirmBankingRequest requestFirmBanking(RequestFirmBankingCommand command) {

		/*
		  <biz logic> - send money a to b

		  0. request firm banking information in request status
		  1. request firm banking to external bank
		  2. update firm banking information request status to completed
		 */

		FirmBankingRequestJpaEntity requestedEntity = requestFirmBankingPort.createRequestFirmBanking(
			new FirmBankingRequest.FromBankName(command.getFromBankName()),
			new FirmBankingRequest.FromBankAccountNumber(command.getToBankAccountNumber()),
			new FirmBankingRequest.ToBankName(command.getToBankName()),
			new FirmBankingRequest.ToBankAccountNumber(command.getToBankAccountNumber()),
			new FirmBankingRequest.MoneyAmount(command.getMoneyAmount()),
			new FirmBankingRequest.FirmBankingStatus(0),
			new FirmBankingRequest.AggregateIdentifier("")
		);

		// transaction uuid
		UUID randomUUID = UUID.randomUUID();
		requestedEntity.setUuid(randomUUID);

		FirmBankingResult firmBankingResult = requestExternalFirmBankingPort.requestExternalFirmBanking(
			new ExternalFirmBankingRequest(
				command.getFromBankName(),
				command.getFromBankAccountNumber(),
				command.getToBankName(),
				command.getToBankAccountNumber(),
				command.getMoneyAmount()
			));

		requestedEntity.setFirmBankingStatus(firmBankingResult.getResultCode());

		FirmBankingRequestJpaEntity jpaEntity = requestFirmBankingPort.modifyRequestFirmBanking(requestedEntity);

		return firmBankingRequestMapper.toDomain(jpaEntity, randomUUID);
	}

	@Override
	public void requestFirmBankingByEvent(RequestFirmBankingCommand request) {

		CreateFirmBankingRequestCommand command = CreateFirmBankingRequestCommand.builder()
			.fromBankName(request.getFromBankName())
			.fromBankAccountNumber(request.getFromBankAccountNumber())
			.toBankName(request.getToBankName())
			.toBankAccountNumber(request.getToBankAccountNumber())
			.moneyAmount(request.getMoneyAmount())
			.build();

		commandGateway.send(command).whenComplete((result, throwable) -> {
			if (throwable != null) {
				throw new RuntimeException(throwable);
			}

			System.out.println("create firm banking request db data: " + result);

			FirmBankingRequestJpaEntity requestedEntity = requestFirmBankingPort.createRequestFirmBanking(
				new FirmBankingRequest.FromBankName(command.getFromBankName()),
				new FirmBankingRequest.FromBankAccountNumber(command.getToBankAccountNumber()),
				new FirmBankingRequest.ToBankName(command.getToBankName()),
				new FirmBankingRequest.ToBankAccountNumber(command.getToBankAccountNumber()),
				new FirmBankingRequest.MoneyAmount(command.getMoneyAmount()),
				new FirmBankingRequest.FirmBankingStatus(0),
				new FirmBankingRequest.AggregateIdentifier(result.toString())
			);

			// transaction uuid
			UUID randomUUID = UUID.randomUUID();
			requestedEntity.setUuid(randomUUID);

			FirmBankingResult firmBankingResult = requestExternalFirmBankingPort.requestExternalFirmBanking(
				new ExternalFirmBankingRequest(
					command.getFromBankName(),
					command.getFromBankAccountNumber(),
					command.getToBankName(),
					command.getToBankAccountNumber(),
					command.getMoneyAmount()
				));

			requestedEntity.setFirmBankingStatus(firmBankingResult.getResultCode());

			FirmBankingRequestJpaEntity jpaEntity = requestFirmBankingPort.modifyRequestFirmBanking(requestedEntity);

		});
	}

	@Override
	public void updateFirmBankingByEvent(UpdateFirmBankingCommand request) {

		UpdateFirmBankingRequestCommand command = UpdateFirmBankingRequestCommand.builder()
			.aggregateIdentifier(request.getFirmBankingRequestAggregateIdentifier())
			.firmBankingStatus(request.getStatus())
			.build();

		commandGateway.send(command).whenComplete((result, throwable) -> {
			if (throwable != null) {
				throw new RuntimeException(throwable);
			}

			System.out.println("update firm banking request status db data: " + result);

			// todo: can be replaced to axon event store data whether than using rdb port
			FirmBankingRequestJpaEntity jpaEntity = requestFirmBankingPort.getFirmBankingRequest(
				new FirmBankingRequest.AggregateIdentifier(result.toString())
			);

			jpaEntity.setFirmBankingStatus(request.getStatus());

			requestFirmBankingPort.modifyRequestFirmBanking(jpaEntity);

			// after status change, needs external bank communication
		});
	}
}
