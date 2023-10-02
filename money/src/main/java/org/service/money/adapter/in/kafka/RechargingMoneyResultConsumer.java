package org.service.money.adapter.in.kafka;

import java.time.Duration;
import java.util.Collections;
import java.util.List;
import java.util.Properties;

import javax.validation.constraints.NotNull;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.service.common.CountDownLatchManager;
import org.service.common.LoggingProducer;
import org.service.common.RechargingMoneyTask;
import org.service.common.SubTask;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Component
public class RechargingMoneyResultConsumer {
	private final KafkaConsumer<String, String> consumer;

	private final LoggingProducer loggingProducer;

	@NotNull
	private final CountDownLatchManager countDownLatchManager;

	public RechargingMoneyResultConsumer(
		@Value("${kafka.clusters.bootstrapservers}") String bootstrapServers,
		@Value("${task.result.topic}") String topic,
		LoggingProducer loggingProducer,
		CountDownLatchManager countDownLatchManager
	) {

		Properties props = new Properties();

		props.put("bootstrap.servers", bootstrapServers);

		props.put("group.id", "consumer-group");
		props.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
		props.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");

		this.consumer = new KafkaConsumer<>(props);
		this.consumer.subscribe(Collections.singletonList(topic));
		this.loggingProducer = loggingProducer;
		this.countDownLatchManager = countDownLatchManager;

		Thread consumerThread = new Thread(() -> {
			try {
				ObjectMapper mapper = new ObjectMapper();

				while(true) {
					ConsumerRecords<String, String> records = consumer.poll(Duration.ofSeconds(1));

					for(ConsumerRecord<String, String> record : records) {
						System.out.println("received message: " + record.key() + " / " + record.value());

						// all tasks done

						// sleep just to check
						try {
							Thread.sleep(3000);
						} catch (InterruptedException e) {
							throw new RuntimeException("thread sleep failed");
						}

						RechargingMoneyTask task;
						try {
							task = mapper.readValue(record.value(), RechargingMoneyTask.class);
						} catch (JsonProcessingException e) {
							throw new RuntimeException("recharging money task class mapping failed");
						}

						List<SubTask> subTaskList = task.getSubTaskList();

						boolean taskResult = true;
						// validate membership
						// validate banking
						for (SubTask subTask : subTaskList) {
							// even if it fails just once, it is considered a failed task
							if (subTask.getStatus().equals("fail")) {
								taskResult = false;
								break;
							}
						}

						if(taskResult) {
							this.loggingProducer.sendMessage(task.getTaskId(), "task success");
							this.countDownLatchManager.setDataForKey(task.getTaskId(), "success");
						} else {
							this.loggingProducer.sendMessage(task.getTaskId(), "task failed");
							this.countDownLatchManager.setDataForKey(task.getTaskId(), "failed");
						}

						// sleep just to check
						try {
							Thread.sleep(3000);
						} catch (InterruptedException e) {
							throw new RuntimeException("thread sleep failed");
						}

						// make await thread free
						this.countDownLatchManager.getCountDownLatch(task.getTaskId()).countDown();
					}
				}
			} finally {
				consumer.close();
			}
		});

		consumerThread.start();
	}

}
