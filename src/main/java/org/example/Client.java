package org.example;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Client extends User implements Registerable {
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
        PostOffice.clients.add(this);
        PostOffice.exportData();
    }

    @Override
    public void viewDelivery() {
        if (deliveries.isEmpty()) {
            this.deliveries = PostOffice.deliveries.stream()
                    .filter(client -> client.getAddress().equalsIgnoreCase(this.address))
                    .toList();
        }

        for (Delivery delivery : deliveries) {
            if (delivery instanceof Parcel p) {
                System.out.printf("Parcel : %s", p);
            }
            if (delivery instanceof Mail m) {
                System.out.printf("Message : %s", m);
            }
        }

        for (Advertisement ad : PostOffice.advertisements) {
            System.out.printf("Ad : %s", ad);
        }
    }

    @Override
    public boolean receiveDelivery(Delivery del) {
        if (del != null) {
            deliveries.add(del);
        }

        if (!PostOffice.deliveries.contains(del)) {
            PostOffice.deliveries.add(del);
            PostOffice.exportData();
        }

        return !PostOffice.deliveries.contains(del);
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
