package org.example;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Client extends User{
    private static int nextId = 0;

    private int clientId;
    private String address;
    private List<Delivery> deliveries;

    public Client(String name, String email, String address) {
        super(name, email);
        this.clientId = nextId++;
        this.address = address;
        this.deliveries = new ArrayList<>();
    }

    public Client(String name, String email, String address, List<Delivery> deliveries) {
        super(name, email);
        this.clientId = nextId++;
        this.address = address;
        this.deliveries = deliveries;
    }

    @Override
    public void register() {

    }

    @Override
    public void viewDelivery() {

    }

    @Override
    public void receiveDelivery(Delivery del) {
        if (del != null) {
            deliveries.add(del);
        }
    }

    @Override
    public String toString() {
        return String.format("Client :\n" +
                "%-10s: %d\n" +
                super.toString() +
                "%-10s: %s\n",
                "Client ID", clientId,
                "Address", address);
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Client client = (Client) o;
        return clientId == client.clientId && Objects.equals(address, client.address) && Objects.equals(deliveries, client.deliveries);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), clientId, address, deliveries);
    }

    public int getClientId() {
        return clientId;
    }

    public void setClientId(int clientId) {
        this.clientId = clientId;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public List<Delivery> getDeliveries() {
        return deliveries;
    }

    public void setDeliveries(List<Delivery> deliveries) {
        this.deliveries = deliveries;
    }
}
