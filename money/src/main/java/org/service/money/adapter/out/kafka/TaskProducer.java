package org.service.money.adapter.out.kafka;

import java.util.Properties;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.service.common.RechargingMoneyTask;
import org.service.money.application.port.out.SendRechargingMoneyTaskPort;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Component
public class TaskProducer implements SendRechargingMoneyTaskPort {

	private final KafkaProducer<String, String> producer;

	private final String topic;

	public TaskProducer(
		@Value("${kafka.clusters.bootstrapservers}") String bootstrapServers,
		@Value("${task.topic}") String topic
	) {
		Properties props = new Properties();
		props.put("bootstrap.servers", bootstrapServers);
		props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
		props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");

		this.producer = new KafkaProducer<>(props);
		this.topic = topic;
	}


	@Override
	public void sendRechargingMoneyTask(RechargingMoneyTask task) {
		this.sendMessage(task.getTaskId(), task);
	}

	private void sendMessage(String key, RechargingMoneyTask value) {
		ObjectMapper mapper = new ObjectMapper();
		String jsonStringToProduce;
		try {
			jsonStringToProduce = mapper.writeValueAsString(value);
		} catch (JsonProcessingException e) {
			throw new RuntimeException("recharging money task json mapping failed");
		}

		ProducerRecord<String, String> record = new ProducerRecord<>(topic, key, jsonStringToProduce);
		producer.send(record, ((metadata, exception) -> {
			if(exception != null) {
				exception.printStackTrace();
			}
		}));
	}
}
