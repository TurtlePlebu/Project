import org.example.*;
import org.junit.jupiter.api.Test;

public class UserInterfaceTest {
    @Test
    public void menuTest() {
        PostOffice postOffice = new PostOffice();
        UserInterface.menu();
    }
}
