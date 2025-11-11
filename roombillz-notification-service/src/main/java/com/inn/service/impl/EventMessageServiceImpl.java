package com.inn.service.impl;

import static net.logstash.logback.argument.StructuredArguments.kv;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.inn.converter.JsonUtil;
import com.inn.dto.EventMessageCreateRequestDto;
import com.inn.dto.PurchaseOrderDetailNotificationEvent;
import com.inn.dto.ResponseDto;
import com.inn.entity.EventMessage;
import com.inn.notificationConstants.NotificationServiceConstant;
import com.inn.repository.IEventMessageRepository;
import com.inn.service.IEventMessageService;
import com.inn.service.IPurchaseOrderDetailService;

@Service
public class EventMessageServiceImpl implements IEventMessageService {
	
	private static final Logger logger = LoggerFactory.getLogger(EventMessageServiceImpl.class);
	
	@Autowired
	IEventMessageRepository eventMessageRepository;
	
	@Autowired
	IPurchaseOrderDetailService purchaseOrderDetailService;
	
	@Override
	public ResponseEntity<ResponseDto> createEvent(EventMessageCreateRequestDto eventMessageCreateRequestDto) {
		try {
			logger.info(NotificationServiceConstant.INSIDE_THE_METHOD + "createEvent :", kv("EventMessageCreateRequestDto", eventMessageCreateRequestDto));
			EventMessage eventMessage = new EventMessage();
			eventMessage.setPurchaseId(eventMessageCreateRequestDto.getPurchaseId());
			eventMessage.setRoomBillzId(eventMessageCreateRequestDto.getId());
			eventMessage.setEventType(eventMessageCreateRequestDto.getEventType());
			eventMessage.setMessage(eventMessageCreateRequestDto.getMessage());
			eventMessage.setSourceService(eventMessageCreateRequestDto.getSourceService());
			eventMessage.setTimestamp(eventMessageCreateRequestDto.getTimestamp());
			eventMessage.setStatus(NotificationServiceConstant.FAILED);
			eventMessageRepository.save(eventMessage);
			return ResponseEntity.ok(new ResponseDto("200", "EventMessage created successfully."));
		} catch (Exception e) {
			logger.error(NotificationServiceConstant.ERROR_OCCURRED_DUE_TO, kv(NotificationServiceConstant.ERROR_MESSAGE, e.getMessage()));
			throw e;
		}
	}

	@Override
	public EventMessage findEarliestFailedEvent() {
		logger.info(NotificationServiceConstant.INSIDE_THE_METHOD +"findEarliestFailedEvent");
		try {
			return eventMessageRepository.findFirstByStatusOrderByCreatedAtAsc(NotificationServiceConstant.FAILED).orElse(null);
		} catch (Exception e) {
			logger.error(NotificationServiceConstant.ERROR_OCCURRED_DUE_TO, kv(NotificationServiceConstant.ERROR_MESSAGE, e.getMessage()));
			throw e;
		}
	}

	@Override
	public ResponseEntity<ResponseDto> updateEventMessageByStatus(Integer id, String status) {
		logger.info(NotificationServiceConstant.INSIDE_THE_METHOD + "updateEventMessageByStatus {}", kv("ID", id), kv("status", status));
	    try {
	        return eventMessageRepository.findById(id)
	                .map(eventMessage -> {
	                    eventMessage.setStatus(status);
	                    eventMessageRepository.save(eventMessage);
	                    return ResponseEntity.ok(new ResponseDto("200", "EventMessage status updated successfully."));
	                })
	                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND)
	                        .body(new ResponseDto("404", "EventMessage not found with id: " + id)));

	    } catch (Exception e) {
	        logger.error(NotificationServiceConstant.ERROR_OCCURRED_DUE_TO, kv(NotificationServiceConstant.ERROR_MESSAGE, e.getMessage()), e);
	        throw e;
	    }
	}

	@Override
	public ResponseEntity<ResponseDto> sendFailedPurchaseOrderDetailToNotificationService() {
		logger.info(NotificationServiceConstant.INSIDE_THE_METHOD + "sendFailedPurchaseOrderDetailToNotificationService");
	    try {
	        EventMessage earliestFailedEvent = this.findEarliestFailedEvent();
	        if (earliestFailedEvent == null) {
	            return ResponseEntity.status(HttpStatus.NOT_FOUND)
	                    .body(new ResponseDto("404", "No failed EventMessage found to send."));
	        }

	        Integer id = earliestFailedEvent.getId();
	        parsedDataForEntity(earliestFailedEvent);
	        this.updateEventMessageByStatus(id, NotificationServiceConstant.SUCCESS);
	        return ResponseEntity.ok(new ResponseDto("200", "EventMessage sent to Notification Service successfully."));
	    } catch (Exception e) {
	        logger.error(NotificationServiceConstant.ERROR_OCCURRED_DUE_TO, kv(NotificationServiceConstant.ERROR_MESSAGE, e.getMessage()), e);
	        throw e;
	    }
	}
	
	public void parsedDataForEntity(EventMessage eventMessage) {
		logger.info(NotificationServiceConstant.INSIDE_THE_METHOD +"parsedDataForEntity");
		String eventType = eventMessage.getEventType();
		logger.info("EventType received from RoomBillz Service: {}", kv("EventType",eventType));
		switch (eventType) {
		case NotificationServiceConstant.PURCHASE_ORDER_CREATED:
			createPurchaseOrderDetail(eventMessage.getMessage());
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
		try {
		logger.info(NotificationServiceConstant.INSIDE_THE_METHOD + "createPurchaseOrderDetail");
		PurchaseOrderDetailNotificationEvent purchaseOrderDetailNotifiEvent = JsonUtil.fromJson(message,PurchaseOrderDetailNotificationEvent.class);
		logger.info("EventType received from RoomBillz Service: {}", kv("PurchaseId",purchaseOrderDetailNotifiEvent.getPurchaseId()));
		purchaseOrderDetailService.createPurchaseOrder(purchaseOrderDetailNotifiEvent);
		} catch (Exception e) {
			logger.error(NotificationServiceConstant.ERROR_OCCURRED_DUE_TO, kv(NotificationServiceConstant.ERROR_MESSAGE, e.getMessage()));
			throw e;
		}
	}
	
	@Override
	public ResponseEntity<ResponseDto> deleteAllEventMessage() {
		try {
			logger.info(NotificationServiceConstant.INSIDE_THE_METHOD + "deleteAllEventMessage :");
			eventMessageRepository.deleteAll();
			return ResponseEntity.ok(new ResponseDto("200", "EventMessage deleted successfully."));
		} catch (Exception e) {
			logger.error(NotificationServiceConstant.ERROR_OCCURRED_DUE_TO, kv(NotificationServiceConstant.ERROR_MESSAGE, e.getMessage()));
			throw e;
		}
	}
}
