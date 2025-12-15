package com.grupo4.VetAndGo.controller.webmodel.exception;

import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class ErrorMessage {
    private final int status;
    private final String error;
    private final String message;
    private final String timestamp;

    public ErrorMessage(Exception exception, int status) {
        this.status = status;
        this.error = getGenericError(status);
        this.message = exception.getMessage();
        this.timestamp = LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME);
    }

    private String getGenericError(int status) {
        return switch (status) {
            case 400 -> "Bad Request";
            case 404 -> "Not Found";
            case 422 -> "Unprocessable Entity";
            case 500 -> "Internal Server Error";
            default -> HttpStatus.valueOf(status).getReasonPhrase();
        };
    }

    public int getStatus() {
        return status;
    }

    public String getError() {
        return error;
    }

    public String getMessage() {
        return message;
    }

    public String getTimestamp() {
        return timestamp;
    }
}
