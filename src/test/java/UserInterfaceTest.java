import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.example.UserInterface.menu;

public class UserInterfaceTest {

    @Test
    public void menuTest() {

        menu();

        Assertions.assertTrue(true);
    }
}
