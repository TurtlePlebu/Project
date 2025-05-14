import org.example.Client;
import org.example.Parcel;
import org.example.PostOffice;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class ClientTest {

    PostOffice postOffice = new PostOffice();

    @Test
    public void registerTest1() {
        Client client = new Client("ChiTai", "Tai@gmail.com", "Samson");
        client.register("Optimus");

        Assertions.assertTrue(
                PostOffice.clients.contains(client)
        );
    }

    @Test
    public void registerTest2() {
        Client client = new Client("Patrick", "Lyon@gmail.com", "50th Street");
        client.register("Yoyo");

        Assertions.assertTrue(
                PostOffice.clients.contains(client)
        );
    }

    @Test
    public void viewDeliveryTest1() {
        Assertions.assertTrue(PostOffice.searchClientByEmail("NathanChg@hotmail.com").viewDelivery(""));
    }

    @Test
    public void viewDeliveryTest2() {
        Assertions.assertTrue(PostOffice.searchClientByEmail("Jpc@hotmail.com").viewDelivery("reverse"));
    }

    @Test
    public void sendBugReportTest1() {
        Client client = PostOffice.searchClient(0);

        int previousSize = PostOffice.openedTickets.size();
        client.sendBugReport("Frozen Screen", "My laptop is frozen when opening the application");

        Assertions.assertTrue(
                PostOffice.openedTickets.size() == previousSize + 1
        );
    }

    @Test
    public void sendBugReportTest2() {
        Client client = PostOffice.searchClient(1);

        int previousSize = PostOffice.openedTickets.size();
        client.sendBugReport("Out of memory", "My PC cannot open this application without crashing");

        Assertions.assertTrue(
                PostOffice.openedTickets.size() == previousSize + 1
        );
    }

    @Test
    public void sendSupportRequestTest1() {
        Client client = PostOffice.searchClient(0);

        int previousSize = PostOffice.openedTickets.size();
        client.sendSupportRequest("Git does not let me push", "IntelliJ does not let push me push my commits");

        Assertions.assertTrue(
                PostOffice.openedTickets.size() == previousSize + 1
        );
    }

    @Test
    public void sendSupportRequestTest2() {
        Client client = PostOffice.searchClient(1);

        int previousSize = PostOffice.openedTickets.size();
        client.sendSupportRequest("Missing Parcel", "Why is my package 4 days late?");

        Assertions.assertTrue(
                PostOffice.openedTickets.size() == previousSize + 1
        );
    }
}
