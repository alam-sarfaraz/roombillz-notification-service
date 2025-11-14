package com.inn.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.inn.repository.INotificationSenderRepository;
import com.inn.service.INotificationSenderService;

@Service
public class NotificationSenderServiceImpl implements INotificationSenderService{
	
	private static final Logger logger = LoggerFactory.getLogger(NotificationSenderServiceImpl.class);
	
	@Autowired
	INotificationSenderRepository notificationRepository;

}
