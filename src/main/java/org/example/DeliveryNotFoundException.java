package org.example;

public class DeliveryNotFoundException extends RuntimeException{
    public DeliveryNotFoundException() {
    }

    public DeliveryNotFoundException(String message) {
        super(message);
    }
}
