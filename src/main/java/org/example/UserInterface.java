package org.example;

import java.util.Arrays;
import java.util.InputMismatchException;
import java.util.Map;
import java.util.Scanner;
import java.util.stream.Collectors;

public class UserInterface {
    private static Scanner input = new Scanner(System.in);
    private static User user;

    /**
     * displays the starting menu of the system
     */
    public static void menu() {
        int choice = 0;

        do {
            System.out.printf("Welcome to %s :\n\n"
                    ,PostOffice.name);

            System.out.printf(
                    "[1] Register as a %s \n" +
                    "[2] Register as a %s \n" +
                    "[3] Log in\n" +
                    "[4] Exit \n\n"
                    ,"client"
                    ,"staff"
                    );
            try {
                choice = input.nextInt();
                if (choice > 4 || choice < 1) {
                    throw new InvalidNumberOptionException();
                }

                switch (choice) {
                    case 1 -> clientRegisterMenu(); 
                    case 2 -> staffRegisterMenu(); 
                    case 3 -> login(); 
                    case 4 -> PostOffice.exportData();
                }

            } catch (InvalidNumberOptionException inoe) {
                System.out.println("Please select the following options.\n");

            } catch (InputMismatchException ime) {
                System.out.println("Please remove any symbols and enter an Integer.\n");

            } catch (RuntimeException re) {
                System.out.println(re.getMessage());
                for (StackTraceElement stackTraceElement : re.getStackTrace()) {
                    System.out.println(stackTraceElement);
                }

            }

        } while (choice != 4);
    }

    /**
     * inner function that registers basics shared information to create or register a User
     * asks for input from the user to register shared information
     * @return an String Array with name, email, and password to create a User
     * @throws RuntimeException general unchecked exception
     */
    private static String[] registerMenu() throws RuntimeException {
        boolean failed;

        String name = "";
        String email = "";
        String password = "";

        do {
            failed = false;

            try {
                System.out.println("Enter your name : ");
                 name = Arrays.stream(input.next().split(" "))
                         .map(str -> (str.isBlank()) ? "" : str)
                         .collect(Collectors.joining());

                 if (exitCheck(name)) {
                    return new String[]{};
                 }
                if (!lettersOnly(name)) {
                    throw new InvalidNameException();
                }

                System.out.println("Enter your email : ");
                email = Arrays.stream(input.next().split(" "))
                        .map(str -> (str.isBlank()) ? "" : str)
                        .collect(Collectors.joining());

                if (exitCheck(email)) {
                    return new String[]{};
                }
                if (!validEmail(email)) {
                    throw new InvalidEmailException();
                }

                System.out.println("Enter a password : ");
                password = Arrays.stream(input.next().split(" "))
                        .map(str -> (str.isBlank()) ? "" : str)
                        .collect(Collectors.joining());

                if (exitCheck(password)) {
                    return new String[]{};
                }

            } catch (InvalidNameException ine) {
                System.out.println("Remove any symbol or whitespace in your name.\n");
                failed = true;

            } catch (InvalidEmailException iee) {
                System.out.println("Email is invalid. Remove any symbol or whitespace.\n");
                failed = true;

            }
        } while (failed);

        return new String[] {name, email, password};
    }

    /**
     * registers the client's shared information
     * asks the user for the address
     * @throws RuntimeException general unchecked exception
     */
    private static void clientRegisterMenu() throws RuntimeException {
        String[] userInfo = registerMenu();
        boolean failed = false;

        String address = "";

        do {
            try {

                System.out.println("Enter your address : ");
                address = input.next();

                if (exitCheck(address)) {
                    return;
                }

            } catch (InvalidNameException ine) {
                System.out.println("Remove any symbol or whitespace in your address.\n");
                failed = true;
            }

        } while (failed);

        Client newClient = new Client(userInfo[1], userInfo[2], address);
        newClient.register(userInfo[3]);
        System.out.println("Registered!\n");
    }

    /**
     * verifies if the user is logged in
     */
    private static void login() {
        loginMenu();
    }

    /**
     * asks the Client user to log in with their email and password
     */
    private static boolean loginMenu() throws RuntimeException {
        boolean failed = false;
        String email = "";
        String password = "";
        String inputPassword = "";
        Client foundClient = null;
        Staff foundStaff = null;

        do {
            try {
                System.out.println("Enter your email : ");
                email = input.next();

                if (exitCheck(email)) {
                    return false;
                }
                if (!validEmail(email)) {
                    throw new InvalidEmailException();
                }

                foundClient = PostOffice.searchClientByEmail(email);
                foundStaff = PostOffice.searchStaffByEmail(email);

                if (foundClient != null) {
                    password = PostOffice.clientSecurityPass.get(foundClient);
                }
                if (foundStaff != null) {
                    password = PostOffice.staffSecurityPass.get(foundStaff);
                }
                if (foundStaff == null && foundClient == null) {
                    throw new UserNotFoundException();
                }

                System.out.println("Enter your password : ");
                inputPassword = input.next();

                if (exitCheck(inputPassword)) {
                    return false;
                }

            } catch (InvalidEmailException iee) {
                System.out.println("Email is invalid. Remove any symbol or whitespace.\n");
                failed = true;
            } catch (UserNotFoundException unfe) {
                System.out.println("No such email or user exist.\n");
                failed = true;
            }

        } while (failed);

        user = (foundClient != null) ? foundClient : foundStaff;
        return password.equals(inputPassword);
    }

    /**
     * distinguishes the user between a client or a staff
     */
    private static void selectUserMenu() {
        if (user instanceof Staff staff) {

        }
        if (user instanceof Client client) {
            clientMenu(client);
        }
    }

    /**
     * displays all the possible actions of a client
     * @param client the current client
     * @throws RuntimeException general unchecked exception
     */
    private static void clientMenu(Client client) throws RuntimeException {
        int choice = 0;

        do {
            System.out.println(
                    "[1] View delivery inbox\n" +
                    "[2] Send mail\n" +
                    "[3] Send parcel\n" +
                    "[4] Remove delivery from inbox\n" +
                    "[5] Create bug report\n" +
                    "[6] Create support request\n" +
                    "[7] Exit\n"
            );
            try {

                choice = input.nextInt();

                if (choice > 7 || choice < 1) {
                    throw new InvalidNumberOptionException();
                }

                switch (choice) {
                    case 1 -> viewDeliveryInbox(client); 
                    case 2 -> createMail(client);
                    case 3 -> createParcel(client);
                    case 4 -> deleteDelivery(client);
                    case 5 -> createBugReport(client);
                    case 6 -> createSupportRequest(client);
                    case 7 -> PostOffice.exportData();
                }

            } catch (InvalidNumberOptionException inoe) {
                System.out.println("Please select the following options.\n");

            } catch (InputMismatchException ime) {
                System.out.println("Please remove any symbols and enter an Integer.\n");

            }

        } while (choice != 7);
    }

    /**
     * inner functions that allows the user to display their deliveries
     * @param u the current user
     */
    private static void viewDeliveryInbox(User u) throws RuntimeException {
        int choice = 0;

        do {
            try {
                System.out.println(
                                "[1] Latest\n" +
                                "[2] Reverse\n" +
                                "[3] Exit\n"
                );
                choice = input.nextInt();

                if (choice > 3 || choice < 1) {
                    throw new InvalidNumberOptionException();
                }

                switch (choice) {
                    case 1 -> u.viewDelivery("");
                    case 2 -> u.viewDelivery("reverse");
                }

            } catch (InvalidNumberOptionException inoe) {
                System.out.println("Please select the following options.\n");
            } catch (InputMismatchException ime) {
                System.out.println("Please remove any symbols and enter an Integer.\n");
            }

        } while (choice != 3) ;
    }

    /**
     * inner function that allows the user to create and send a mail to another user
     * @param u the current user
     * @throws RuntimeException general unchecked exception
     */
    private static void createMail(User u) throws RuntimeException {
        boolean failed = false;

        System.out.println("Enter a title :");
        String title = input.nextLine();

        System.out.println("Reply :");
        String description = input.nextLine();

        String email = "";

        do {
            try {
                System.out.println("Receiver's email :");
                 email = input.nextLine();

                if (exitCheck(email)) {
                    return;
                }
                if (!validEmail(email)) {
                    throw new InvalidEmailException();
                }
                if (PostOffice.searchUser(email) == null) {
                    throw new EmailNotFoundException();
                }


            } catch (InvalidEmailException iee) {
                System.out.println("Email is invalid. Remove any symbol or whitespace.\n");
                failed = true;
            } catch (EmailNotFoundException enfe ) {
                System.out.println("Cannot find email.\n");
                failed = true;
            }

        } while (failed);

        u.sendMail(description, title, email);
    }

    /**
     * inner function that allows the user to create and send a Parcel to another user
     * @param u the current user
     * @throws RuntimeException general unchecked exception
     */
    private static void createParcel(User u) throws RuntimeException {
        boolean failed = false;

        System.out.println("Enter the item name :");
        String itemName = input.nextLine();

        if (exitCheck(itemName)) {
            return;
        }

        System.out.println("Detail :");
        String detail = input.nextLine();

        double weight = 0;
        int quantity = 0;
        String email = "";

        do {
            try {
                System.out.println("Weight :");
                String weightInput = input.next();

                if (exitCheck(weightInput)) {
                    return;
                }
                weight = Double.parseDouble(weightInput);

                if (weight < 0) {
                    throw new InvalidInputException();
                }

                System.out.println("Quantity :");
                String quantityInput = input.next();

                if (exitCheck(quantityInput)) {
                    return;
                }
                quantity = Integer.parseInt(quantityInput);

                if (quantity < 0) {
                    throw new InvalidInputException();
                }

                System.out.println("Receiver's email :");
                email = input.nextLine();

                if (exitCheck(email)) {
                    return;
                }
                if (!validEmail(email)) {
                    throw new InvalidEmailException();
                }
                if (PostOffice.searchUser(email) == null) {
                    throw new EmailNotFoundException();
                }

            } catch (InvalidInputException iie) {
                System.out.println("Invalid number.");
                failed = true;
            } catch (InvalidEmailException iee) {
                System.out.println("Email is invalid. Remove any symbol or whitespace.\n");
                failed = true;
            } catch (EmailNotFoundException enfe ) {
                System.out.println("Cannot find email.\n");
                failed = true;
            }
        } while (failed);

        u.sendParcel(itemName, detail, weight, quantity, email);
    }

    /**
     * inner function that allows the user to remove a Delivery from a user's inbox
     * @param u the current user
     * @throws RuntimeException general unchecked exception
     */
    private static void deleteDelivery(User u) throws RuntimeException {
        int choice = -1;

        do {
            u.viewDelivery("");

            System.out.println("Choose a delivery to remove (Use a number of the display order starting from 0) : ");
            String choiceInput = input.next();

            if (exitCheck(choiceInput)) {
                return;
            }

            choice = Integer.parseInt(choiceInput);
        } while (choice < 0);

        u.removeDelivery(u.getDeliveries().get(choice));
    }

    /**
     * inner function that allows the client to create and send a bug report ticket to the staffs
     * @param client the current client
     * @throws RuntimeException general unchecked exception;
     */
    private static void createBugReport(Client client) throws RuntimeException {
        System.out.println("Enter -10 to leave");
        System.out.println("Enter a title : ");
        String title = input.nextLine();

        System.out.println("Enter details : ");
        String detail = input.nextLine();

        if (title.contains("-10") || detail.contains("-10")) {
            return;
        }

        client.sendBugReport(title, detail);
    }

    /**
     * inner function that allows the client to create and send a support request ticket to the staffs
     * @param client the current client
     * @throws RuntimeException general unchecked exception;
     */
    private static void createSupportRequest(Client client) throws RuntimeException {
        System.out.println("Enter -10 to leave");
        System.out.println("Enter a title : ");
        String title = input.nextLine();

        System.out.println("Enter details : ");
        String detail = input.nextLine();

        if (title.contains("-10") || detail.contains("-10")) {
            return;
        }

        client.sendSupportRequest(title, detail);
    }
    /**
     * checks if the String is spelled "exit"
     * @param str the given String
     * @return a true or false value indicating the want to go back
     */
    private static boolean exitCheck(String str) {
        if (str == null) {
            return true;
        }
        return str.equalsIgnoreCase("exit");
    }

    /**
     * registers the staff's shared information
     * @throws RuntimeException general uncheck exception
     */
    private static void staffRegisterMenu() throws RuntimeException {
        String[] userInfo = registerMenu();

        Staff newStaff = new Staff(userInfo[1], userInfo[2]);
        newStaff.register(userInfo[3]);
        System.out.println("Registered!\n");
    }

    /**
     * verifies if the String only contains letters,
     * excluding whitespaces and hyphens
     * @param str the given String to verify
     * @return a true or false value indicating if the String only contains letters
     */
    private static boolean lettersOnly(String str) {
        boolean lettersOnly = true;

        for (Character c : str.toCharArray()) {
            if (lettersOnly) {
                lettersOnly = Character.isLetter(c) || c == '-' || Character.isSpaceChar(c);
            }
        }

        return lettersOnly;
    }

    /**
     * verifies if the email is valid
     * @param str the email to verify
     * @return a true of false value indicating if the String only contains letters
     */
    private static boolean validEmail(String str) {
        return str.contains("@") && str.contains(".") && lettersOnly(str.substring(0,str.indexOf("@")));
    }
}
