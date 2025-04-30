package org.example;

import java.time.LocalDateTime;
import java.util.Objects;

public class Parcel extends Delivery{
    private static int nextId = 0;

    private int parcelId;
    private Item item;
    private int quantity;

    public Parcel(LocalDateTime arrivalTime, String description, Item item, int quantity) {
        super(arrivalTime, description);
        this.parcelId = nextId++;
        this.item = item;
        this.quantity = quantity;
    }

    @Override
    public String toString() {
        return String.format("Parcel : %-5d%-10s%-3d" + super.toString(),
                parcelId,item.getName(),quantity);
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Parcel parcel = (Parcel) o;
        return parcelId == parcel.parcelId && quantity == parcel.quantity && Objects.equals(item, parcel.item);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), parcelId, item, quantity);
    }

    public int getParcelId() {
        return parcelId;
    }

    public void setParcelId(int parcelId) {
        this.parcelId = parcelId;
    }

    public Item getItem() {
        return item;
    }

    public void setItem(Item item) {
        this.item = item;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
