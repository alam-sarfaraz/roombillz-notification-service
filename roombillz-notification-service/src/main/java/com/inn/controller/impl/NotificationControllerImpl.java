package com.inn.controller.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import com.inn.controller.INotificationController;
import com.inn.service.INotificationService;

@RestController
public class NotificationControllerImpl implements INotificationController{
	
	private static final Logger logger = LoggerFactory.getLogger(NotificationControllerImpl.class);
	
	@Autowired
	INotificationService notificationService;

}
