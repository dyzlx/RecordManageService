package com.dyz.recordservice.common.execption;

@SuppressWarnings("serial")
public class IllegalParamException extends BusinessException {

	public IllegalParamException() {
		super();
	}

	public IllegalParamException(String message) {
		super(message);
	}

	public IllegalParamException(int code, String message) {
		super(message);
		this.code = code;
	}

	public IllegalParamException(String message, Throwable cause) {
		super(message, cause);
	}
}
