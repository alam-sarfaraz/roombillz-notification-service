package com.inn.customException;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class NotificationServiceException extends RuntimeException{
	
	public NotificationServiceException(String message) {
		super(message);
	}
}
