package org.example;

import java.time.LocalDateTime;
import java.util.Objects;

public class Parcel extends Delivery{
    private static int nextId = 0;

    private int parcelId;
    private String address;
    private Item item;
    private int quantity;

    public Parcel(LocalDateTime arrivalTime, String description, String address, Item item, int quantity) {
        super(arrivalTime, description);
        this.parcelId = nextId++;
        this.address = address;
        this.item = item;
        this.quantity = quantity;
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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
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
