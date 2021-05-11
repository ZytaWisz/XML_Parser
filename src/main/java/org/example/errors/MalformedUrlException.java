package org.example.errors;

public class MalformedUrlException extends RuntimeException {

    public MalformedUrlException(String message) {
        super(message);
    }
}
