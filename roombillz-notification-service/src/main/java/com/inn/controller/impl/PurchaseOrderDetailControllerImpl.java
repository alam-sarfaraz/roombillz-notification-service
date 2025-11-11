package com.inn.controller.impl;

import static net.logstash.logback.argument.StructuredArguments.kv;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import com.inn.controller.IPurchaseOrderDetailController;
import com.inn.dto.ApproveRejectDTO;
import com.inn.dto.PurchaseOrderDetailNotificationEvent;
import com.inn.dto.ResponseDto;
import com.inn.logs.LogRequestResponse;
import com.inn.notificationConstants.NotificationServiceConstant;
import com.inn.service.IPurchaseOrderDetailService;

import jakarta.validation.Valid;

@RestController
public class PurchaseOrderDetailControllerImpl implements IPurchaseOrderDetailController {

	private static final Logger logger = LoggerFactory.getLogger(PurchaseOrderDetailControllerImpl.class);

	@Autowired
	IPurchaseOrderDetailService iPurchaseOrderDetailService;

	@Override
	@LogRequestResponse
	public ResponseEntity<ResponseDto> createPurchaseOrder(@Valid PurchaseOrderDetailNotificationEvent purchaseOrderDetailNotificationEvent) {
		try {
			logger.info(NotificationServiceConstant.INSIDE_THE_METHOD + "createPurchaseOrder {}",kv("PurchaseOrderDetail", purchaseOrderDetailNotificationEvent));
			return iPurchaseOrderDetailService.createPurchaseOrder(purchaseOrderDetailNotificationEvent);
		} catch (Exception e) {
			logger.error(NotificationServiceConstant.ERROR_OCCURRED_DUE_TO,kv(NotificationServiceConstant.ERROR_MESSAGE, e.getMessage()));
			throw e;
		}
	}

	@Override
	@LogRequestResponse
	public ResponseEntity<ResponseDto> approveRejectPurchaseOrderDetail(@Valid ApproveRejectDTO approveRejectDTO) {
		try {
			logger.info(NotificationServiceConstant.INSIDE_THE_METHOD + "approveRejectPurchaseOrderDetail {}",kv("ApproveRejectDTO", approveRejectDTO));
			return iPurchaseOrderDetailService.approveRejectPurchaseOrderDetail(approveRejectDTO);
		} catch (Exception e) {
			logger.error(NotificationServiceConstant.ERROR_OCCURRED_DUE_TO,kv(NotificationServiceConstant.ERROR_MESSAGE, e.getMessage()));
			throw e;
		}
	}

	@Override
	@LogRequestResponse
	public ResponseEntity<ResponseDto> updateApproveRejectPurchaseOrderDetailStatus() {
		try {
			logger.info(NotificationServiceConstant.INSIDE_THE_METHOD + "updateApproveRejectPurchaseOrderDetailStatus:");
			return iPurchaseOrderDetailService.updateApproveRejectPurchaseOrderDetailStatus();
		} catch (Exception e) {
			logger.error(NotificationServiceConstant.ERROR_OCCURRED_DUE_TO,kv(NotificationServiceConstant.ERROR_MESSAGE, e.getMessage()));
			throw e;
		}
	}
	
	@Override
	@LogRequestResponse
	public ResponseEntity<ResponseDto> deleteAllPurchaseOrderDetails() {
		try {
			logger.info(NotificationServiceConstant.INSIDE_THE_METHOD + "deleteAllPurchaseOrderDetails");
			return iPurchaseOrderDetailService.deleteAllPurchaseOrderDetails();
		} catch (Exception e) {
			logger.error(NotificationServiceConstant.ERROR_OCCURRED_DUE_TO, kv(NotificationServiceConstant.ERROR_MESSAGE, e.getMessage()));
			throw e;
		}
	}

}
