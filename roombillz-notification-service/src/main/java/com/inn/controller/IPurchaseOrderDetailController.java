package com.inn.controller;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.inn.dto.ApproveRejectDTO;
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
	                                                       @Valid @RequestBody PurchaseOrderDetailNotificationEvent purchaseOrderDetailNotificationEvent);

	@Operation(summary = "Approve or Reject Purchase Order Detail", description = "Approve or Reject Purchase Order Detail")
	@ApiResponses(value = {
	        @ApiResponse(responseCode = "201", description = "approveRejectPurchaseOrderDetail successfully"),
	        @ApiResponse(responseCode = "400", description = "Invalid input",content = @Content(schema = @Schema(implementation = ErrorResponseDto.class))),
	        @ApiResponse(responseCode = "500", description = "HTTP Status Internal Server Error",content = @Content(schema = @Schema(implementation = ErrorResponseDto.class)))
	})
	@PutMapping(path = "/approveRejectPurchaseOrderDetail", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseDto> approveRejectPurchaseOrderDetail(@Parameter(description = "Purchase order details",required = true,content = @Content(schema = @Schema(implementation = ApproveRejectDTO.class)))
	                                                                    @Valid @RequestBody ApproveRejectDTO approveRejectDTO);
	
	@Operation(summary = "Update Approve or Reject Purchase Order Detail status",description = "Approves or Rejects purchase order detail based on internal logic")
	@ApiResponses(value = {
	        @ApiResponse(responseCode = "200", description = "Status updated successfully"),
	        @ApiResponse(responseCode = "400", description = "Invalid input", content = @Content(schema = @Schema(implementation = ErrorResponseDto.class))),
	        @ApiResponse(responseCode = "500", description = "Internal Server Error", content = @Content(schema = @Schema(implementation = ErrorResponseDto.class)))
	})
	@GetMapping(path = "/updateApproveRejectPurchaseOrderDetailStatus", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseDto> updateApproveRejectPurchaseOrderDetailStatus();
	
	@Operation(summary = "Delete all Purchase Order Details", description = "Deletes all Purchase Order Details from the system")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "All Purchase Order Details deleted successfully"),
			@ApiResponse(responseCode = "500", description = "Internal Server Error", content = @Content(schema = @Schema(implementation = ErrorResponseDto.class))) })
	@DeleteMapping(path = "/delete-all", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseDto> deleteAllPurchaseOrderDetails();

}