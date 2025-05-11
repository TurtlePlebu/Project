import org.example.Client;
import org.example.PostOffice;
import org.example.Staff;
import org.example.UserInterface;
import org.junit.jupiter.api.Test;

public class UserInterfaceTest {
    @Test
    public void menuTest() {
        PostOffice postOffice = new PostOffice();
        UserInterface.menu();
    }
}
