package org.example;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.Objects;

public abstract class Delivery {
    private static int nextId = 0;

    protected int deliveryId;
    protected String address;
    protected String description;
    protected LocalDateTime arrivalTime;
    protected Status status;

    public Delivery(String address, String description, LocalDateTime arrivalTime) {
        this.deliveryId = nextId++;
        this.address = address;
        this.description = description;
        this.arrivalTime = arrivalTime;
        this.status = Delivery.Status.ONGOING;
    }

    public Delivery(String address, String description, LocalDateTime arrivalTime, Status status) {
        this.deliveryId = nextId++;
        this.address = address;
        this.description = description;
        this.arrivalTime = arrivalTime;
        this.status = status;
    }

    /**
     * inner Comparator class sorting by:
     * the arrival time of the Delivery descendingly
     * by default, the arrival time of the Delivery ascendingly
     */
    public static class DeliveryComparator implements Comparator<Delivery> {
        private String type;

        public DeliveryComparator(String type) {
            this.type = type;
        }

        @Override
        public int compare(Delivery o1, Delivery o2) {
            return switch (type.toLowerCase()) {
                case "reverse" -> o2.getArrivalTime().compareTo(o1.getArrivalTime());
                default -> o1.getArrivalTime().compareTo(o2.getArrivalTime());
            };
        }
    }

    public static enum Status {
        DELIVERED,
        ONGOING;

        private Status() {
        }
    }

    @Override
    public String toString() {
        return String.format("%-5d%-10s%-20s%-10s%-15s\n",
                deliveryId,address, description,String.valueOf(status),arrivalTime.toString());
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Delivery delivery = (Delivery) o;
        return deliveryId == delivery.deliveryId && Objects.equals(address, delivery.address) && Objects.equals(description, delivery.description) && Objects.equals(arrivalTime, delivery.arrivalTime) && status == delivery.status;
    }

    @Override
    public int hashCode() {
        return Objects.hash(description, arrivalTime, status);
    }

    public int getDeliveryId() {
        return deliveryId;
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
