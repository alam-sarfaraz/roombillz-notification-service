package com.inn.service;

import org.springframework.http.ResponseEntity;

import com.inn.dto.ApproveRejectDTO;
import com.inn.dto.PurchaseOrderDetailNotificationEvent;
import com.inn.dto.ResponseDto;

public interface IPurchaseOrderDetailService {

	public ResponseEntity<ResponseDto> createPurchaseOrder(PurchaseOrderDetailNotificationEvent purchaseOrderDetailNotificationEvent);

	public ResponseEntity<ResponseDto> approveRejectPurchaseOrderDetail(ApproveRejectDTO approveRejectDTO);

	public ResponseEntity<ResponseDto> updateApproveRejectPurchaseOrderDetailStatus();

	public ResponseEntity<ResponseDto> deleteAllPurchaseOrderDetails();

}
