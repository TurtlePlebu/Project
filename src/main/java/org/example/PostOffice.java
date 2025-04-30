package org.example;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class PostOffice {
    private String name;
    private String address;
    private String email;
    private List<Delivery> deliveries;
    private List<Ticket> tickets;
    private List<User> users;
    private Manager manager;

    public PostOffice(String name, String address, String email, Manager manager) {
        this.name = name;
        this.address = address;
        this.email = email;
        this.manager = manager;
        this.deliveries = new ArrayList<>();
        this.tickets= new ArrayList<>();
        this.users = new ArrayList<>();
    }

    public PostOffice(String name, String address, String email, List<Delivery> deliveries, List<Ticket> tickets, List<User> users, Manager manager) {
        this.name = name;
        this.address = address;
        this.email = email;
        this.deliveries = deliveries;
        this.tickets = tickets;
        this.users = users;
        this.manager = manager;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        PostOffice that = (PostOffice) o;
        return Objects.equals(name, that.name) && Objects.equals(address, that.address) && Objects.equals(email, that.email) && Objects.equals(deliveries, that.deliveries) && Objects.equals(tickets, that.tickets) && Objects.equals(users, that.users) && Objects.equals(manager, that.manager);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, address, email, deliveries, tickets, users, manager);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public List<Delivery> getDeliveries() {
        return deliveries;
    }

    public void setDeliveries(List<Delivery> deliveries) {
        this.deliveries = deliveries;
    }

    public List<Ticket> getTickets() {
        return tickets;
    }

    public void setTickets(List<Ticket> tickets) {
        this.tickets = tickets;
    }

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }

    public Manager getManager() {
        return manager;
    }

    public void setManager(Manager manager) {
        this.manager = manager;
    }
}
