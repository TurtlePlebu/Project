package org.example;

public class InvalidNumberOptionException extends RuntimeException{
    public InvalidNumberOptionException() {
    }

    public InvalidNumberOptionException(String message) {
        super(message);
    }
}
