package com.dyz.recordservice.common.execption;


public class RemoteServiceException extends BusinessException {

    public RemoteServiceException() {
        super();
    }

    public RemoteServiceException(String message) {
        super(message);
    }

    public RemoteServiceException(int code, String message) {
        super(message);
        this.code = code;
    }

    public RemoteServiceException(String message, Throwable cause) {
        super(message, cause);
    }
}