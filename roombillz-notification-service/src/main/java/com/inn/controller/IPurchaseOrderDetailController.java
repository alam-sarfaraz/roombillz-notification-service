package com.inn.controller;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;

import com.inn.dto.ErrorResponseDto;
import com.inn.dto.PurchaseOrderDetailNotificationEvent;
import com.inn.dto.ResponseDto;
import com.inn.entity.PurchaseOrderDetail;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@Tag(name = "Purchase Order Detail Controller", description = "APIs for managing user Purchase Order Detail in RoomBillz Notification Service")
@RequestMapping(path = "/purchaseOrderDetail")
@Validated
public interface IPurchaseOrderDetailController {

	@Operation(summary = "Create a new Purchase Order Detail", description = "Create a new Purchase Order Detail")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "201", description = "Purchase Order Detail created successfully"),
			@ApiResponse(responseCode = "400", description = "Invalid input"),
			@ApiResponse(responseCode = "500", description = "HTTP Status Internal Server Error", content = @Content(schema = @Schema(implementation = ErrorResponseDto.class))) })
	@PostMapping(path = "/createPurchaseOrder", consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseDto> createPurchaseOrder(@Parameter(description = "Purchase order details", required = true, content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = PurchaseOrderDetail.class))) 
	                                                       @Valid @RequestPart("purchaseOrder") PurchaseOrderDetailNotificationEvent purchaseOrderDetailNotificationEvent);

}