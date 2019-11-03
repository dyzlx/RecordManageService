package com.dyz.recordservice.api.interceptor;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.dyz.recordservice.api.model.Result;
import com.dyz.recordservice.common.execption.BusinessException;


@ControllerAdvice
public class GlobalExceptionInterceptor {

	@ExceptionHandler(value = BusinessException.class)
	public ResponseEntity<Result> handlerBusinessException(BusinessException exception) {
		return ResponseEntity.status(HttpStatus.FORBIDDEN)
				.body(Result.builder().code(exception.getCode()).message(exception.getMessage()).build());
	}
	
	@ExceptionHandler(value = Exception.class)
	public ResponseEntity<Result> handlerException(Exception exception) {
		return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE)
				.body(Result.builder().code(-1).message(exception.getMessage()).build());
	}

}
