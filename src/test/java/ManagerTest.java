import org.example.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;

public class ManagerTest {
    PostOffice postOffice = new PostOffice();

    @Test
    public void reassignTest1() {
        Manager manager = PostOffice.manager;

        Staff staff = PostOffice.searchStaffByEmail("Nathan@hotmail.com");

        manager.reassign(staff);

        Assertions.assertTrue(
                staff.getRole().equals(Staff.Role.COURIER)
        );
    }

    @Test
    public void reassignTest2() {
        Manager manager = PostOffice.manager;

        Staff staff = PostOffice.searchStaffByEmail("Quang@hotmail.com");

        manager.reassign(staff);

        Assertions.assertTrue(
                staff.getRole().equals(Staff.Role.INDOORS)
        );
    }

    @Test
    public void viewClientTest1() {
        Assertions.assertTrue(PostOffice.manager.viewClient(""));
    }

    @Test
    public void viewClientTest2() {
        Assertions.assertTrue(PostOffice.manager.viewClient("name descendingly"));
    }

    @Test
    public void viewStaffTest1() {
        Assertions.assertTrue(PostOffice.manager.viewStaff(""));
    }

    @Test
    public void viewStaffTest2() {
        Assertions.assertTrue(PostOffice.manager.viewStaff("name descendingly"));
    }

    @Test
    public void searchCouriersTest1() { //This method does not need a second test since its function is too generic
        PostOffice.importData();
        List<Courier> couriers = PostOffice.manager.searchCouriers();

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
    public void searchCourierTest1() {
        PostOffice.importData();
        Courier courier = PostOffice.manager.searchCourier(2, PostOffice.manager.searchCouriers());

        Assertions.assertTrue(
                courier.getStaffId() == 2
                        && courier.getName().equalsIgnoreCase("Vinh")
                        && courier.getEmail().equalsIgnoreCase("Quang@hotmail.com")
        );
    }

    @Test
    public void searchCourierTest2() {
        PostOffice.importData();
        Courier courier = PostOffice.manager.searchCourier(3, PostOffice.manager.searchCouriers());

        Assertions.assertTrue(
                courier.getStaffId() == 3
                        && courier.getName().equalsIgnoreCase("Kim")
                        && courier.getEmail().equalsIgnoreCase("Ethan@gmail.com")
        );
    }

    @Test
    public void viewAllDeliveryTest1() {
        Assertions.assertTrue(PostOffice.manager.viewAllDelivery(""));
    }

    @Test
    public void viewAllDeliveryTest2() {
        Assertions.assertTrue(PostOffice.manager.viewAllDelivery("reverse"));
    }

    @Test
    public void removePostOfficeDeliveryTest1() {
        int previousSize = PostOffice.deliveries.size();
        PostOffice.manager.removePostOfficeDelivery(0);

        Assertions.assertEquals(PostOffice.deliveries.size(), previousSize - 1);
    }

    @Test
    public void removePostOfficeDeliveryTest2() {
        int previousSize = PostOffice.deliveries.size();
        PostOffice.manager.removePostOfficeDelivery(1);

        Assertions.assertEquals(PostOffice.deliveries.size(), previousSize - 1);
    }

    @Test
    public void searchDeliverySystemTest1() {
        PostOffice.importData();
        Parcel parcel = (Parcel) PostOffice.manager.searchDeliverySystem(0);

        Assertions.assertTrue(
                parcel.getDeliveryId() == 0
                        && parcel.equals(PostOffice.deliveries.get(0))
        );
    }

    @Test
    public void searchDeliverySystemTest2() {
        PostOffice.importData();
        Mail mail = (Mail) PostOffice.manager.searchDeliverySystem(2);

        Assertions.assertTrue(
                mail.getDeliveryId() == 2
                        && mail.equals(PostOffice.deliveries.get(2))
        );
    }
}
