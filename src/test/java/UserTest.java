import org.example.PostOffice;
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


}
