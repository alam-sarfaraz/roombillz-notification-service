package com.inn.dto;

import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ErrorResponseDto {
	
	private String apipath;
	private HttpStatus errorCode;
	private String errorMessage;
	private LocalDateTime errorTime;

}
