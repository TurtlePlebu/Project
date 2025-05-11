package org.example;

import java.util.ArrayList;
import java.util.List;

public interface UsersDeliveryManaging {

    /**
     * displays all clients in the Post-Office's list of Client
     * @param sorting the sorting format of the display
     */
    default void viewClient(String sorting) {
        PostOffice.clients.sort(new Client.ClientComparator(sorting));

        for (Client client : PostOffice.clients) {
            System.out.printf("Client : %s", client);
        }
    }

    /**
     * displays all staffs in the Post-Office's list of Staff
     * @param sorting the sorting format of the display
     */
    default void viewStaff(String sorting) {
        PostOffice.staffs.sort(new Staff.StaffComparator(sorting));

        for (Staff staff : PostOffice.staffs) {
            if (staff instanceof Courier c) {
                System.out.printf("Courier : %s", c);
            }
            else {
                System.out.printf("Staff : %s", staff);
            }
        }
    }

    default List<Courier> searchCouriers() {
        List<Courier> couriers = new ArrayList<>();

        for (Staff staff : PostOffice.staffs) {
            if (staff instanceof Courier c) {
                couriers.add(c);
            }
        }

        return couriers;
    }

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
     * searches for a Staff with the given id
     * @param id the id of the targeted Staff
     * @return the Staff with the corresponding id
     */
    default Staff searchStaff(int id) {
        return searchStaff(id);
    }

    /**
     * searches for a Client with the given id
     * @param id the id of the targeted Client
     * @return the Client with the corresponding id
     */
    default Client searchClient(int id) {
        return PostOffice.searchClient(id);
    }

    /**
     * displays all deliveries in the Post-Office's list of Delivery
     * @param sorting the sorting format of the display
     */
    default void viewAllDelivery(String sorting) {
        PostOffice.deliveries.sort(new Delivery.DeliveryComparator(sorting));

        for (Delivery delivery : PostOffice.deliveries) {
            if (delivery instanceof Parcel p) {
                System.out.printf("Parcel : %s", p);
            }
            if (delivery instanceof Mail m) {
                System.out.printf("Message : %s", m);
            }
        }
    }

    /**
     * arbitrary removes a Delivery from the Post-Office's system
     * @param id the targeted Delivery's id
     */
    default void removePostOfficeDelivery(int id) {
        PostOffice.deliveries.remove(searchDeliverySystem(id));
        PostOffice.exportData();
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
}
