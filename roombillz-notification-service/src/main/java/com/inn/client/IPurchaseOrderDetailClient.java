package com.inn.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.inn.dto.ResponseDto;
import com.inn.entity.PurchaseOrderDetail;

@FeignClient(name = "ROOMBILLZ-SERVICE", path ="/api/v1/roomBillz/purchaseOrderDetail", configuration = {FeignConfig.class,FeignRetryConfig.class})
public interface IPurchaseOrderDetailClient {
	
	@PutMapping(path = "/updatePODetailStatusByPurchaseId", produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<ResponseDto> updatePODetailStatusByPurchaseId(@RequestParam(name = "purchaseId") String purchaseId,
			                                                     @RequestParam(name = "status") String status);
	@GetMapping(path = "/findPurchaseOrderDetailByPurchaseId")
    ResponseEntity<PurchaseOrderDetail> findPurchaseOrderDetailByPurchaseId(@RequestParam(name = "purchaseId") String purchaseId);
}
