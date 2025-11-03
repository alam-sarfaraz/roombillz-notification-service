package com.inn.kafkaConfiguration;



import static net.logstash.logback.argument.StructuredArguments.kv;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import com.inn.dto.EventMessage;
import com.inn.notificationConstants.NotificationServiceConstant;

@Service
public class NotificationConsumer {
	
	private static final Logger logger = LoggerFactory.getLogger(NotificationConsumer.class);

	@KafkaListener(topics = "${kafka.topics.roomBillz}",groupId = "${kafka.groups.notification}")
    public void consumeRoomBillzEvent(EventMessage message) {
    	logger.info(NotificationServiceConstant.INSIDE_THE_METHOD + "Event received event from RoomBillz Service:{}",	kv("Message", message.getMessage()));
        // Process event and send notification
    }
}
