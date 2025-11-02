package com.inn.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.inn.repository.INotificationRepository;
import com.inn.service.INotificationService;

@Service
public class NotificationServiceImpl implements INotificationService{
	
	private static final Logger logger = LoggerFactory.getLogger(NotificationServiceImpl.class);
	
	@Autowired
	INotificationRepository notificationRepository;

}
