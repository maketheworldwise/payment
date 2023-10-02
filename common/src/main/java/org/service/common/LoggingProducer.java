package org.service.common;

import java.util.Properties;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class LoggingProducer {
	private final KafkaProducer<String, String> producer;

	private final String topic;

	public LoggingProducer(
		// each individual modules environments
		@Value("${kafka.clusters.bootstrapservers}") String bootstrapServers,
		// each individual modules environments
		@Value("${logging.topic}") String topic
	) {
		// Producer Initialization ';'
		Properties props = new Properties();

		// kafka:29092 (brokers)
		props.put("bootstrap.servers", bootstrapServers);

		// producing data (key, value)
		props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
		props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");

		this.producer = new KafkaProducer<>(props);
		this.topic = topic;
	}


	public void sendMessage(String key, String value) {
		// producing kafka cluster <key, value>
		ProducerRecord<String, String> record = new ProducerRecord<>(topic, key, value);
		producer.send(record, ((metadata, exception) -> {
			if(exception != null) {
				exception.printStackTrace();
			}
		}));
	}
}
