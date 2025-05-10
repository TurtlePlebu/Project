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
                    "[3] Exit \n\n"
                    ,"client"
                    ,"staff"
                    );
            try {
                choice = input.nextInt();
                if (choice > 3) {
                    throw new InvalidNumberOptionException();
                }

                switch (choice) {
                    case 1 -> clientRegisterMenu();
                    case 2 -> staffRegisterMenu();
                    case 3 -> PostOffice.exportData();
                }

            } catch (InvalidNumberOptionException inoe) {
                System.out.println("Please select the following options.\n");

            } catch (InputMismatchException ime) {
                System.out.println("Please remove any symbols and enter a number.\n");

            } catch (RuntimeException re) {
                System.out.println(re.getMessage());
                for (StackTraceElement stackTraceElement : re.getStackTrace()) {
                    System.out.println(stackTraceElement);
                }

            }

        } while (choice != 3);
    }

    /**
     * inner function that registers basics shared information to create or register a User
     * asks for input from the user to register shared information
     * @return an String Array with name, email, and password to create a User
     * @throws RuntimeException general unchecked exception
     */
    private static String[] registerMenu() throws RuntimeException{
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

                if (!lettersOnly(name)) {
                    throw new InvalidNameException();
                }

                System.out.println("Enter your email : ");
                email = Arrays.stream(input.next().split(" "))
                        .map(str -> (str.isBlank()) ? "" : str)
                        .collect(Collectors.joining());

                if (!validEmail(email)) {
                    throw new InvalidEmailException();
                }

                System.out.println("Enter a password : ");
                password = Arrays.stream(input.next().split(" "))
                        .map(str -> (str.isBlank()) ? "" : str)
                        .collect(Collectors.joining());

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
    private static void clientRegisterMenu() throws RuntimeException{
        String[] userInfo = registerMenu();
        boolean failed = false;

        String address = "";

        do {
            try {

                System.out.println("Enter your address : ");
                address = input.next();

            } catch (InvalidNameException ine) {
                System.out.println("Remove any symbol or whitespace in your address.\n");
                failed = true;
            }

        } while (failed);

        Client newClient = new Client(userInfo[1], userInfo[2], address);
        newClient.register(userInfo[3]);
    }

    /**
     * registers the staff's shared information
     * @throws RuntimeException general uncheck exception
     */
    private static void staffRegisterMenu() throws RuntimeException {
        String[] userInfo = registerMenu();

        Staff newStaff = new Staff(userInfo[1], userInfo[2]);
        newStaff.register(userInfo[3]);
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
