import org.example.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

public class PostOfficeIOTest {
    //exportData() may only be performed once
    //importData can be performed multiple times

//    @Test
//    public void exportData_Test1() {
//        Staff staff = new Staff("Nathan", "Nathan@hotmail.com"); String password3 = "Pinotta";
//        Courier courier = new Courier("Vinh", "Quang@hotmail.com"); String password2 = "Personal5";
//        Client client = new Client("Nathan", "NathanChg@hotmail.com", "rue de boheme"); String password1 = "Turtle";
//
//        Parcel parcel = new Parcel("Rue de boheme", "Computer Mouse", LocalDateTime.now(), Delivery.Status.DELIVERED, new Item("Mouse", 1.6, LocalDateTime.now()),1, courier, "NathanChg@hotmail.com");
//        Mail mail = new Mail("Rue de boheme", "This message is a test", LocalDateTime.now(), Delivery.Status.DELIVERED,"Testing", "NathanChg@hotmail.com");
//        Advertisement ad = new Advertisement("Get your discounted price for a new AI mop", LocalDateTime.now(), "AIMop", "MOP Industry", "MOP@Industry.com");
//
//        Ticket closed = new Ticket("The new application is littered with AI", "All I see is AI generated posting", client, Ticket.Type.BUGREPORT, Ticket.TicketStatus.CLOSED, staff, LocalDateTime.now());
//        Ticket processing = new Ticket("This view is read only", "Help IntelliJ's console is stuck in \'This view is read only\'", client, Ticket.Type.SUPPORT, Ticket.TicketStatus.PROCESSING, staff, LocalDateTime.now() );
//        Ticket open = new Ticket("Missing Parcel", "I didn't receive my parcel last week", client, Ticket.Type.SUPPORT, Ticket.TicketStatus.OPEN, staff, LocalDateTime.now());
//
//        PostOffice.clients.add(client);
//        PostOffice.clientSecurityPass.put(client, password1);
//        PostOffice.staffs.add(staff);
//        PostOffice.staffSecurityPass.put(staff, password3);
//        PostOffice.staffs.add(courier);
//        PostOffice.staffSecurityPass.put(courier, password2);
//
//        PostOffice.deliveries.add(parcel);
//        PostOffice.deliveries.add(mail);
//        PostOffice.advertisements.add(ad);
//
//        PostOffice.openedTickets.offer(open);
//        PostOffice.ongoingTickets.add(processing);
//        PostOffice.completedTickets.add(closed);
//
//
//        Assertions.assertTrue(PostOffice.exportData());
//    }

    @Test
    public void exportData_Test2() {
        Staff staff = new Staff("Nathan", "Nathan@hotmail.com"); String password3 = "Pinotta";
        Staff staff2 = new Staff("Luca", "LClucky@hotmail.com"); String password4 = "Mythical";
        Courier courier = new Courier("Vinh", "Quang@hotmail.com"); String password2 = "Personal5";
        Courier courier2 = new Courier("Kim", "Ethan@gmail.com"); String password5 = "Mona";
        Client client = new Client("Nathan", "NathanChg@hotmail.com", "rue de boheme"); String password1 = "Turtle";
        Client client2 = new Client("Jacques", "Jpc@hotmail.com", "100e avenue"); String password6 = "hotbread";

        Parcel parcel = new Parcel("Rue de boheme", "Computer Mouse", LocalDateTime.now().plusDays(7), Delivery.Status.DELIVERED, new Item("Mouse", 1.6, LocalDateTime.now()),1, courier, "NathanChg@hotmail.com");
        Parcel parcel2 = new Parcel("100e avenue", "dumbbell", LocalDateTime.now().plusDays(5), Delivery.Status.DELIVERED, new Item("Mouse", 30, LocalDateTime.now()),2, courier2, "Jpc@hotmail.com");
        Mail mail = new Mail("Rue de boheme", "This message is a test", LocalDateTime.now(), Delivery.Status.DELIVERED,"Testing", "NathanChg@hotmail.com");
        Mail mail2 = new Mail("100e avenue", "I need help", LocalDateTime.now(), Delivery.Status.DELIVERED,"Assignment", "Jpc@hotmail.com");
        Advertisement ad = new Advertisement("Get your discounted price for a new AI mop", LocalDateTime.now(), "AIMop", "MOP Industry", "MOP@Industry.com");
        Advertisement ad2 = new Advertisement("Feeling lonely? Come get a companion!", LocalDateTime.now(), "ArtificialBunker", "ModernShelter", "Artificial@MyOnlineShelter.com");

        Ticket closed = new Ticket("The new application is littered with AI", "All I see is AI generated posting", client, Ticket.Type.BUGREPORT, Ticket.TicketStatus.CLOSED, staff, LocalDateTime.now());
        Ticket closed2 = new Ticket("Subscription", "I need a better deal or subscription. This one is out of my budget.", client2, Ticket.Type.SUPPORT, Ticket.TicketStatus.CLOSED, staff2, LocalDateTime.now());
        Ticket processing = new Ticket("This view is read only", "Help IntelliJ's console is stuck in \'This view is read only\'", client, Ticket.Type.SUPPORT, Ticket.TicketStatus.PROCESSING, staff2, LocalDateTime.now() );
        Ticket processing2 = new Ticket("Locked Account", "My account is said to be unreachable.", client2, Ticket.Type.BUGREPORT, Ticket.TicketStatus.PROCESSING, staff, LocalDateTime.now() );
        Ticket open = new Ticket("Missing Parcel", "I didn't receive my parcel last week", client2, Ticket.Type.SUPPORT, Ticket.TicketStatus.OPEN, staff2, LocalDateTime.now());
        Ticket open2 = new Ticket("BSOD ", "My client is causing me blue screens", client, Ticket.Type.BUGREPORT, Ticket.TicketStatus.OPEN, staff, LocalDateTime.now());

        PostOffice.clients.add(client);
        PostOffice.clientSecurityPass.put(client, password1);
        PostOffice.clients.add(client2);
        PostOffice.clientSecurityPass.put(client2, password6);

        PostOffice.staffs.add(staff);
        PostOffice.staffSecurityPass.put(staff, password3);
        PostOffice.staffs.add(staff2);
        PostOffice.staffSecurityPass.put(staff2, password4);

        PostOffice.staffs.add(courier);
        PostOffice.staffSecurityPass.put(courier, password2);
        PostOffice.staffs.add(courier2);
        PostOffice.staffSecurityPass.put(courier2, password5);

        PostOffice.deliveries.add(parcel);
        PostOffice.deliveries.add(parcel2);
        PostOffice.deliveries.add(mail);
        PostOffice.deliveries.add(mail2);

        PostOffice.advertisements.add(ad);
        PostOffice.advertisements.add(ad2);

        PostOffice.openedTickets.offer(open);
        PostOffice.openedTickets.offer(open2);
        PostOffice.ongoingTickets.add(processing);
        PostOffice.ongoingTickets.add(processing2);
        PostOffice.completedTickets.add(closed);
        PostOffice.completedTickets.add(closed2);


        Assertions.assertTrue(PostOffice.exportData());
    }

    @Test
    public void importDataTest1() {
        Assertions.assertTrue(PostOffice.importData());
    }

    @Test
    public void importDataTest2() {
        Assertions.assertTrue(
                PostOffice.name.equalsIgnoreCase("Canada Post") &&
                        PostOffice.address.equalsIgnoreCase("2000 Blvd. Marcel-Laurin Saint-Laurent") &&
                        PostOffice.email.equalsIgnoreCase("CanadaPost@hotmail.com") &&
                        PostOffice.manager.getName().equalsIgnoreCase("Nathan") &&
                        PostOffice.manager.getEmail().equalsIgnoreCase("CanadaPostManagement@CanadaPost.com") &&
                        (PostOffice.staffs.size() == 4) &&
                        (PostOffice.clients.size() == 2) &&
                        (PostOffice.deliveries.size() == 4) &&
                        (PostOffice.advertisements.size() == 2) &&
                        (PostOffice.openedTickets.size() == 2) &&
                        (PostOffice.ongoingTickets.size() == 2) &&
                        (PostOffice.completedTickets.size() == 2) &&
                        (PostOffice.staffSecurityPass.size() == 4) &&
                        (PostOffice.clientSecurityPass.size() == 2)
        );
    }

}
