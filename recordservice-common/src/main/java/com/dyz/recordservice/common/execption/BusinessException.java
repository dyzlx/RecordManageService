package com.dyz.recordservice.common.execption;

@SuppressWarnings("serial")
public class BusinessException extends RuntimeException {

	protected int code;

	public BusinessException() {
		super();
	}
	
	public BusinessException(String message) {
		super(message);
	}

	public BusinessException(int code, String message) {
		super(message);
		this.code = code;
	}

	public BusinessException(String message, Throwable cause) {
		super(message, cause);
	}
	
	public int getCode() {
		return code;
	}
}
