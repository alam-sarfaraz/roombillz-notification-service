package com.inn.service;

import java.util.Map;

import org.springframework.http.ResponseEntity;

import com.inn.dto.ResponseDto;

public interface INotificationSenderService {
	
	public ResponseEntity<ResponseDto> sendPurchaseOrderEmail(String[] recipients, Map<String,Object> purchaseModel);
}
