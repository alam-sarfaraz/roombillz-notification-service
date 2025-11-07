package com.inn.service;

import org.springframework.http.ResponseEntity;

import com.inn.dto.PurchaseOrderDetailNotificationEvent;
import com.inn.dto.ResponseDto;

import jakarta.validation.Valid;

public interface IPurchaseOrderDetailService {

	ResponseEntity<ResponseDto> createPurchaseOrder(@Valid PurchaseOrderDetailNotificationEvent purchaseOrderDetailNotificationEvent);

}
