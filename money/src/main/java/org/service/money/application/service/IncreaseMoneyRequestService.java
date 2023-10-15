package org.service.money.application.service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.axonframework.commandhandling.gateway.CommandGateway;
import org.service.common.CountDownLatchManager;
import org.service.common.RechargingMoneyTask;
import org.service.common.SubTask;
import org.service.common.Usecase;
import org.service.money.adapter.axon.command.IncreaseMemberMoneyCommand;
import org.service.money.adapter.out.persistence.MemberMoneyJpaEntity;
import org.service.money.adapter.out.persistence.MoneyChangingRequestJpaEntity;
import org.service.money.adapter.out.persistence.MoneyChangingRequestMapper;
import org.service.money.application.port.in.IncreaseMoneyRequestCommand;
import org.service.money.application.port.in.IncreaseMoneyRequestUsecase;
import org.service.money.application.port.out.GetMemberMoneyPort;
import org.service.money.application.port.out.GetMembershipPort;
import org.service.money.application.port.out.MembershipStatus;
import org.service.money.application.port.out.MoneyChangingRequestPort;
import org.service.money.application.port.out.SendRechargingMoneyTaskPort;
import org.service.money.domain.MemberMoney;
import org.service.money.domain.MoneyChangingRequest;

import lombok.RequiredArgsConstructor;

@Usecase
@RequiredArgsConstructor
public class IncreaseMoneyRequestService implements IncreaseMoneyRequestUsecase {

	private final GetMembershipPort getMembershipPort;

	private final MoneyChangingRequestPort moneyChangingRequestPort;

	private final MoneyChangingRequestMapper moneyChangingRequestMapper;

	private final SendRechargingMoneyTaskPort sendRechargingMoneyTaskPort;

	private final CountDownLatchManager countDownLatchManager;

	private final GetMemberMoneyPort getMemberMoneyPort;
	private final CommandGateway commandGateway;

	@Override
	public MoneyChangingRequest increaseMoneyRequest(IncreaseMoneyRequestCommand command) {

		/*
		  <biz logic>

		  0. check membership (membership service)
		  1. check if membership bank account available (banking service)
		  2. check if membership bank account has enough money (banking service)
		  3. check if corporate bank account available (banking service)
		  4. create money changing request in request status (money service)
		  5. record increase request action to money request history (money service)
		  6. request firm banking service (membership bank account to corporate bank account) (banking service)
		  7. update money change request status according to the result of firm banking (money service)
		  8. increase members money

		 */
		// 0.
		MembershipStatus membershipStatus = getMembershipPort.getMembership(command.getTargetMembershipId());
		if (!membershipStatus.isValid()) {
			throw new RuntimeException("invalid membership");
		}

		// 4.
		MoneyChangingRequestJpaEntity requestedEntity = moneyChangingRequestPort.createMoneyChangingRequest(
			new MoneyChangingRequest.TargetMembershipId(command.getTargetMembershipId()),
			new MoneyChangingRequest.MoneyChangingType(0), // increase
			new MoneyChangingRequest.MoneyChangingAmount(command.getAmount()),
			new MoneyChangingRequest.MoneyChangingStatus(0), // requested
			new MoneyChangingRequest.Uuid(UUID.randomUUID())
		);

		// 7.
		requestedEntity.setChangingMoneyStatus(1); // succeeded
		MoneyChangingRequestJpaEntity jpaEntity = moneyChangingRequestPort.modifyMoneyChangingRequest(requestedEntity);

		// 8.
		MemberMoneyJpaEntity memberMoneyJpaEntity = moneyChangingRequestPort.increaseMemberMoney(
			new MemberMoney.MembershipId(command.getTargetMembershipId()),
			command.getAmount()
		);
		if (memberMoneyJpaEntity == null) {
			throw new RuntimeException("member money increase failed");
		}

		return moneyChangingRequestMapper.toDomain(jpaEntity);
	}

	@Override
	public MoneyChangingRequest increaseMoneyRequestAsync(IncreaseMoneyRequestCommand command) {

		/*
		  <biz logic>

		  0. create subtask, task
		  		- tasks are created to "validate" each service with a specific membershipId
		  		- so, processing amount of money to firm banking service will be skipped
		  1. kafka cluster produce
		  2. produce wait
		  		2-1. if subtask, status valid, produce task result
		  3. task result consume
		  4. if consumed, execute biz logic

		  <summary - double queueing>

		  1. produce task.topic (TaskProducer, wait count down latch)
		  2. consume task.topic (TaskConsumer)
		  3. validate and produce task.result.topic (TaskConsumer)
		  4. consume task.result.topic (RechargingMoneyResultConsumer, sleep about 6sec just to check and count down latch)
		  5. biz logic
		 */

		// 0.
		// membership validation
		SubTask validMemberTask = SubTask.builder()
			.membershipId(command.getTargetMembershipId())
			.subTaskName("validMemberTask: validate membership")
			.taskType("membership")
			.status("ready")
			.build();

		// banking account validation
		SubTask validBankAccount = SubTask.builder()
			.membershipId(command.getTargetMembershipId())
			.subTaskName("validMemberTask: validate bank account")
			.taskType("banking")
			.status("ready")
			.build();

		List<SubTask> subTaskList = new ArrayList<>();
		subTaskList.add(validMemberTask);
		subTaskList.add(validBankAccount);

		RechargingMoneyTask task = RechargingMoneyTask.builder()
			.taskId(UUID.randomUUID().toString())
			.taskName("increase money request async")
			.subTaskList(subTaskList)
			.moneyAmount(command.getAmount())
			.membershipId(command.getTargetMembershipId())
			.toBankName("bank")
			.toBankAccountNumber("1234")
			.build();

		// 1.
		sendRechargingMoneyTaskPort.sendRechargingMoneyTask(task);
		countDownLatchManager.addCountDownLatch(task.getTaskId());

		// 2.
		try {
			// 2-1.
			countDownLatchManager.getCountDownLatch(task.getTaskId()).await();
		} catch (InterruptedException e) {
			throw new RuntimeException(e);
		}

		// 3.
		String rechargingMoneyResult = countDownLatchManager.getDataForKey(task.getTaskId());
		if (rechargingMoneyResult.equals("failed")) {
			throw new RuntimeException("task producing, consuming failed");
		}

		// 4.
		MoneyChangingRequestJpaEntity requestedEntity = moneyChangingRequestPort.createMoneyChangingRequest(
			new MoneyChangingRequest.TargetMembershipId(command.getTargetMembershipId()),
			new MoneyChangingRequest.MoneyChangingType(0), // increase
			new MoneyChangingRequest.MoneyChangingAmount(command.getAmount()),
			new MoneyChangingRequest.MoneyChangingStatus(0), // requested
			new MoneyChangingRequest.Uuid(UUID.randomUUID())
		);

		requestedEntity.setChangingMoneyStatus(1); // succeeded
		MoneyChangingRequestJpaEntity jpaEntity = moneyChangingRequestPort.modifyMoneyChangingRequest(requestedEntity);

		MemberMoneyJpaEntity memberMoneyJpaEntity = moneyChangingRequestPort.increaseMemberMoney(
			new MemberMoney.MembershipId(command.getTargetMembershipId()),
			command.getAmount()
		);
		if (memberMoneyJpaEntity == null) {
			throw new RuntimeException("member money increase failed");
		}

		return moneyChangingRequestMapper.toDomain(jpaEntity);
	}

	@Override
	public void increaseMoneyChangingRequestByEvent(IncreaseMoneyRequestCommand command) {
		MemberMoneyJpaEntity memberMoneyJpaEntity = getMemberMoneyPort.getMemberMoney(
			new MemberMoney.MembershipId(command.getTargetMembershipId())
		);
		commandGateway.send(IncreaseMemberMoneyCommand.builder()
			.aggregateIdentifier(memberMoneyJpaEntity.getAggregateIdentifier())
			.membershipId(command.getTargetMembershipId())
			.amount(command.getAmount())
			.build()
		).whenComplete((result, throwable) -> {
			// after event sourcing handler is executed
			if (throwable != null) {
				System.out.println(throwable.getMessage());
				throw new RuntimeException(throwable);
			}

			System.out.println(result.toString());
			moneyChangingRequestPort.increaseMemberMoney(
				new MemberMoney.MembershipId(command.getTargetMembershipId()),
				command.getAmount()
			);
		});
	}
}
