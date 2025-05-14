import org.example.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;

public class StaffTest {
    PostOffice postOffice = new PostOffice();

    @Test
    public void registerTest1() {
        Staff staff = new Staff("Simon", "Simon@hotmail.com");
        staff.register("Yakuza");

        Assertions.assertTrue(
                PostOffice.staffs.contains(staff)
        );
    }

    @Test
    public void registerTest2() {
        Staff staff = new Staff("Charles", "Lamar@hotmail.com");
        staff.register("Willpower");

        Assertions.assertTrue(
                PostOffice.staffs.contains(staff)
        );
    }

    @Test
    public void assignProcessedParcelTest1() {
        Staff staff = (Staff) PostOffice.searchUser("Nathan@hotmail.com");

        staff.sendParcel("Vinyl", "A vinyl record of Frank Sinatra", 2.00, 1, "NathanChg@hotmail.com");

        staff.assignProcessedParcel(staff.searchCourier(2, staff.searchCouriers()));

        Parcel parcel = ((Courier) PostOffice.searchUser("Quang@hotmail.com")).getParcels().getLast();

        Assertions.assertTrue(
                parcel.getItem().getName().equalsIgnoreCase("Vinyl")
                && parcel.getItem().getWeight() == 2
                && parcel.getQuantity() == 1
                && parcel.getDescription().equalsIgnoreCase("A vinyl record of Frank Sinatra")
                && parcel.getEmail().equalsIgnoreCase("NathanChg@hotmail.com")
                && parcel.getCourier().equals(PostOffice.searchUser("Quang@hotmail.com"))
                );
    }

    @Test
    public void assignProcessedParcelTest2() {
        Staff staff = (Staff) PostOffice.searchUser("Nathan@hotmail.com");

        staff.sendParcel("Vinyl", "A very 1999 christmas", 2.00, 1, "NathanChg@hotmail.com");

        staff.assignProcessedParcel(staff.searchCourier(3, staff.searchCouriers()));

        Parcel parcel = ((Courier) PostOffice.searchUser("Ethan@gmail.com")).getParcels().getLast();

        Assertions.assertTrue(
                parcel.getItem().getName().equalsIgnoreCase("Vinyl")
                        && parcel.getItem().getWeight() == 2
                        && parcel.getQuantity() == 1
                        && parcel.getDescription().equalsIgnoreCase("A very 1999 christmas")
                        && parcel.getEmail().equalsIgnoreCase("NathanChg@hotmail.com")
                        && parcel.getCourier().equals(PostOffice.searchUser("Ethan@gmail.com"))
        );
    }

    @Test
    public void replyTicketTest1() {
        Staff staff = (Staff) PostOffice.searchUser("Nathan@hotmail.com");

        int previousSize = PostOffice.openedTickets.size();
        Ticket ticket = PostOffice.openedTickets.poll();

        staff.replyTicket(ticket, "Oops");

        Ticket newTicket = PostOffice.completedTickets.getLast();

        Assertions.assertTrue(
                PostOffice.openedTickets.size() == (previousSize - 1)
                        && newTicket.getTicketId() == ticket.getTicketId()
                        && newTicket.getClient().equals(ticket.getClient())
                        && newTicket.getStaff().equals(staff)
                        && newTicket.getCreationTime().equals(ticket.getCreationTime())
        );
    }

    @Test
    public void replyTicketTest2() {
        PostOffice.importData();
        Staff staff = (Staff) PostOffice.searchUser("LClucky@hotmail.com");

        int previousSize = PostOffice.openedTickets.size();
        Ticket ticket = PostOffice.openedTickets.poll();

        staff.replyTicket(ticket, "Oops");

        Ticket newTicket = PostOffice.completedTickets.getLast();

        Assertions.assertTrue(
                PostOffice.openedTickets.size() == (previousSize - 1)
                        && newTicket.getTicketId() == ticket.getTicketId()
                        && newTicket.getClient().equals(ticket.getClient())
                        && newTicket.getStaff().equals(staff)
                        && newTicket.getCreationTime().equals(ticket.getCreationTime())
        );
    }

    @Test
    public void viewClientTest1() {
        Assertions.assertTrue(PostOffice.searchStaff(0).viewClient(""));
    }

    @Test
    public void viewClientTest2() {
        Assertions.assertTrue(PostOffice.searchStaff(1).viewClient(""));
    }

    @Test
    public void viewStaffTest1() {
        Assertions.assertTrue(PostOffice.searchStaff(0).viewStaff(""));
    }

    @Test
    public void viewStaffTest2() {
        Assertions.assertTrue(PostOffice.searchStaff(1).viewStaff(""));
    }

    @Test
    public void viewCourierListTest1() {
        List<Courier> couriers = PostOffice.searchStaff(0).searchCouriers();

        Assertions.assertTrue(
                couriers.size() == 2
                && couriers.get(0).getStaffId() == 2
                && couriers.get(0).getName().equalsIgnoreCase("Vinh")
                && couriers.get(0).getEmail().equalsIgnoreCase("Quang@hotmail.com")
                && couriers.get(1).getStaffId() == 3
                && couriers.get(1).getName().equalsIgnoreCase("Kim")
                && couriers.get(1).getEmail().equalsIgnoreCase("Ethan@gmail.com")
        );
    }

    @Test
    public void viewCourierListTest2() {
        List<Courier> couriers = PostOffice.searchStaff(1).searchCouriers();

        Assertions.assertTrue(
                couriers.size() == 2
                        && couriers.get(0).getStaffId() == 2
                        && couriers.get(0).getName().equalsIgnoreCase("Vinh")
                        && couriers.get(0).getEmail().equalsIgnoreCase("Quang@hotmail.com")
                        && couriers.get(1).getStaffId() == 3
                        && couriers.get(1).getName().equalsIgnoreCase("Kim")
                        && couriers.get(1).getEmail().equalsIgnoreCase("Ethan@gmail.com")
        );
    }

    @Test
    public  void searchCourierTest1() {
        Staff staff = PostOffice.searchStaff(0);
        Courier courier = staff.searchCourier(2, staff.searchCouriers());

        Assertions.assertTrue(
                courier.getStaffId() == 2
                && courier.getName().equalsIgnoreCase("Vinh")
                && courier.getEmail().equalsIgnoreCase("Quang@hotmail.com")
        );
    }

    @Test
    public  void searchCourierTest2() {
        Staff staff = PostOffice.searchStaff(1);
        Courier courier = staff.searchCourier(3, staff.searchCouriers());

        Assertions.assertTrue(
                courier.getStaffId() == 3
                        && courier.getName().equalsIgnoreCase("Kim")
                        && courier.getEmail().equalsIgnoreCase("Ethan@gmail.com")
        );
    }

    @Test
    public void viewAllDeliveryTest1() {
        Assertions.assertTrue(PostOffice.searchStaff(0).viewAllDelivery(""));
    }

    @Test
    public void viewAllDeliveryTest2() {
        Assertions.assertTrue(PostOffice.searchStaff(1).viewAllDelivery("reverse"));
    }

    @Test
    public void removePostOfficeDeliveryTest1() {
        int previousSize = PostOffice.deliveries.size();
        PostOffice.searchStaff(0).removePostOfficeDelivery(0);

        Assertions.assertEquals(PostOffice.deliveries.size(), previousSize - 1);
    }

    @Test
    public void removePostOfficeDeliveryTest2() {
        PostOffice.importData();
        int previousSize = PostOffice.deliveries.size();
        PostOffice.searchStaff(0).removePostOfficeDelivery(1);

        Assertions.assertEquals(PostOffice.deliveries.size(), previousSize - 1);
    }

    @Test
    public void searchDeliverySystemTest1() {
        PostOffice.importData();
        Staff staff = PostOffice.searchStaff(0);

        Parcel parcel = (Parcel) staff.searchDeliverySystem(0);

        Assertions.assertTrue(
                parcel.getDeliveryId() == 0
                        && parcel.equals(PostOffice.deliveries.get(0))
        );
    }

    @Test
    public void searchDeliverySystemTest2() {
        PostOffice.importData();
        Staff staff = PostOffice.searchStaff(0);

        Mail mail = (Mail) staff.searchDeliverySystem(2);

        Assertions.assertTrue(
                mail.getDeliveryId() == 2
                        && mail.equals(PostOffice.deliveries.get(2))
        );
    }

    @Test
    public void viewAllAdsTest1() {
        Assertions.assertTrue(PostOffice.searchStaff(0).viewAllAds());
    }

    @Test
    public void viewAllAdsTest2() {
        Assertions.assertTrue(PostOffice.searchStaff(1).viewAllAds());
    }
}
