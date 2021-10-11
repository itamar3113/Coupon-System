package com.jb.Couponsystem.rest.common;

public class ErrorResponse {

    private final String message;
    private final long timeStamp;

    private ErrorResponse(String message, long timeStamp) {
        this.message = message;
        this.timeStamp = timeStamp;
    }

    public static ErrorResponse from(Exception exception) {
        return new ErrorResponse(exception.getMessage(),System.currentTimeMillis());
    }

    public String getMessage() {
        return message;
    }

    public long getTimeStamp() {
        return timeStamp;
    }
}
