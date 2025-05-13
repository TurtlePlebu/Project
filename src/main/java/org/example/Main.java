package org.example;

import java.time.LocalDateTime;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Staff staff = new Staff("Nathan", "NathanChg@hotmail.com"); String password1 = "Turtle";
        Courier courier = new Courier("Vinh", "Quang@hotmail.com"); String password2 = "Personal5";
        Client client = new Client("Nathan", "NathanChg@hotmail.com", "288 rue de boheme"); String password3 = "Pinotta";
        Parcel parcel = new Parcel("Rue de boheme", "Computer Mouse", LocalDateTime.now(), Delivery.Status.DELIVERED, new Item("Mouse", 1.6, LocalDateTime.now()),1, courier, "NathanChg@hotmail.com");
        Mail mail = new Mail("Rue de boheme", "This message is a test", LocalDateTime.now(), Delivery.Status.DELIVERED,"Testing", "NathanChg@hotmail.com");
        Advertisement ad = new Advertisement("Get your discounted price for a new AI mop", LocalDateTime.now(), "AIMop", "MOP Industry", "MOP@Industry.com");
        Ticket closed = new Ticket("The new application is littered with AI", "All I see is AI generated posting", client, Ticket.Type.BUGREPORT, Ticket.TicketStatus.CLOSED, staff, LocalDateTime.now());
        Ticket processing = new Ticket("This view is read only", "Help IntelliJ's console is stuck in \'This view is read only\'", client, Ticket.Type.SUPPORT, Ticket.TicketStatus.PROCESSING, staff, LocalDateTime.now() );
        Ticket open = new Ticket("Missing Parcel", "I didn't receive my parcel last week", client, Ticket.Type.SUPPORT, Ticket.TicketStatus.OPEN, staff, LocalDateTime.now());

        PostOffice.clients.add(client);
        PostOffice.clientSecurityPass.put(client, password1);
        PostOffice.staffs.add(staff);
        PostOffice.staffSecurityPass.put(staff, password3);
        PostOffice.staffs.add(courier);
        PostOffice.staffSecurityPass.put(courier, password2);
        PostOffice.deliveries.add(parcel);
        PostOffice.deliveries.add(mail);
        PostOffice.advertisements.add(ad);
        PostOffice.openedTickets.offer(open);
        PostOffice.ongoingTickets.add(processing);
        PostOffice.completedTickets.add(closed);

        PostOffice.exportData();
    }
}
