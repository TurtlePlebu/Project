package org.example;

import java.util.ArrayList;
import java.util.List;

public interface UsersDeliveryManaging {

    /**
     * displays all clients in the Post-Office's list of Client*
     * @param sorting the sorting format of the display
     * @return a true or false value indicating the success of the operation
     */
    default boolean viewClient(String sorting) {
        List<Client> clientsCopy = List.copyOf(PostOffice.clients).stream()
                .sorted(new Client.ClientComparator(sorting))
                .toList();

        for (Client client : clientsCopy) {
            System.out.printf("Client : %s", client);
        }
        return true;
    }

    /**
     * displays all staffs in the Post-Office's list of Staff
     * @param sorting the sorting format of the display
     * @return a true or false value indicating the success of the operation
     */
    default boolean viewStaff(String sorting) {
        List<Staff> staffsCopy = List.copyOf(PostOffice.staffs).stream()
                .sorted(new Staff.StaffComparator(sorting))
                .toList();

        for (Staff staff : staffsCopy) {
            if (staff instanceof Courier c) {
                System.out.printf("Courier : %s", c);
            }
            else {
                System.out.printf("Staff : %s", staff);
            }
        }
        return true;
    }

    /**
     * finds all couriers in the Post-Office's Staff List
     * @return a List of couriers
     */
    default List<Courier> searchCouriers() {
        List<Courier> couriers = new ArrayList<>();

        for (Staff staff : PostOffice.staffs) {
            if (staff instanceof Courier c) {
                couriers.add(c);
            }
        }

        return couriers;
    }

    /**
     * searches for a courier with the given id
     * @param id the targeted Courier's id
     * @param couriers the List of Courier
     * @return the targeted Courier
     */
    default Courier searchCourier(int id, List<Courier> couriers) {
        Courier foundCourier = null;

        for (Courier courier : couriers) {
            if (courier.getStaffId() == id) {
                foundCourier = courier;
            }
        }

        return foundCourier;
    }

    /**
     * displays all deliveries in the Post-Office's list of Delivery
     * @param sorting the sorting format of the display
     * @return a true or false value indicating the success of the operation
     */
    default boolean viewAllDelivery(String sorting) {
        List<Delivery> deliveriesCopy = List.copyOf(PostOffice.deliveries).stream()
                .sorted(new Delivery.DeliveryComparator(sorting))
                .toList();

        for (Delivery delivery : deliveriesCopy) {
            if (delivery instanceof Parcel p) {
                System.out.printf("Parcel : %s", p);
            }
            if (delivery instanceof Mail m) {
                System.out.printf("Message : %s", m);
            }
        }
        return true;
    }

    /**
     * arbitrary removes a Delivery from the Post-Office's system
     * @param id the targeted Delivery's id
     */
    default void removePostOfficeDelivery(int id) {
        PostOffice.deliveries = PostOffice.deliveries.stream()
                .filter(delivery -> !delivery.equals(searchDeliverySystem(id)))
                .toList();
    }

    /**
     * searches a Delivery in the Post-Office's system
     * @param id the id of the targeted Delivery
     * @return the targeted Delivery
     */
    default Delivery searchDeliverySystem(int id) {
        Delivery foundDelivery = null;

        for (Delivery delivery : PostOffice.deliveries) {
            if (delivery.getDeliveryId() == id) {
               foundDelivery = delivery;
            }
        }

        return foundDelivery;
    }

    /**
     * displays all the advertisements in the Post-Office's system
     * @return a true or false value indicating the success of the operation
     */
    default boolean viewAllAds() {

        PostOffice.advertisements = PostOffice.advertisements.stream()
                .sorted()
                .toList();

        for (Advertisement advertisement : PostOffice.advertisements) {
            System.out.println(advertisement);
        }

        return true;
    }
}
