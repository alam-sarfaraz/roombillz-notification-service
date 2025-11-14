package com.inn.controller.impl;

import static net.logstash.logback.argument.StructuredArguments.kv;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import com.inn.controller.INotificationSenderController;
import com.inn.dto.ResponseDto;
import com.inn.notificationConstants.NotificationServiceConstant;
import com.inn.service.INotificationSenderService;

@RestController
public class NotificationSenderControllerImpl implements INotificationSenderController{
	
	private static final Logger logger = LoggerFactory.getLogger(NotificationSenderControllerImpl.class);
	
	@Autowired
	INotificationSenderService notificationService;

	@Override
	public ResponseEntity<ResponseDto> sendPurchaseOrderEmail(String[] recipients, Map<String, Object> purchaseModel) {
		try {
			logger.info(NotificationServiceConstant.INSIDE_THE_METHOD + "sendPurchaseOrderEmail {}",kv("Recipients", recipients),kv("PurchaseModel", purchaseModel));
			return notificationService.sendPurchaseOrderEmail(recipients,purchaseModel);
		} catch (Exception e) {
			logger.error(NotificationServiceConstant.ERROR_OCCURRED_DUE_TO,kv(NotificationServiceConstant.ERROR_MESSAGE, e.getMessage()));
			throw e;
		}
	}

}
