package org.example;

import java.time.LocalDateTime;
import java.util.Objects;

public class Item {
    private String name;
    private double weight;
    private LocalDateTime purchasedTime;

    public Item(String name, double weight, LocalDateTime purchasedTime) {
        this.name = name;
        this.weight = weight;
        this.purchasedTime = purchasedTime;
    }

    @Override
    public String toString() {
        return String.format("Item :\n" +
                "%s" +
                "%.2f" +
                "%s",
                name,
                weight,
                purchasedTime.toString()
        );
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Item item = (Item) o;
        return Double.compare(weight, item.weight) == 0 && Objects.equals(name, item.name) && Objects.equals(purchasedTime, item.purchasedTime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, weight, purchasedTime);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public LocalDateTime getPurchasedTime() {
        return purchasedTime;
    }

    public void setPurchasedTime(LocalDateTime purchasedTime) {
        this.purchasedTime = purchasedTime;
    }
}
