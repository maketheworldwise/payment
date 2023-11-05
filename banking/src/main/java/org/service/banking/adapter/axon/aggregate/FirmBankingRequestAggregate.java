package org.service.banking.adapter.axon.aggregate;

import static org.axonframework.modelling.command.AggregateLifecycle.*;

import java.util.UUID;

import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.spring.stereotype.Aggregate;
import org.service.banking.adapter.axon.command.CreateFirmBankingRequestCommand;
import org.service.banking.adapter.axon.command.UpdateFirmBankingRequestCommand;
import org.service.banking.adapter.axon.event.FirmBankingRequestCreatedEvent;
import org.service.banking.adapter.axon.event.FirmBankingRequestUpdatedEvent;

import lombok.Data;

@Aggregate()
@Data
public class FirmBankingRequestAggregate {

	@AggregateIdentifier
	private String id;

	private String fromBankName;

	private String fromBankAccountNumber;

	private String toBankName;

	private String toBankAccountNumber;

	private int moneyAmount;

	private int firmBankingStatus;

	@CommandHandler
	public FirmBankingRequestAggregate(CreateFirmBankingRequestCommand command) {
		System.out.println("CreateFirmBankingRequestCommand Handler");

		apply(new FirmBankingRequestCreatedEvent(
				command.getFromBankName(),
				command.getFromBankAccountNumber(),
				command.getToBankName(),
				command.getToBankAccountNumber(),
				command.getMoneyAmount()
			)
		);
	}

	@CommandHandler
	public String handle(UpdateFirmBankingRequestCommand command) {
		System.out.println("UpdateFirmBankingRequestCommand Handler");

		id = command.getAggregateIdentifier();
		apply(new FirmBankingRequestUpdatedEvent(
				command.getFirmBankingStatus()
			)
		);

		return id;
	}

	@EventSourcingHandler
	public void on(FirmBankingRequestCreatedEvent event) {
		System.out.println("FirmBankingRequestCreatedEvent Handler");

		id = UUID.randomUUID().toString();
		fromBankName = event.getFromBankName();
		fromBankAccountNumber = event.getFromBankAccountNumber();
		toBankName = event.getToBankName();
		toBankAccountNumber = event.getToBankAccountNumber();
		moneyAmount = event.getMoneyAmount();
	}

	@EventSourcingHandler
	public void on(FirmBankingRequestUpdatedEvent event) {
		System.out.println("FirmBankingRequestUpdatedEvent Handler");

		firmBankingStatus = event.getFirmBankingStatus();
	}

	public FirmBankingRequestAggregate() {
	}
}
