import org.example.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.List;

public class PostOfficeTest {

    PostOffice postOffice = new PostOffice();

    @Test
    public void searchClientTest_WithId1() {

        Client results = PostOffice.searchClient(0);

        Assertions.assertTrue(
                (results.getClientId() == 0) &&
                (results.getName().equalsIgnoreCase("Nathan")) &&
                (results.getEmail().equalsIgnoreCase("NathanChg@hotmail.com")) &&
                (results.getAddress().equalsIgnoreCase("rue de boheme"))
        );
    }

    @Test
    public void searchClientTest_WithId2() {

        Client results = PostOffice.searchClient(1);

        Assertions.assertTrue(
                (results.getClientId() == 1) &&
                        (results.getName().equalsIgnoreCase("Jacques")) &&
                        (results.getEmail().equalsIgnoreCase("Jpc@hotmail.com")) &&
                        (results.getAddress().equalsIgnoreCase("100e avenue"))
        );
    }

    @Test
    public void searchClientTest_WithAddress1() {

        Client results = PostOffice.searchClient("rue de boheme");

        Assertions.assertTrue(
                (results.getClientId() == 0) &&
                        (results.getName().equalsIgnoreCase("Nathan")) &&
                        (results.getEmail().equalsIgnoreCase("NathanChg@hotmail.com")) &&
                        (results.getAddress().equalsIgnoreCase("rue de boheme"))
        );
    }

    @Test
    public void searchClientTest_WithAddress2() {

        Client results = PostOffice.searchClient("100e avenue");

        Assertions.assertTrue(
                (results.getClientId() == 1) &&
                        (results.getName().equalsIgnoreCase("Jacques")) &&
                        (results.getEmail().equalsIgnoreCase("Jpc@hotmail.com")) &&
                        (results.getAddress().equalsIgnoreCase("100e avenue"))
        );
    }

    @Test
    public void searchStaffTest_WithId1() {

        Staff results = PostOffice.searchStaff(0);

        Assertions.assertTrue(
                (results.getStaffId() == 0) &&
                        (results.getName().equalsIgnoreCase("Nathan")) &&
                        (results.getEmail().equalsIgnoreCase("Nathan@hotmail.com"))
        );
    }

    @Test
    public void searchStaffTest_WithId2() {

        Staff results = PostOffice.searchStaff(1);

        Assertions.assertTrue(
                (results.getStaffId() == 1) &&
                        (results.getName().equalsIgnoreCase("Luca")) &&
                        (results.getEmail().equalsIgnoreCase("LClucky@hotmail.com"))
        );
    }

    @Test
    public void searchUserListTest1() {

        List<User> results = PostOffice.searchUserList("Nathan");

        Assertions.assertTrue(
                (results.size() == 2) &&
                        (results.get(0).getName().equalsIgnoreCase("Nathan")) &&
                        (results.get(0).getEmail().equalsIgnoreCase("Nathan@hotmail.com")) &&
                        (results.get(1).getName().equalsIgnoreCase("Nathan")) &&
                        (results.get(1).getEmail().equalsIgnoreCase("NathanChg@hotmail.com"))
        );
    }

    @Test
    public void searchUserListTest2() {

        List<User> results = PostOffice.searchUserList("Kim");

        Assertions.assertTrue(
                (results.size() == 1) &&
                        (results.get(0).getName().equalsIgnoreCase("Kim")) &&
                        (results.get(0).getEmail().equalsIgnoreCase("Ethan@gmail.com"))
        );
    }

    @Test
    public void searchClientByEmailTest1() {

        Client results = PostOffice.searchClientByEmail("NathanChg@hotmail.com");

        Assertions.assertTrue(
                (results.getClientId() == 0) &&
                        (results.getName().equalsIgnoreCase("Nathan")) &&
                        (results.getEmail().equalsIgnoreCase("NathanChg@hotmail.com")) &&
                        (results.getAddress().equalsIgnoreCase("rue de boheme"))
        );
    }

    @Test
    public void searchClientByEmailTest2() {

        Client results = PostOffice.searchClientByEmail("Jpc@hotmail.com");

        Assertions.assertTrue(
                (results.getClientId() == 1) &&
                        (results.getName().equalsIgnoreCase("Jacques")) &&
                        (results.getEmail().equalsIgnoreCase("Jpc@hotmail.com")) &&
                        (results.getAddress().equalsIgnoreCase("100e avenue"))
        );
    }

    @Test
    public void searchStaffByEmailTest1() {

        Staff results = PostOffice.searchStaffByEmail("Nathan@hotmail.com");

        Assertions.assertTrue(
                (results.getStaffId() == 0) &&
                        (results.getName().equalsIgnoreCase("Nathan")) &&
                        (results.getEmail().equalsIgnoreCase("Nathan@hotmail.com"))
        );
    }

    @Test
    public void searchStaffByEmailTest2() {

        Staff results = PostOffice.searchStaffByEmail("Quang@hotmail.com");

        Assertions.assertTrue(
                (results.getStaffId() == 2) &&
                        (results.getName().equalsIgnoreCase("Vinh")) &&
                        (results.getEmail().equalsIgnoreCase("Quang@hotmail.com"))
        );
    }

    @Test
    public void searchUserTest1() {

        User results = PostOffice.searchUser("Nathan@hotmail.com");

        Assertions.assertTrue(
                        (results.getName().equalsIgnoreCase("Nathan")) &&
                        (results.getEmail().equalsIgnoreCase("Nathan@hotmail.com"))
        );
    }

    @Test
    public void searchUserTest2() {

        User results = PostOffice.searchUser("NathanChg@hotmail.com");

        Assertions.assertTrue(
                (results.getName().equalsIgnoreCase("Nathan")) &&
                        (results.getEmail().equalsIgnoreCase("NathanChg@hotmail.com"))
        );
    }
}
