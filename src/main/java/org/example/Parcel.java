package org.example;

import java.time.LocalDateTime;
import java.util.Objects;

public class Parcel extends Delivery{
    private static int nextId = 0;

    private int parcelId;
    private Item item;
    private int quantity;

    public Parcel(String address, String description, LocalDateTime arrivalTime, Status status, Item item, int quantity) {
        super(address, description, arrivalTime, status);
        this.parcelId = nextId++;
        this.item = item;
        this.quantity = quantity;
    }

    public Parcel(String address, String description, LocalDateTime arrivalTime, Item item, int quantity) {
        super(address, description, arrivalTime);
        this.parcelId = nextId++;
        this.item = item;
        this.quantity = quantity;
        this.status = Delivery.Status.ONGOING;
    }

    @Override
    public String toString() {
        return String.format("Parcel : %-5d%-10s%-10s%-3d" + super.toString(),
                parcelId,address,item.getName(),quantity);
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Parcel parcel = (Parcel) o;
        return parcelId == parcel.parcelId && quantity == parcel.quantity && Objects.equals(address, parcel.address) && Objects.equals(item, parcel.item);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), parcelId, address, item, quantity);
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
