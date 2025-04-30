package org.example;

import java.time.LocalDateTime;
import java.util.Objects;

public abstract class Delivery {
    protected String description;
    protected LocalDateTime arrivalTime;
    protected Status Status;

    public Delivery(LocalDateTime arrivalTime, String description) {
        this.arrivalTime = arrivalTime;
        this.description = description;
        this.Status = Status.ONGOING;
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
                description,String.valueOf(Status),arrivalTime.toString());
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Delivery delivery = (Delivery) o;
        return Objects.equals(description, delivery.description) && Objects.equals(arrivalTime, delivery.arrivalTime) && Status == delivery.Status;
    }

    @Override
    public int hashCode() {
        return Objects.hash(description, arrivalTime, Status);
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
        return Status;
    }

    public void setStatus(Status status) {
        Status = status;
    }
}
