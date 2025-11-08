package com.inn.controller.impl;

import static net.logstash.logback.argument.StructuredArguments.kv;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import com.inn.controller.IEventMessageController;
import com.inn.dto.EventMessageCreateRequestDto;
import com.inn.dto.ResponseDto;
import com.inn.entity.EventMessage;
import com.inn.logs.LogRequestResponse;
import com.inn.notificationConstants.NotificationServiceConstant;
import com.inn.service.IEventMessageService;

@RestController
public class EventMessageControllerImpl implements IEventMessageController {

	private static final Logger logger = LoggerFactory.getLogger(EventMessageControllerImpl.class);

	@Autowired
	IEventMessageService eventMessageService;
	
	@Override
	@LogRequestResponse
	public ResponseEntity<ResponseDto> createEvent(EventMessageCreateRequestDto eventMessageCreateRequestDtoe) {
		try {
			logger.info(NotificationServiceConstant.INSIDE_THE_METHOD + "createEvent :", kv("EventMessageCreateRequestDto", eventMessageCreateRequestDtoe));
			return eventMessageService.createEvent(eventMessageCreateRequestDtoe);
		} catch (Exception e) {
			logger.error(NotificationServiceConstant.ERROR_OCCURRED_DUE_TO, kv(NotificationServiceConstant.ERROR_MESSAGE, e.getMessage()));
			throw e;
		}
	}

	@Override
	@LogRequestResponse
	public EventMessage findEarliestFailedEvent() {
		try {
			logger.info(NotificationServiceConstant.INSIDE_THE_METHOD + "findEarliestFailedEvent :");
			return eventMessageService.findEarliestFailedEvent();
		} catch (Exception e) {
			logger.error(NotificationServiceConstant.ERROR_OCCURRED_DUE_TO, kv(NotificationServiceConstant.ERROR_MESSAGE, e.getMessage()));
			throw e;
		}
	}

	@Override
	@LogRequestResponse
	public ResponseEntity<ResponseDto> updateEventMessageByStatus(Integer id,String status) {
		try {
			logger.info(NotificationServiceConstant.INSIDE_THE_METHOD + "updateEventMessageByStatus :{}", kv("ID", id), kv("status", status));
			return eventMessageService.updateEventMessageByStatus(id,status);
		} catch (Exception e) {
			logger.error(NotificationServiceConstant.ERROR_OCCURRED_DUE_TO, kv(NotificationServiceConstant.ERROR_MESSAGE, e.getMessage()));
			throw e;
		}
	}

	@Override
	@LogRequestResponse
	public ResponseEntity<ResponseDto> sendFailedPurchaseOrderDetailToNotificationService() {
		try {
			logger.info(NotificationServiceConstant.INSIDE_THE_METHOD + "sendFailedPurchaseOrderDetailToNotificationService :");
			return eventMessageService.sendFailedPurchaseOrderDetailToNotificationService();
		} catch (Exception e) {
			logger.error(NotificationServiceConstant.ERROR_OCCURRED_DUE_TO, kv(NotificationServiceConstant.ERROR_MESSAGE, e.getMessage()));
			throw e;
		}
	}

}
