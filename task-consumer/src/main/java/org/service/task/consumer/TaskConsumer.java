package org.service.task.consumer;

import java.time.Duration;
import java.util.Collections;
import java.util.Properties;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.service.common.RechargingMoneyTask;
import org.service.common.SubTask;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Component
public class TaskConsumer {
	private final KafkaConsumer<String, String> consumer;

	private final TaskResultProducer taskResultProducer;

	public TaskConsumer(
		@Value("${kafka.clusters.bootstrapservers}") String bootstrapServers,
		@Value("${task.topic}") String topic,
		TaskResultProducer taskResultProducer
	) {
		Properties props = new Properties();

		props.put("bootstrap.servers", bootstrapServers);

		props.put("group.id", "consumer-group");
		props.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
		props.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");

		this.consumer = new KafkaConsumer<>(props);
		this.consumer.subscribe(Collections.singletonList(topic));
		this.taskResultProducer = taskResultProducer;

		Thread consumerThread = new Thread(() -> {
			try {
				ObjectMapper mapper = new ObjectMapper();

				while(true) {
					ConsumerRecords<String, String> records = consumer.poll(Duration.ofSeconds(1));

					for(ConsumerRecord<String, String> record : records) {
						// get task
						RechargingMoneyTask task;
						try {
							task = mapper.readValue(record.value(), RechargingMoneyTask.class);
						} catch (JsonProcessingException e) {
							throw new RuntimeException("recharging money task class mapping failed");
						}

						// run task
						for(SubTask subTask: task.getSubTaskList()) {

							// todo: validate membership, banking

							// all tasks done
							subTask.setStatus("success");
						}

						// produce task result
						this.taskResultProducer.sendTaskResult(task.getTaskId(), task);
					}
				}
			} finally {
				consumer.close();
			}
		});

		consumerThread.start();
	}

}
