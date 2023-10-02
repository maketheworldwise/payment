package org.service.logging.consumer;

import java.time.Duration;
import java.util.Collections;
import java.util.Properties;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class LoggingConsumer {
	private final KafkaConsumer<String, String> consumer;

	public LoggingConsumer(
		// each individual modules environments
		@Value("${kafka.clusters.bootstrapservers}") String bootstrapServers,
		// each individual modules environments
		@Value("${logging.topic}") String topic
	) {
		Properties props = new Properties();

		props.put("bootstrap.servers", bootstrapServers);

		props.put("group.id", "consumer-group");
		props.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
		props.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");

		this.consumer = new KafkaConsumer<>(props);
		this.consumer.subscribe(Collections.singletonList(topic));

		Thread consumerThread = new Thread(() -> {
			try {
				while(true) {
					ConsumerRecords<String, String> records = consumer.poll(Duration.ofSeconds(1));
					for(ConsumerRecord<String, String> record : records) {
						System.out.println("received message: " + record.value());
					}
				}
			} finally {
				consumer.close();
			}
		});

		consumerThread.start();
	}

}
