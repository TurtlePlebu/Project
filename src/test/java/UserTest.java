import org.example.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class UserTest {
    PostOffice postOffice = new PostOffice();

    @Test
    public void sendMailTest1() {
        Assertions.assertTrue(
                PostOffice.searchUser("Nathan@hotmail.com")
                        .sendMail("Testing sendingMail function 1", "Testing Mail 1", "NathanChg@hotmail.com")
        );
    }

    @Test
    public void sendMailTest2() {
        Assertions.assertTrue(
                PostOffice.searchUser("Jpc@hotmail.com")
                        .sendMail("Testing sendingMail function 2", "Testing Mail 2", "Nathan@hotmail.com") &&
                        PostOffice.searchUser("Nathan@hotmail.com")
                                .getDeliveries()
                                .getLast().getDescription().equalsIgnoreCase("Testing sendingMail function 2")
        );
    }

    @Test
    public void sendParcelTest1() {
        Staff staff = (Staff) PostOffice.searchUser("Nathan@hotmail.com");

        staff.sendParcel("Ball", "Stress Ball", 2.00, 1, "NathanChg@hotmail.com");

        staff.assignProcessedParcel(staff.searchCourier(3, staff.searchCouriers()));

        Parcel parcel = ((Courier) PostOffice.searchUser("Ethan@gmail.com")).getParcels().getLast();

        Assertions.assertTrue(
                (
                parcel.getItem().getName().equalsIgnoreCase("Ball")
                && parcel.getItem().getWeight() == 2
                && parcel.getQuantity() == 1
                && parcel.getDescription().equalsIgnoreCase("Stress Ball")
                && parcel.getCourier().equals(staff.searchCourier(3, staff.searchCouriers()))
                )
        );
    }

    @Test
    public void sendParcelTest2() {
        Staff staff = (Staff) PostOffice.searchUser("Nathan@hotmail.com");

        staff.sendParcel("Box", "Stress Box", 4.00, 1, "Jpc@hotmail.com");

        staff.assignProcessedParcel(staff.searchCourier(2, staff.searchCouriers()));

        Parcel parcel = ((Courier) PostOffice.searchUser("Quang@hotmail.com")).getParcels().getLast();

        Assertions.assertTrue(
                (
                        parcel.getItem().getName().equalsIgnoreCase("Box")
                                && parcel.getItem().getWeight() == 4
                                && parcel.getQuantity() == 1
                                && parcel.getDescription().equalsIgnoreCase("Stress Box")
                                && parcel.getCourier().equals(staff.searchCourier(2, staff.searchCouriers()))
                )
        );
    }

    @Test
    public void removeDeliveryTest1() {
        User user = PostOffice.searchUser("NathanChg@hotmail.com");
        Delivery target = user.getDeliveries().getFirst(); //Should be a Parcel object
        int previousSize = user.getDeliveries().size();
        int previousSystemSize = PostOffice.deliveries.size();
        user.removeDelivery(target);

        Assertions.assertTrue(
                user.getDeliveries().size() == (previousSize - 1)
                && PostOffice.deliveries.size() == (previousSystemSize - 1)
        );
    }

    @Test
    public void removeDeliveryTest2() {
        PostOffice.importData();
        User user = PostOffice.searchUser("NathanChg@hotmail.com");
        Delivery target = user.getDeliveries().getLast(); //Should be a Mail object
        int previousSize = user.getDeliveries().size();
        int previousSystemSize = PostOffice.deliveries.size();
        user.removeDelivery(target);

        Assertions.assertTrue(
                user.getDeliveries().size() == (previousSize - 1)
                        && PostOffice.deliveries.size() == (previousSystemSize - 1)
        );
    }

    @Test
    public void searchDeliveryTest1() {
        User user = PostOffice.searchUser("NathanChg@hotmail.com");
        Parcel target = (Parcel) user.searchDelivery(0);

        Assertions.assertTrue(
                target.getDescription().equalsIgnoreCase("Computer Mouse")
                && target.getAddress().equalsIgnoreCase("rue de boheme")
                && target.getParcelId() == 0
                && target.getQuantity() == 1
                && target.getItem().getName().equalsIgnoreCase("Mouse")
                && target.getItem().getWeight() == 1.6
                && target.getCourier().equals(PostOffice.searchStaff(target.getCourier().getStaffId()))
        );
    }

    @Test
    public void searchDeliveryTest2() {
        User user = PostOffice.searchUser("NathanChg@hotmail.com");
        Mail target = (Mail) user.searchDelivery(2);

        Assertions.assertTrue(
                target.getDescription().equalsIgnoreCase("This message is a test")
                        && target.getTitle().equalsIgnoreCase("Testing")
                        && target.getAddress().equalsIgnoreCase("Rue de boheme")
                        && target.getEmail().equalsIgnoreCase("NathanChg@hotmail.com")
        );
    }

    @Test
    public void viewDeliveryTest1() {
        Assertions.assertFalse(PostOffice.searchUser("Nathan@hotmail.com").viewDelivery(""));
    }

    @Test
    public void viewDeliveryTest2() {
        Assertions.assertTrue(PostOffice.searchUser("NathanChg@hotmail.com").viewDelivery(""));
    }

    @Test
    public void checkDeliveryTest1() {
        Assertions.assertTrue(PostOffice.searchUser("NathanChg@hotmail.com").checkDelivery(0));
    }

    @Test
    public void checkDeliveryTest2() {
        Assertions.assertFalse(PostOffice.searchUser("Nathan@hotmail.com").checkDelivery(9));
    }
}
