package org.example;

public class EmailNotFoundException extends RuntimeException{

    public EmailNotFoundException() {
    }

    public EmailNotFoundException(String message) {
        super(message);
    }
}
