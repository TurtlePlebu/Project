package org.example;

import java.util.Arrays;
import java.util.InputMismatchException;
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
        boolean failed;

        String address = "";

        do {
            failed = false;
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
        selectUserMenu();
    }

    /**
     * asks the Client user to log in with their email and password
     */
    private static boolean loginMenu() throws RuntimeException {
        boolean failed;
        String email = "";
        String password = "";
        String inputPassword = "";
        Client foundClient = null;
        Staff foundStaff = null;

        do {
            failed = false;
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
            if (staff instanceof Courier courier) {
                courierMenu(courier);
            }
            else {
                staffMenu(staff);
            }
        }
        if (user instanceof Client client) {
            clientMenu(client);
        }
    }

    private static void courierMenu(Courier courier) {
        int choice = 0;

        do {
            System.out.println(
                    "[1] View delivery inbox\n" +
                    "[2] Send mail\n" +
                    "[3] View client list\n" +
                    "[4] View Staff list\n" +
                    "[5] Remove delivery from inbox\n" +
                    "[6] View ongoing parcels\n" +
                    "[7] Deliver parcels\n" +
                    "[8] Exit\n"
            );
            try {

                choice = input.nextInt();

                if (choice > 7 || choice < 1) {
                    throw new InvalidNumberOptionException();
                }

                switch (choice) {
                    case 1 -> viewDeliveryInbox(courier);
                    case 2 -> createMail(courier);
                    case 3 -> viewClientList(courier);
                    case 4 -> viewStaffList(courier);
                    case 5 -> deleteDelivery(courier);
                    case 6 -> viewOngoingParcels(courier);
                    case 7 -> deliverParcel(courier);
                    case 8 -> PostOffice.exportData();
                }

            } catch (InvalidNumberOptionException inoe) {
                System.out.println("Please select the following options.\n");

            } catch (InputMismatchException ime) {
                System.out.println("Please remove any symbols and enter an Integer.\n");

            }

        } while (choice != 11);
    }

    /**
     * inner functions that allows the Courier to display all parcel in his inventory
     * @param courier the current Courier
     * @throws RuntimeException general unchecked exception
     */
    private static void viewOngoingParcels(Courier courier) throws RuntimeException {
        int choice = 0;

        do {
            try {
                System.out.println(
                        "[1] By Name alphabetically\n" +
                        "[2] By Name reverse alphabetically\n" +
                        "[3] By Id reverse\n" +
                        "[4] By Id\n" +
                        "[5] Exit\n"
                );
                choice = input.nextInt();

                if (choice > 5 || choice < 1) {
                    throw new InvalidNumberOptionException();
                }

                switch (choice) {
                    case 1 -> courier.viewOngoingParcels("id ascendingly");
                    case 2 -> courier.viewOngoingParcels("id descendingly");
                    case 3 -> courier.viewOngoingParcels("time descendingly ");
                    case 4 -> courier.viewOngoingParcels("");
                    case 5 -> PostOffice.exportData();
                }
            } catch (InvalidNumberOptionException inoe) {
                System.out.println("Please select the following options.\n");
            } catch (InputMismatchException ime) {
                System.out.println("Please remove any symbols and enter an Integer.\n");
            }
        }while (choice != 5);
    }

    /**
     * inner function that allows the Courier to deliver a parcel to the receiver
     * @param courier the current Courier
     * @throws RuntimeException general unchecked exception
     */
    private static void deliverParcel(Courier courier) throws RuntimeException {
        int id = 0;
        boolean failed;

        do {
            failed = false;
            try {
                System.out.println("Enter parcel Id : ");
                String idInput = input.next();

                if (exitCheck(idInput)) {
                    return;
                }

                id = Integer.parseInt(idInput);

                if (courier.searchParcel(id) == null) {
                    throw new DeliveryNotFoundException();
                }

            }  catch (InputMismatchException | NumberFormatException ime) {
                System.out.println("Remove any symbol and enter an Integer.\n");
                failed = true;
            } catch (DeliveryNotFoundException dnfe) {
                System.out.println("Unable to find given delivery.\n");
                failed = true;
            }

        } while (failed);

        courier.deliver(courier.searchParcel(id));
    }

    /**
     * displays all possible actions the Staff can perform
     * @param staff the current Staff
     */
    private static void staffMenu(Staff staff) {
        int choice = 0;

        do {
            System.out.println(
                            "[1] View delivery inbox\n" +
                            "[2] Send mail\n" +
                            "[3] Send parcel\n" +
                            "[4] Process ongoing parcel\n" +
                            "[5] View client list\n" +
                            "[6] View Staff list\n" +
                            "[7] Review next ticket\n" +
                            "[8] Remove delivery from inbox\n" +
                            "[9] Remove delivery from system\n" +
                            "[10] View all the deliveries\n" +
                            "[11] Exit\n"
            );
            try {

                choice = input.nextInt();

                if (choice > 11 || choice < 1) {
                    throw new InvalidNumberOptionException();
                }

                switch (choice) {
                    case 1 -> viewDeliveryInbox(staff);
                    case 2 -> createMail(staff);
                    case 3 -> createParcel(staff);
                    case 4 -> processParcel(staff);
                    case 5 -> viewClientList(staff);
                    case 6 -> viewStaffList(staff);
                    case 7 -> reviewTicket(staff);
                    case 8 -> deleteDelivery(staff);
                    case 9 -> deleteDeliverySystem(staff);
                    case 10 -> viewAllDelivery(staff);
                    case 11 -> PostOffice.exportData();
                }

            } catch (InvalidNumberOptionException inoe) {
                System.out.println("Please select the following options.\n");

            } catch (InputMismatchException ime) {
                System.out.println("Please remove any symbols and enter an Integer.\n");

            }

        } while (choice != 11);
    }

    /**
     * inner function that allows the Staff to assign a parcel to a given Courier
     * @param staff the current Staff
     * @throws RuntimeException general unchecked exception
     */
    private static void processParcel(Staff staff) throws RuntimeException {
        boolean failed;
        int id = 0;

        do {
            failed = false;
            try {
                System.out.println("Enter a courier's id : ");
                String idInput = input.next();

                if (exitCheck(idInput)) {
                    return;
                }

                id = Integer.parseInt(idInput);


                if (PostOffice.searchStaff(id) == null) {
                    throw new UserNotFoundException();
                }

            } catch (UserNotFoundException unfe) {
                System.out.println("Id does not exist.\n");
                failed = true;
            }
            catch (NumberFormatException nfe) {
                System.out.println("Remove any symbols and enter an Integer\n");
                failed = true;
            }
        } while (failed);

        staff.assignProcessedParcel(staff.searchCourier(id, staff.searchCouriers()), Staff.getProcessedParcels().poll());
    }

    /**
     * inner function that allows the Staff to view the list of Client
     * @param staff the current Staff
     * @throws RuntimeException general unchecked exception
     */
    private static void viewClientList(Staff staff) throws RuntimeException {
        int choice = 0;

        do {
            try {
                System.out.println(
                        "[1] By Name alphabetically\n" +
                        "[2] By Name reverse alphabetically\n" +
                        "[3] By Id reverse\n" +
                        "[4] By Id\n" +
                        "[5] Exit\n"
                );
                choice = input.nextInt();

                if (choice > 5 || choice < 1) {
                    throw new InvalidNumberOptionException();
                }

                switch (choice) {
                    case 1 -> staff.viewClient("name ascendingly");
                    case 2 -> staff.viewClient("name descendingly");
                    case 3 -> staff.viewClient("id descendingly ");
                    case 4 -> staff.viewClient("");
                    case 5 -> PostOffice.exportData();
                }
            } catch (InvalidNumberOptionException inoe) {
                System.out.println("Please select the following options.\n");
            } catch (InputMismatchException ime) {
                System.out.println("Please remove any symbols and enter an Integer.\n");
            }

        } while (choice != 5) ;
    }

    /**
     * inner function that allows the Staff to view the list of Staff
     * @param staff the current Staff
     * @throws RuntimeException general unchecked exception
     * */
    private static void viewStaffList(Staff staff) throws RuntimeException {
        int choice = 0;

        do {
            try {
                System.out.println(
                        "[1] By Name alphabetically\n" +
                        "[2] By Name reverse alphabetically\n" +
                        "[3] By Id reverse\n" +
                        "[4] By Id\n" +
                        "[5] Exit\n"
                );
                choice = input.nextInt();

                if (choice > 5 || choice < 1) {
                    throw new InvalidNumberOptionException();
                }

                switch (choice) {
                    case 1 -> staff.viewStaff("name ascendingly");
                    case 2 -> staff.viewStaff("name descendingly");
                    case 3 -> staff.viewStaff("id descendingly ");
                    case 4 -> staff.viewStaff("");
                    case 5 -> PostOffice.exportData();
                }
            } catch (InvalidNumberOptionException inoe) {
                System.out.println("Please select the following options.\n");
            } catch (InputMismatchException ime) {
                System.out.println("Please remove any symbols and enter an Integer.\n");
            }

        } while (choice != 5) ;
    }

    /**
     * inner function that allows the Staff to reply to the next OPEN Ticket in queue
     * @param staff the current Staff
     */
    private static void reviewTicket(Staff staff) {
        staff.getOngoingTickets().offer(PostOffice.openedTickets.poll());

        Ticket ticket = staff.getOngoingTickets().poll();

        System.out.printf(
                            "%d\n" +
                            "%s\n" +
                            "Title : %s\n" +
                            "Detail : %s\n" +
                            "Type : %s\n" +
                            "Time : %s\n"
                            ,ticket.getTicketId()
                            ,ticket.getClient().getName()
                            ,ticket.getDetail()
                            ,ticket.getType().toString()
                            ,ticket.getCreationTime().toString()
        );

        System.out.println("Enter -10 to leave.");
        System.out.println("Reply : ");
        String reply = input.nextLine();

        if (reply.contains("-10")) {
            ticket.setTicketStatus(Ticket.TicketStatus.PROCESSING);
            staff.getOngoingTickets().offer(ticket);
            return;
        }

        staff.replyTicket(ticket, reply);
    }

    /**
     * arbitrary delete a Delivery from the Post-Office's system
     * @param staff the current Staff
     * @throws RuntimeException general unchecked exception
     */
    private static void deleteDeliverySystem(Staff staff) throws RuntimeException {
        int choice = 0;
        boolean failed;

        do {
            failed = false;
            try {
                System.out.println("Enter delivery ID : ");
                String choiceInput = input.next();

                if (exitCheck(choiceInput)) {
                    return;
                }

                choice = Integer.parseInt(choiceInput);

                if (staff.searchDelivery(choice) == null) {
                    throw new DeliveryNotFoundException();
                }

            } catch (InputMismatchException | NumberFormatException ime) {
                System.out.println("Remove any symbol and enter an Integer.\n");
                failed = true;
            } catch (DeliveryNotFoundException dnfe) {
                System.out.println("Unable to find given delivery.\n");
                failed = true;
            }

        } while (failed);

        staff.removePostOfficeDelivery(choice);
    }

    /**
     * inner function that allows the Staff to display all the deliveries in the Post-Office system
     * @param staff the current Staff
     */
    private static void viewAllDelivery(Staff staff) {
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
                    case 1 -> staff.viewAllDelivery("");
                    case 2 -> staff.viewAllDelivery("reverse");
                    case 3 -> PostOffice.exportData();
                }

            } catch (InvalidNumberOptionException inoe) {
                System.out.println("Please select the following options.\n");
            } catch (InputMismatchException ime) {
                System.out.println("Please remove any symbols and enter an Integer.\n");
            }

        } while (choice != 3) ;
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
                    case 3 -> PostOffice.exportData();
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
        boolean failed;

        System.out.println("Enter a title :");
        String title = input.nextLine();

        System.out.println("Reply :");
        String description = input.nextLine();

        String email = "";

        do {
            failed = false;
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
        boolean failed;

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
            failed = false;
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
            } catch (NumberFormatException nfe) {
                System.out.println("Remove any symbols and enter an Integer\n");
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
        int id = -1;
        boolean failed = false;

        do {
            failed = false;
            try {
                u.viewDelivery("");

                System.out.println("Enter delivery ID : ");
                String idInput = input.next();

                if (exitCheck(idInput)) {
                    return;
                }

                id = Integer.parseInt(idInput);

                if (!u.checkDelivery(id)) {
                    throw new DeliveryNotFoundException();
                }

            } catch (InputMismatchException | NumberFormatException ime) {
                System.out.println("Remove any symbol and enter an Integer.\n");
                failed = true;
            } catch (DeliveryNotFoundException dnfe) {
                System.out.println("Unable to find given delivery.\n");
                failed = true;
            }

        } while (failed);

        u.removeDelivery(u.searchDelivery(id));
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
        return str.equalsIgnoreCase("exit") || str.toLowerCase().contains("exit");
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
