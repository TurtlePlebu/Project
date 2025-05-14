import org.example.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class CourierTest {

    @Test
    public void viewOngointParcelsTest1() {
        Staff staff = PostOffice.searchStaff(0);
        staff.sendParcel("TCG Cards", "Pokemon cards", 2.5, 10, "NathanChg@hotmail.com");
        staff.assignProcessedParcel(staff.searchCourier(2, staff.searchCouriers()));

        Assertions.assertTrue(staff.searchCourier(2, staff.searchCouriers()).viewOngoingParcels(""));
    }

    @Test
    public void viewOngointParcelsTest2() {
        PostOffice.importData();
        Staff staff = PostOffice.searchStaff(0);
        staff.sendParcel("TCG Cards", "YuGiOh cards", 2.5, 10, "NathanChg@hotmail.com");
        staff.assignProcessedParcel(staff.searchCourier(3, staff.searchCouriers()));

        Assertions.assertTrue(staff.searchCourier(3, staff.searchCouriers()).viewOngoingParcels(""));
    }

    @Test
    public void pickupParcelTest1() {
        PostOffice.importData();
        Staff staff = PostOffice.searchStaff(0);
        staff.sendParcel("TCG Cards", "HearthStone cards", 2.5, 10, "NathanChg@hotmail.com");
        staff.assignProcessedParcel(staff.searchCourier(2, staff.searchCouriers()));

        Courier courier = staff.searchCourier(2, staff.searchCouriers());
        Parcel parcel = courier.getParcels().getLast();

        Assertions.assertTrue(
                parcel.getItem().getName().equalsIgnoreCase("TCG Cards")
                && parcel.getItem().getWeight() == 2.5
                && parcel.getDescription().equalsIgnoreCase("HearthStone cards")
                && parcel.getEmail().equalsIgnoreCase("NathanChg@hotmail.com")
                && parcel.getCourier().getStaffId() == 2
                && parcel.getCourier().getName().equalsIgnoreCase("Vinh")
                && parcel.getCourier().getEmail().equalsIgnoreCase("Quang@hotmail.com")
        );
    }

    @Test
    public void pickupParcelTest2() {
        PostOffice.importData();
        Staff staff = PostOffice.searchStaff(0);
        staff.sendParcel("TCG Cards", "Magic the Gathering cards", 2.5, 10, "NathanChg@hotmail.com");
        staff.assignProcessedParcel(staff.searchCourier(3, staff.searchCouriers()));

        Courier courier = staff.searchCourier(3, staff.searchCouriers());
        Parcel parcel = courier.getParcels().getLast();

        Assertions.assertTrue(
                parcel.getItem().getName().equalsIgnoreCase("TCG Cards")
                        && parcel.getItem().getWeight() == 2.5
                        && parcel.getDescription().equalsIgnoreCase("Magic the Gathering cards")
                        && parcel.getEmail().equalsIgnoreCase("NathanChg@hotmail.com")
                        && parcel.getCourier().getStaffId() == 3
                        && parcel.getCourier().getName().equalsIgnoreCase("Kim")
                        && parcel.getCourier().getEmail().equalsIgnoreCase("Ethan@gmail.com")
        );
    }

    @Test
    public void deliverTest1() {
        PostOffice.importData();
        Staff staff = PostOffice.searchStaff(0);
        staff.sendParcel("TCG Cards", "HearthStone cards", 2.5, 10, "NathanChg@hotmail.com");
        staff.assignProcessedParcel(staff.searchCourier(2, staff.searchCouriers()));

        Courier courier = staff.searchCourier(2, staff.searchCouriers());
        Parcel parcel = courier.getParcels().getLast();

        courier.deliver(parcel);

        User receiver = PostOffice.searchUser("NathanChg@hotmail.com");
        Parcel receivedParcel = (Parcel) receiver.getDeliveries().getLast();

        Assertions.assertTrue(
                courier.getParcels().isEmpty()
                && receivedParcel.getItem().getName().equalsIgnoreCase("TCG Cards")
                && receivedParcel.getItem().getWeight() == 2.5
                && receivedParcel.getDescription().equalsIgnoreCase("HearthStone cards")
                && receiver.getEmail().equalsIgnoreCase("NathanChg@hotmail.com")
                && receivedParcel.getCourier().getStaffId() == 2
                && receivedParcel.getCourier().getName().equalsIgnoreCase("Vinh")
                && receivedParcel.getCourier().getEmail().equalsIgnoreCase("Quang@hotmail.com")
                && receivedParcel.getStatus().equals(Delivery.Status.DELIVERED)
        );
    }

    @Test
    public void deliverTest2() {
        PostOffice.importData();
        Staff staff = PostOffice.searchStaff(0);
        staff.sendParcel("TCG Cards", "Magic the Gathering cards", 2.5, 10, "NathanChg@hotmail.com");
        staff.assignProcessedParcel(staff.searchCourier(3, staff.searchCouriers()));

        Courier courier = staff.searchCourier(3, staff.searchCouriers());
        Parcel parcel = courier.getParcels().getLast();

        courier.deliver(parcel);

        User receiver = PostOffice.searchUser("NathanChg@hotmail.com");
        Parcel receivedParcel = (Parcel) receiver.getDeliveries().getLast();

        Assertions.assertTrue(
                courier.getParcels().isEmpty()
                        && receivedParcel.getItem().getName().equalsIgnoreCase("TCG Cards")
                        && receivedParcel.getItem().getWeight() == 2.5
                        && receivedParcel.getDescription().equalsIgnoreCase("Magic the Gathering cards")
                        && receiver.getEmail().equalsIgnoreCase("NathanChg@hotmail.com")
                        && receivedParcel.getCourier().getStaffId() == 3
                        && receivedParcel.getCourier().getName().equalsIgnoreCase("Kim")
                        && receivedParcel.getCourier().getEmail().equalsIgnoreCase("Ethan@gmail.com")
                        && receivedParcel.getStatus().equals(Delivery.Status.DELIVERED)
        );
    }

    @Test
    public void searchParcelTest1() {
        PostOffice.importData();
        Staff staff = PostOffice.searchStaff(0);
        staff.sendParcel("TCG Cards", "HearthStone cards", 2.5, 10, "NathanChg@hotmail.com");
        staff.assignProcessedParcel(staff.searchCourier(2, staff.searchCouriers()));

        Courier courier = staff.searchCourier(2, staff.searchCouriers());
        Parcel parcel = courier.searchParcel(2);

        Assertions.assertTrue(
                parcel.getItem().getName().equalsIgnoreCase("TCG Cards")
                        && parcel.getItem().getWeight() == 2.5
                        && parcel.getDescription().equalsIgnoreCase("HearthStone cards")
                        && parcel.getEmail().equalsIgnoreCase("NathanChg@hotmail.com")
                        && parcel.getCourier().getStaffId() == 2
                        && parcel.getCourier().getName().equalsIgnoreCase("Vinh")
                        && parcel.getCourier().getEmail().equalsIgnoreCase("Quang@hotmail.com")
        );
    }

    @Test
    public void searchParcelTest2() {
        PostOffice.importData();
        Staff staff = PostOffice.searchStaff(0);
        staff.sendParcel("TCG Cards", "Magic the Gathering cards", 2.5, 10, "NathanChg@hotmail.com");
        staff.assignProcessedParcel(staff.searchCourier(3, staff.searchCouriers()));

        Courier courier = staff.searchCourier(3, staff.searchCouriers());
        Parcel parcel = courier.searchParcel(2);

        Assertions.assertTrue(
                parcel.getItem().getName().equalsIgnoreCase("TCG Cards")
                        && parcel.getItem().getWeight() == 2.5
                        && parcel.getDescription().equalsIgnoreCase("Magic the Gathering cards")
                        && parcel.getEmail().equalsIgnoreCase("NathanChg@hotmail.com")
                        && parcel.getCourier().getStaffId() == 3
                        && parcel.getCourier().getName().equalsIgnoreCase("Kim")
                        && parcel.getCourier().getEmail().equalsIgnoreCase("Ethan@gmail.com")
        );
    }
}
