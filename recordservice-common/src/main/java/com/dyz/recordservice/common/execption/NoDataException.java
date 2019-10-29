package com.dyz.recordservice.common.execption;

@SuppressWarnings("serial")
public class NoDataException extends BusinessException {

	public NoDataException() {
		super();
	}

	public NoDataException(String message) {
		super(message);
	}

	public NoDataException(int code, String message) {
		super(message);
		this.code = code;
	}

	public NoDataException(String message, Throwable cause) {
		super(message, cause);
	}
}
