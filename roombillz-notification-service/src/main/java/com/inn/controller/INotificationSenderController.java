package com.inn.controller;

import java.util.Map;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.inn.dto.ErrorResponseDto;
import com.inn.dto.ResponseDto;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Notification Detail Sender Controller", description = "APIs for managing and sending notifications (email, SMS, or in-app) in the RoomBillz application")
@RequestMapping(path = "/notificationSender", produces = { MediaType.APPLICATION_JSON_VALUE })
@Validated
public interface INotificationSenderController {

	@Operation(summary = "Send Purchase Order Email", description = "Sends a purchase order notification email to multiple recipients using the provided template model.")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Email sent successfully", content = @Content(schema = @Schema(implementation = ResponseDto.class))),
			@ApiResponse(responseCode = "400", description = "Invalid input", content = @Content(schema = @Schema(implementation = ErrorResponseDto.class))),
			@ApiResponse(responseCode = "500", description = "Internal Server Error", content = @Content(schema = @Schema(implementation = ErrorResponseDto.class))) })
	@PostMapping(value = "/sendPurchaseOrderEmail", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseDto> sendPurchaseOrderEmail(@Parameter(description = "Array of email recipients", example = "[\"user1@example.com\", \"user2@example.com\"]") @RequestParam String[] recipients,
			                                                  @Parameter(description = "Dynamic purchase order model data used in template rendering")
	                                                          @RequestBody Map<String, Object> purchaseModel);

}
