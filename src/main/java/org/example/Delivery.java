package org.example;

import java.time.LocalDateTime;
import java.util.Objects;

public abstract class Delivery {
    protected String address;
    protected String description;
    protected LocalDateTime arrivalTime;
    protected Status status;

    public Delivery(String address, String description, LocalDateTime arrivalTime) {
        this.address = address;
        this.description = description;
        this.arrivalTime = arrivalTime;
        this.status = Delivery.Status.ONGOING;
    }

    public Delivery(String address, String description, LocalDateTime arrivalTime, Status status) {
        this.address = address;
        this.description = description;
        this.arrivalTime = arrivalTime;
        this.status = status;
    }

    public static enum Status {
        DELIVERED,
        ONGOING;

        private Status() {
        }
    }

    @Override
    public String toString() {
        return String.format("%-20s%-10s%-15s\n",
                description,String.valueOf(status),arrivalTime.toString());
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Delivery delivery = (Delivery) o;
        return Objects.equals(description, delivery.description) && Objects.equals(arrivalTime, delivery.arrivalTime) && status == delivery.status;
    }

    @Override
    public int hashCode() {
        return Objects.hash(description, arrivalTime, status);
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDateTime getArrivalTime() {
        return arrivalTime;
    }

    public void setArrivalTime(LocalDateTime arrivalTime) {
        this.arrivalTime = arrivalTime;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }
}
