package com.inn.kafkaConfiguration;

import static net.logstash.logback.argument.StructuredArguments.kv;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import com.inn.dto.EventMessageDTO;
import com.inn.notificationConstants.NotificationServiceConstant;

@Service
public class NotificationProducer {

	private static final Logger logger = LoggerFactory.getLogger(NotificationProducer.class);
	
	@Value("${kafka.topics.notification}")
	private String notificationKafkaTopic;

	private final KafkaTemplate<String, EventMessageDTO> kafkaTemplate;

	public NotificationProducer(KafkaTemplate<String, EventMessageDTO> kafkaTemplate) {
		this.kafkaTemplate = kafkaTemplate;
	}

	public void eventPublisher(EventMessageDTO message) {
		logger.info(NotificationServiceConstant.INSIDE_THE_METHOD + "eventPublisher");
		kafkaTemplate.send(notificationKafkaTopic, message);
		logger.info("Event Published to RoomBillz Service:{}",	kv("Message", message.getMessage()));
	}
}