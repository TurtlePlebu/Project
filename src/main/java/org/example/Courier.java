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

    public void viewOngoingParcels(String sorting) {
        if (parcels == null || parcels.isEmpty()) {
            return;
        }

        parcels.sort(new Parcel.ParcelComparator(sorting));

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
        deliveries.add(parcel);
    }

    /**
     * delivers the Parcel to the Client through the address
     * @param parcel the delivered Parcel
     * @return a true or false value indicating the success of the operation
     */
    public boolean deliver(Parcel parcel) {
        Client receiver = PostOffice.searchClient(parcel.getAddress());

        if (receiver == null) {
            return false;
        }

        parcel.setArrivalTime(LocalDateTime.now());

        receiver.getDeliveries().add(parcel);

        parcels.remove(parcel);

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
