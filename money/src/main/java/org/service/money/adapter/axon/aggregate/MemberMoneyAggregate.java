package org.service.money.adapter.axon.aggregate;

import static org.axonframework.modelling.command.AggregateLifecycle.*;

import java.util.UUID;

import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.spring.stereotype.Aggregate;
import org.service.money.adapter.axon.command.IncreaseMemberMoneyCommand;
import org.service.money.adapter.axon.command.MemberMoneyCreatedCommand;
import org.service.money.adapter.axon.event.IncreaseMemberMoneyEvent;
import org.service.money.adapter.axon.event.MemberMoneyCreatedEvent;

import lombok.Data;

@Aggregate
@Data
public class MemberMoneyAggregate {

	@AggregateIdentifier
	private String id;

	private Long membershipId;

	private int balance;

	public MemberMoneyAggregate() {
	}

	@CommandHandler
	public MemberMoneyAggregate(MemberMoneyCreatedCommand command) {
		System.out.println("MemberMoneyCreatedCommand Handler");

		apply(new MemberMoneyCreatedEvent(command.getMembershipId()));
	}

	@CommandHandler
	public String handle(IncreaseMemberMoneyCommand command) {
		System.out.println("IncreaseMemberMoneyCommand Handler");
		id = command.getAggregateIdentifier();

		// store event
		apply(new IncreaseMemberMoneyEvent(id, command.getMembershipId(), command.getAmount()));
		return id;
	}

	@EventSourcingHandler
	public void on(MemberMoneyCreatedEvent event) {
		System.out.println("MemberMoneyCreatedCommand Handler");
		this.id = UUID.randomUUID().toString();
		this.membershipId = Long.parseLong(event.getTargetMembershipId());
		this.balance = 0;
	}

	@EventSourcingHandler
	public void on(IncreaseMemberMoneyEvent event) {
		System.out.println("IncreaseMemberMoneyCommand Handler");
		this.id = event.getAggregateIdentifier();
		this.membershipId = Long.parseLong(event.getTargetMembershipId());
		this.balance = event.getAmount();
	}

}
