package org.example.errors;

public class ErrorResponse {

    String message;
    String details;

    public ErrorResponse() {
    }

    public ErrorResponse(String message, String details) {
        this.details = details;
        this.message = message;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
