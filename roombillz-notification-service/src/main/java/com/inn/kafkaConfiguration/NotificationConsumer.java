package com.inn.kafkaConfiguration;



import static net.logstash.logback.argument.StructuredArguments.kv;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.stereotype.Service;

import com.inn.converter.JsonUtil;
import com.inn.dto.EventMessageDTO;
import com.inn.dto.PurchaseOrderDetailNotificationEvent;
import com.inn.notificationConstants.NotificationServiceConstant;
import com.inn.service.IPurchaseOrderDetailService;

@Service
public class NotificationConsumer {
	
	private static final Logger logger = LoggerFactory.getLogger(NotificationConsumer.class);
	
	@Autowired
	IPurchaseOrderDetailService purchaseOrderDetailService;

	@KafkaListener(topics = "${kafka.topics.roomBillz}",groupId = "${kafka.groups.notification}",containerFactory = "kafkaListenerContainerFactory")
	public void eventListener(EventMessageDTO message, Acknowledgment ack) {
	    logger.info(NotificationServiceConstant.INSIDE_THE_METHOD + "eventListener");
	    logger.info("Event received from RoomBillz Service: {}", kv("Message",message));
	    parsedDataForEntity(message);
	    ack.acknowledge();
	}
	
	
	public void parsedDataForEntity(EventMessageDTO eventMessageDTO) {
		logger.info(NotificationServiceConstant.INSIDE_THE_METHOD +"parsedDataForEntity");
		String eventType = eventMessageDTO.getEventType();
		logger.info("EventType received from RoomBillz Service: {}", kv("EventType",eventType));
		switch (eventType) {
		case NotificationServiceConstant.PURCHASE_ORDER_CREATED:
			createPurchaseOrderDetail(eventMessageDTO.getMessage());
			break;
		case "Approved":
			System.out.println("Order is approved");
			break;
		case "Rejected":
			System.out.println("Order is rejected");
			break;
		default:
			System.out.println("Unknown status");
		}
	}
	
	public void createPurchaseOrderDetail(String message) {
		logger.info(NotificationServiceConstant.INSIDE_THE_METHOD + "createPurchaseOrderDetail");
		PurchaseOrderDetailNotificationEvent purchaseOrderDetailNotifiEvent = JsonUtil.fromJson(message,PurchaseOrderDetailNotificationEvent.class);
		logger.info("EventType received from RoomBillz Service: {}", kv("PurchaseId",purchaseOrderDetailNotifiEvent.getPurchaseId()));
		purchaseOrderDetailService.createPurchaseOrder(purchaseOrderDetailNotifiEvent);
	}
}
