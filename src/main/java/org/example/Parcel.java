package org.example;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.Objects;

public class Parcel extends Delivery{
    private static int nextId = 0;

    private int parcelId;
    private Item item;
    private int quantity;
    private Courier courier;

    public Parcel(String address, String description, LocalDateTime arrivalTime, Item item, int quantity, Courier courier) {
        super(address, description, arrivalTime);
        this.parcelId = nextId++;
        this.item = item;
        this.quantity = quantity;
        this.courier = courier;
    }

    public Parcel(String address, String description, LocalDateTime arrivalTime, Status status, Item item, int quantity, Courier courier) {
        super(address, description, arrivalTime, status);
        this.parcelId = nextId++;
        this.item = item;
        this.quantity = quantity;
        this.courier = courier;
    }

    /**
     * inner Comparator class sorting by:
     * the title ascendingly, then the arrival time of the Delivery ascendingly
     * the title descendingly, then the arrival time of the Delivery ascendingly
     * the arrival time of the Delivery descendingly, then the title ascendingly
     * by default, the arrival time of the Delivery ascendingly, then the title ascendingly
     */
    public static class ParcelComparator implements Comparator<Parcel> {
        private String type;

        public ParcelComparator(String type) {
            this.type = type;
        }

        @Override
        public int compare(Parcel o1, Parcel o2) {
            return switch (type) {
                case "id ascendingly" -> (o1.getParcelId() - o2.getParcelId()) * 100 + (o1.getArrivalTime().compareTo(o2.getArrivalTime()));
                case "id descendingly" -> (o2.getParcelId() - o1.getParcelId()) * 100 + (o1.getArrivalTime().compareTo(o2.getArrivalTime()));
                case "time descendingly" -> (o2.getArrivalTime().compareTo(o1.getArrivalTime())) * 100 + (o1.getParcelId() - o2.getParcelId());
                default -> (o1.getArrivalTime().compareTo(o2.getArrivalTime())) * 100 + (o1.getParcelId() - o2.getParcelId());
            };
        }
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

    public static void setNextId(int nextId) {
        Parcel.nextId = nextId;
    }

    public int getParcelId() {
        return parcelId;
    }

    public void setParcelId(int parcelId) {
        this.parcelId = parcelId;
    }

    public Courier getCourier() {
        return courier;
    }

    public void setCourier(Courier courier) {
        this.courier = courier;
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
