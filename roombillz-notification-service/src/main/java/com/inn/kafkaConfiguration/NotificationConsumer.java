package com.inn.kafkaConfiguration;



import static net.logstash.logback.argument.StructuredArguments.kv;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.stereotype.Service;

import com.inn.dto.EventMessage;
import com.inn.notificationConstants.NotificationServiceConstant;

@Service
public class NotificationConsumer {
	
	private static final Logger logger = LoggerFactory.getLogger(NotificationConsumer.class);

	@KafkaListener(topics = "${kafka.topics.roomBillz}",groupId = "${kafka.groups.notification}",
			       containerFactory = "kafkaListenerContainerFactory")
    public void eventListener(EventMessage message,Acknowledgment ack) {
		logger.info(NotificationServiceConstant.INSIDE_THE_METHOD + "eventListener");
    	logger.info("Event received from RoomBillz Service:{}",kv("Message", message));
        // Process event and send notification
    }
}
