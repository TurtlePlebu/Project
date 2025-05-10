package org.example;

public interface Registerable {
    void register(String password);
    boolean receiveDelivery(Delivery del);
}
