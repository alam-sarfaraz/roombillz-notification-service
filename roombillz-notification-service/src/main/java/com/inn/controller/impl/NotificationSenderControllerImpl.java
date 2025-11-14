package com.inn.controller.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import com.inn.controller.INotificationSenderController;
import com.inn.service.INotificationSenderService;

@RestController
public class NotificationSenderControllerImpl implements INotificationSenderController{
	
	private static final Logger logger = LoggerFactory.getLogger(NotificationSenderControllerImpl.class);
	
	@Autowired
	INotificationSenderService notificationService;

}
