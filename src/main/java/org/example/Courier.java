package org.example;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Courier extends Staff{

    private List<Parcel> parcels;

    public Courier(String name, String email) {
        super(name, email);
        this.staffId = Staff.nextId++;
        this.role = Role.COURIER;
        this.parcels = new ArrayList<>();
    }

    /**
     * displays all the parcel in the Courier's inventory
     * @param sorting the sorting format of the display
     */
    public void viewOngoingParcels(String sorting) {
        if (parcels == null || parcels.isEmpty()) {
            return;
        }

        parcels = parcels.stream()
                .sorted(new Parcel.ParcelComparator(sorting))
                .toList();

        for (Parcel parcel : parcels) {
            System.out.println(parcel);
        }
    }

    /**
     * collects a Parcel and adds it to his inventory to deliver
     * @param parcel the
     */
    public void pickupParcel(Parcel parcel) {
        if (parcel == null) {
            System.out.println("No parcel left");
            return;
        }

        parcel.setCourier(this);

        List<Parcel> parcelsCopy = new ArrayList<>(parcels);
        parcelsCopy.add(parcel);
        parcels = parcelsCopy;

        PostOffice.deliveries = PostOffice.deliveries
                .stream()
                .map(delivery -> (delivery instanceof Parcel p && p.getParcelId() == parcel.getParcelId()) ? parcel : delivery)
                .toList();
    }

    /**
     * delivers the Parcel to the Client through the address
     * @param parcel the delivered Parcel
     * @return a true or false value indicating the success of the operation
     */
    public boolean deliver(Parcel parcel) {
        User receiver;

        if (parcel.getAddress().equalsIgnoreCase(PostOffice.address)) {
            receiver = PostOffice.searchUser(parcel.getEmail());
        } else {
            receiver = PostOffice.searchClient(parcel.getAddress());
        }


        if (receiver == null) {
            return false;
        }

        parcel.setArrivalTime(LocalDateTime.now());
        parcel.setStatus(Delivery.Status.DELIVERED);

        List<Delivery> deliveriesCopy = new ArrayList<>(receiver.getDeliveries());
        deliveriesCopy.add(parcel);
        receiver.setDeliveries(deliveriesCopy);

        List<Parcel> parcelsCopy = parcels.stream()
                .filter(parcel1 -> !(parcel1.equals(parcel)))
                .toList();

        parcels = parcelsCopy;

        PostOffice.deliveries = PostOffice.deliveries.stream()
                .map(delivery -> (delivery instanceof Parcel p && p.getParcelId() == parcel.getParcelId()) ? parcel : delivery)
                .toList();

        return true;
    }

    /**
     * searches the Parcel with the given id
     * @param id the id of the targeted Parcel
     * @return the targeted Parcel
     */
    public Parcel searchParcel(int id) {
        Parcel foundParcel = null;

        for (Parcel parcel : parcels) {
            if (parcel.getParcelId() == id) {
                foundParcel = parcel;
            }
        }

        return foundParcel;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Courier courier = (Courier) o;
        return Objects.equals(parcels, courier.parcels);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), parcels);
    }

    public List<Parcel> getParcels() {
        return parcels;
    }

    public void setParcels(List<Parcel> parcels) {
        this.parcels = parcels;
    }
}
