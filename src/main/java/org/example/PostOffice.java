package org.example;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.*;

public class PostOffice implements FilePaths{
    public static String name = "Canada Post";
    public static String address = "2000 Blvd. Marcel-Laurin Saint-Laurent";
    public static String email = "CanadaPost@hotmail.com";
    public static List<Delivery> deliveries = new ArrayList<>();
    public static List<Advertisement> advertisements = new ArrayList<>();
    public static Queue<Ticket> openedTickets = new PriorityQueue<>(new Ticket.TicketComparator(""));
    public static List<Ticket> ongoingTickets = new ArrayList<>();
    public static List<Ticket> completedTickets = new ArrayList<>();
    public static List<Client> clients = new ArrayList<>();
    public static List<Staff> staffs = new ArrayList<>();
    public static Manager manager = new Manager("Nathan", "CanadaPostManagement@CanadaPost.com");

    public static Map<Staff, String> staffSecurityPass = new TreeMap<>(new Staff.StaffComparator(""));
    public static Map<Client, String> clientSecurityPass = new TreeMap<>(new Client.ClientComparator(""));

    public PostOffice() {
        // load the data
        importData();
    }

    /**
     * exports all current data inside the post-center into their appropriate files in Resources
     */
    public static boolean exportData() {
        try {
            exportPostOfficeInfo();
            exportDeliveries();
            exportClients();
            exportStaffs();
            exportClientSecurity();
            exportStaffSecurity();
            exportOpenedTickets();
            exportOngoingTickets();
            exportCompletedTickets();

            distributeProcessedParcels();
            distributeParcelsToCourier();

        } catch (IOException e) {
            System.out.println("Could not write all the data");
            System.out.println(e.getMessage());
            for (StackTraceElement trace : e.getStackTrace()) {
                System.out.println(trace.toString());
            }
            return false;
        } catch (RuntimeException e) {
            System.out.println(e.getMessage());
            for (StackTraceElement trace : e.getStackTrace()) {
                System.out.println(trace.toString());
            }
            return false;
        }
        return true;
    }

    /**
     * inner function for exportData() function that exports all the post-center's shared information
     * @throws IOException Output data exceptions during writing data
     * @throws RuntimeException General unchecked exception
     */
    private static void exportPostOfficeInfo() throws IOException, RuntimeException {
        File file = new File(POSTCENTER_FILE_PATH);

        try (FileWriter fw = new FileWriter(file)) {
            fw.write(name + ",");
            fw.write(address + ",");
            fw.write(email + ",");
            fw.write(manager.getName() + ",");
            fw.write(manager.getEmail() + "\n");
        }
    }

    /**
     * inner function for exportData() function that exports all deliveries of the post-center
     * separate both mails and parcel in different files
     * @throws IOException Output data exceptions during writing data
     * @throws RuntimeException General unchecked exception
     */
    private static void exportDeliveries() throws IOException, RuntimeException {
        Set<Advertisement> ads = new HashSet<>();
        List<Parcel> parcels = new ArrayList<>();
        List<Mail> mails = new ArrayList<>();

        for (Delivery delivery : deliveries) {
            if (delivery instanceof Parcel parcel1) {
                parcels.add(parcel1);
            }
            if (delivery instanceof Mail mail1) {
                if (mail1 instanceof Advertisement ad) {
                    ads.add(ad);
                } else {
                    mails.add(mail1);
                }
            }
        }

        for (Advertisement ad : advertisements) {
            ads.add(ad);
        }

        exportMails(mails);
        exportParcels(parcels);
        exportCompanyAdvertisements(ads);
    }

    /**
     * inner function for exportDeliveries() function that exports all the parcels of the post-center
     * @param parcels given List of Parcel to export into the parcel resource file
     * @throws IOException Output data exceptions during writing data
     * @throws RuntimeException General unchecked exception
     */
    private static void exportParcels(List<Parcel> parcels) throws IOException, RuntimeException {
        File parcelFile = new File(PARCELS_FILE_PATH);

        try (FileWriter fw = new FileWriter(parcelFile)) {
            for (Parcel parcel : parcels) {
                fw.write(parcel.getParcelId() + ",");
                fw.write(parcel.getAddress() + ",");
                fw.write(parcel.getDescription() + ",");
                fw.write(parcel.getQuantity() + ",");
                fw.write(parcel.getItem().getName() + ",");
                fw.write(parcel.getItem().getWeight() + ",");
                fw.write(parcel.getItem().getPurchasedTime().toString() + ",");
                fw.write(parcel.getArrivalTime().toString() + ",");
                fw.write(parcel.getStatus().toString() + ",");
                if (parcel.getCourier() == null) {
                    fw.write("-1,");
                } else {
                    fw.write(parcel.getCourier().getStaffId() + ",");
                }
                fw.write(parcel.getEmail() + "\n");
            }
        }
    }

    /**
     * inner function for exportDeliveries() function that exports all the mails of the post-center
     * separates Advertisements into a different file without duplicates
     * @param mails given List of Mail to export into the mail resource file
     * @throws IOException Output data exceptions during writing data
     * @throws RuntimeException General unchecked exception
     */
    private static void exportMails(List<Mail> mails) throws IOException, RuntimeException {
        File mailPath = new File(MAILS_FILE_PATH);

        try (FileWriter fw = new FileWriter(mailPath)) {
            for (Mail mail : mails) {
                fw.write(mail.getTitle() + ",");
                fw.write(mail.getAddress() + ",");
                fw.write(mail.getDescription() + ",");
                fw.write(mail.getArrivalTime().toString() + ",");
                fw.write(mail.getStatus().toString() + ",");
                fw.write(mail.getEmail() + "\n");
            }
        }
    }

    /**
     * inner function for exportMails() function that includes each unique Advertisement
     * @param ads the Set of all different Advertisements to export
     * @throws IOException Output data exceptions during writing data
     * @throws RuntimeException General unchecked exception
     */
    private static void exportCompanyAdvertisements(Set<Advertisement> ads) throws IOException, RuntimeException {
        if (ads.isEmpty()) {
            return;
        }

        File adPath = new File(COMPANYADVERTISEMENTS_FILE_PATH);

        for (Advertisement ad : advertisements) {
            ads.add(ad);
        }

        try (FileWriter fw = new FileWriter(adPath)) {
            for (Advertisement ad : ads) {
                fw.write(ad.getCompanyName() + ",");
                fw.write(ad.getTitle() + ",");
                fw.write(ad.getDescription() + ",");
                fw.write(ad.getArrivalTime().toString() + ",");
                fw.write(ad.getStatus().toString() + "\n");
            }
        }
    }

    /**
     * inner function for exportData() function that exports all Client users of the post-center
     * @throws IOException Output data exceptions during writing data
     * @throws RuntimeException General unchecked exception
     */
    private static void exportClients() throws IOException, RuntimeException {
        File clientPath = new File(CLIENT_FILE_PATH);

        try (FileWriter fw = new FileWriter(clientPath)) {
            for (Client client : clients) {
                fw.write(client.getClientId() + ",");
                fw.write(client.getName() + ",");
                fw.write(client.getAddress() + ",");
                fw.write(client.getEmail() + "\n");
            }
        }
    }

    /**
     * inner function for exportData() function that exports all Staff users of the post-center
     * @throws IOException Output data exceptions during writing data
     * @throws RuntimeException General unchecked exception
     */
    private static void exportStaffs() throws IOException, RuntimeException {
        File staffPath = new File(STAFF_FILE_PATH);

        try (FileWriter fw = new FileWriter(staffPath)) {
            for (Staff staff : staffs) {
                fw.write(staff.getStaffId() + ",");
                fw.write(staff.getName() + ",");
                fw.write(staff.getEmail() + ",");
                fw.write(staff.getRole().toString() + "\n");
            }
        }
    }

    /**
     * inner function for exportData() function exports all Client users' security info
     * @throws IOException Output data exceptions during writing data
     * @throws RuntimeException General uncheck exception
     */
    private static void exportClientSecurity() throws IOException, RuntimeException {
        File clientSecurityPath = new File(CLIENT_SECURITY_PASS_FILE_PATH);

        try (FileWriter fw = new FileWriter(clientSecurityPath)) {
            for (Map.Entry<Client, String> client : clientSecurityPass.entrySet()) {
                fw.write(client.getKey().getEmail() + ",");
                fw.write(client.getValue() + "\n");
            }
        }
    }

    /**
     * inner function for exportData() function exports all Staff users' security info
     * @throws IOException Output data exceptions during data
     * @throws RuntimeException General uncheck exception
     */
    private static void exportStaffSecurity() throws IOException, RuntimeException {
        File staffSecurityPath = new File(STAFF_SECURITY_PASS_FILE_PATH);

        try (FileWriter fw = new FileWriter(staffSecurityPath)) {
            for (Map.Entry<Staff, String> staff : staffSecurityPass.entrySet()) {
                fw.write(staff.getKey().getEmail() + ",");
                fw.write(staff.getValue() + "\n");
            }
        }
    }

    /**
     * inner function for exportData() function that exports all Tickets in the post-center
     * @throws IOException Output data exceptions during writing data
     * @throws RuntimeException General unchecked exception
     */
    private static void exportTickets(String path, Collection<Ticket> tickets) throws IOException, RuntimeException {
        File ticketPath = new File(path);

        try (FileWriter fw = new FileWriter(ticketPath)) {
            for (Ticket ticket : tickets) {
                fw.write(ticket.getTicketId() + ",");
                fw.write(ticket.getTitle() + ",");
                fw.write(ticket.getType().toString() + ",");
                fw.write(ticket.getDetail() + ",");
                fw.write(ticket.getTicketStatus().toString() + ",");
                fw.write(ticket.getClient().getClientId() + ",");
                if (ticket.getStaff() == null) {
                    fw.write(" ,");
                }
                else {
                    fw.write(ticket.getStaff().getStaffId() + ",");
                }
                fw.write(ticket.getCreationTime().toString() + "\n");
            }
        }
    }

    /**
     * calls exportTickets() function to export all OPEN Tickets in the post-center
     * @throws IOException Output data exceptions during writing data
     * @throws RuntimeException General unchecked exception
     */
    private static void exportOpenedTickets() throws IOException, RuntimeException {
        exportTickets(OPENEDTICKETS_FILE_PATH, openedTickets);
    }

    /**
     * calls exportTickets() function to export all PROCESSING Tickets in the post-center
     * @throws IOException Output data exceptions during writing data
     * @throws RuntimeException General unchecked exception
     */
    private static void exportOngoingTickets() throws IOException, RuntimeException {
        exportTickets(ONGOINGTICKETS_FILE_PATH, ongoingTickets);
    }

    /**
     * calls exportTickets() function to export all CLOSED Tickets in the post-center
     * @throws IOException Output data exceptions during writing data
     * @throws RuntimeException General unchecked exception
     */
    private static void exportCompletedTickets() throws IOException, RuntimeException {
        exportTickets(COMPLETEDTICKETs_FILE_PATH, completedTickets);
    }

    /**
     * loads all registered data from Resource file
     */
    public static boolean importData() {
        try {
            String[] info = importPostOfficeInfo();
            name = info[0];
            address = info[1];
            email = info[2];
            manager = new Manager(info[3], info[4]);
            clients = importClients();
            staffs = importStaffs();
            clientSecurityPass = importClientSecurityPass();
            staffSecurityPass =  importStaffSecurityPass();
            deliveries = importDeliveries();
            advertisements = importAdvertisements();

            distributeProcessedParcels();
            distributeParcelsToCourier();

            Ticket.setNextId(0);
            importOpenedTickets();
            importOngoingTickets();
            importCompletedTickets();
        }
        catch (RuntimeException e) {
            System.out.println(e.getMessage());
            for (StackTraceElement ste : e.getStackTrace()) {
                System.out.println(ste.toString());
            }
            return false;
        }
        return true;
    }

    /**
     * handles all FileNotFoundException from importing data functions called by importData()
     * @param fnfe the FileNotFoundException
     * @param fileName the filepath name
     */
    private static void handler(FileNotFoundException fnfe, String fileName) {
        String message = String.format("Cannot find %s", fileName);
        System.out.println(message);
        System.out.println(fnfe.getMessage());
        for (StackTraceElement ste : fnfe.getStackTrace()) {
            System.out.println(ste.toString());
        }
    }

    /**
     * inner function for importData() that imports all information data from Post-Center.csv
     * @return a String array containing the shared information of the post-office
     * @throws RuntimeException general unchecked exception
     */
    private static String[] importPostOfficeInfo()  throws RuntimeException {
        File file = new File(POSTCENTER_FILE_PATH);

        String[] info = new String[]{};

        try (Scanner input = new Scanner(file)) {
            info = input.nextLine().split(",");

        } catch (FileNotFoundException fnfe) {
            handler(fnfe, POSTCENTER_FILE_PATH);
        }

        return info;
    }

    /**
     * inner function for importData() that imports all Delivery data from Delivery .csv files
     * @return a List of Delivery containing all the deliveries in the post-office
     * @throws RuntimeException general unchecked exception
     */
    private static List<Delivery> importDeliveries() throws RuntimeException {
        List<Delivery> deliveries = new ArrayList<>();

        Delivery.setNextId(0);

        for (Delivery delivery : importParcels()) {
            deliveries.add(delivery);
        }
        for (Delivery delivery : importMails()) {
            deliveries.add(delivery);
        }

        distributeDeliveries(deliveries);

        return deliveries;
    }

    /**
     * distributes all the deliveries its respective User
     * @param deliveries the List of all the deliveries
     */
    private static void distributeDeliveries(List<Delivery> deliveries) {
        for (Delivery delivery : deliveries) {
            if (delivery instanceof Parcel parcel) {
                if (parcel.getStatus().equals(Delivery.Status.ONGOING)) {
                    continue;
                }
                searchUser(parcel.getEmail()).receiveDelivery(parcel);
            }
            if (delivery instanceof Mail mail) {
                searchUser(mail.getEmail()).receiveDelivery(mail);
            }
        }
    }

    /**
     * inner function for importDeliveries() that imports all Parcels data from Parcels.csv
     * @return a List of Delivery containing all the parcels in the post-office
     * @throws RuntimeException general unchecked exception
     */
    private static List<Delivery> importParcels() throws RuntimeException {
        File parcelFile = new File(PARCELS_FILE_PATH);
        List<Delivery> parcels = new ArrayList<>();
        Parcel.setNextId(0);

        try (Scanner input = new Scanner(parcelFile)) {
            while (input.hasNextLine()) {
                String[] parcelLine = input.nextLine().split(",");

                int id = Integer.parseInt(parcelLine[0]); //Optional
                String address = parcelLine[1];
                String description = parcelLine[2];
                int quantity = Integer.parseInt(parcelLine[3]);
                String itemName = parcelLine[4];
                double itemWeight = Double.parseDouble(parcelLine[5]);
                LocalDateTime itemTime = LocalDateTime.parse(parcelLine[6]);
                LocalDateTime arrivalTime = LocalDateTime.parse(parcelLine[7]);
                Delivery.Status status = (parcelLine[8].equalsIgnoreCase("DELIVERED")) ?
                        Delivery.Status.DELIVERED : Delivery.Status.ONGOING;
                int courierId = Integer.parseInt(parcelLine[9]);
                Courier courier = (courierId == -1) ? null : (Courier) searchStaff(courierId);
                String email = parcelLine[10];
                parcels.add(new Parcel(address, description, arrivalTime, status, new Item(itemName, itemWeight, itemTime), quantity, courier, email));
            }

        } catch (FileNotFoundException fnfe) {
            handler(fnfe, PARCELS_FILE_PATH);
        }

        return parcels;
    }

    /**
     * distributes Parcel data from Delivery files to Couriers
     */
    private static void distributeParcelsToCourier() {
        for (Delivery delivery : deliveries) {
            if (delivery instanceof Parcel p && p.getStatus().equals(Delivery.Status.ONGOING) ) {
                if (p.getCourier() != null) {
                    ((Courier) searchStaff(p.getCourier().getStaffId())).pickupParcel(p);
                }
                else {
                    Staff.getProcessedParcels().add(p);
                }
            }
        }
    }

    private static void distributeProcessedParcels() {
        if (deliveries == null || deliveries.isEmpty()) {
            return;
        }
        for (Delivery delivery : deliveries) {
            if (delivery instanceof  Parcel p && p.getCourier() == null) {
                Staff.processedParcels.offer(p);
            }
        }
    }

    /**
     * inner function for importDeliveries() that imports all Mails data from Mails.csv
     * @return a List of Delivery containing all the mails in the post-office
     * @throws RuntimeException general unchecked exception
     */
    private static List<Delivery> importMails() throws RuntimeException {
        File mailPath = new File(MAILS_FILE_PATH);
        List<Delivery> mails = new ArrayList<>();

        try (Scanner input = new Scanner(mailPath)) {
            while (input.hasNextLine()) {
                String[] mailLine = input.nextLine().split(",");

                String title = mailLine[0];
                String address = mailLine[1];
                String description = mailLine[2];
                LocalDateTime arrivalTime = LocalDateTime.parse(mailLine[3]);
                Delivery.Status status = (mailLine[4].equalsIgnoreCase("DELIVERED")) ?
                        Delivery.Status.DELIVERED : Delivery.Status.ONGOING;
                String email = mailLine[5];
                mails.add(new Mail(address, description, arrivalTime, status, title, email));
            }

        } catch (FileNotFoundException fnfe) {
            handler(fnfe, MAILS_FILE_PATH);
        }

        return mails;
    }

    private static List<Advertisement> importAdvertisements() throws RuntimeException{
        File adPath = new File(COMPANYADVERTISEMENTS_FILE_PATH);
        List<Advertisement> ads = new ArrayList<>();

        try (Scanner input = new Scanner(adPath)) {
            while (input.hasNextLine()) {
                String[] adLine = input.nextLine().split(",");
                String companyName = adLine[0];
                String title = adLine[1];
                String description = adLine[2];
                LocalDateTime arrivalTime = LocalDateTime.parse(adLine[3]);
                Delivery.Status status = (adLine[3].equalsIgnoreCase("DELIVERED")) ?
                        Delivery.Status.DELIVERED : Delivery.Status.ONGOING;
                ads.add(new Advertisement(description, arrivalTime, title, companyName, null));
            }
        } catch (FileNotFoundException fnfe) {
            handler(fnfe, COMPANYADVERTISEMENTS_FILE_PATH);
        }

        return ads;
    }

    /**
     * inner function for importData() that imports all Client data from Clients.csv
     * @return a List of Client containing all the clients in the post-office
     * @throws RuntimeException general unchecked exception
     */
    private static List<Client> importClients() throws RuntimeException {
        File clientPath = new File(CLIENT_FILE_PATH);
        List<Client> clients = new ArrayList<>();
        Client.setNextId(0);

        try (Scanner input = new Scanner(clientPath)) {
            while (input.hasNextLine()) {
                String[] clientLine = input.nextLine().split(",");

                int id = Integer.parseInt(clientLine[0]); //optional
                String name = clientLine[1];
                String address = clientLine[2];
                String email = clientLine[3];

                clients.add(new Client(name, email, address));
            }
        } catch (FileNotFoundException fnfe) {
            handler(fnfe, CLIENT_FILE_PATH);
        }

        return clients;
    }

    /**
     * inner function for importData() that imports all Staff data from Staffs.csv
     * @return a List of Staff containing all the staffs in the post-office
     * @throws RuntimeException general unchecked exception
     */
    private static List<Staff> importStaffs() throws RuntimeException {
        File staffPath = new File(STAFF_FILE_PATH);
        List<Staff> staffs = new ArrayList<>();
        Staff.setNextId(0);

        try (Scanner input = new Scanner(staffPath)) {
            while (input.hasNextLine()) {
                String[] staffLine = input.nextLine().split(",");

                int id = Integer.parseInt(staffLine[0]); //optional
                String name = staffLine[1];
                String email = staffLine[2];
                Staff.Role role = (staffLine[3].equalsIgnoreCase("INDOORS")) ?
                        Staff.Role.INDOORS : Staff.Role.COURIER;

                if (role.equals(Staff.Role.COURIER)) {
                    staffs.add(new Courier(name, email));
                }
                else {
                    staffs.add(new Staff(name, email));
                }
            }
        } catch (FileNotFoundException fnfe) {
            handler(fnfe, STAFF_FILE_PATH);
        }

        return staffs;
    }

    /**
     * inner function for importData() that imports all Staff security data from Client_SecurityPass.csv
     * @return a Map of clients with their passwords as their values
     * @throws RuntimeException general unchecked exception
     */
    private static Map<Client, String> importClientSecurityPass() throws RuntimeException {
        File clientSecurityPath = new File(CLIENT_SECURITY_PASS_FILE_PATH);
        Map<Client, String> clientSecurityPass = new TreeMap<>(new Client.ClientComparator(""));

        try (Scanner input = new Scanner(clientSecurityPath)) {
            while (input.hasNextLine()) {
                String[] securityLine = input.nextLine().split(",");

                String email = securityLine[0];
                String password = securityLine[1];

                clientSecurityPass.put(searchClientByEmail(email), password);
            }
        } catch (FileNotFoundException fnfe) {
            handler(fnfe, STAFF_SECURITY_PASS_FILE_PATH);
        }

        return clientSecurityPass;
    }

    /**
     * inner function for importData() that imports all Staff security data from Staff_SecurityPass.csv
     * @return a Map of clients with their passwords as their values
     * @throws RuntimeException general unchecked exception
     */
    private static Map<Staff, String> importStaffSecurityPass() throws RuntimeException {
        File staffSecurityPath = new File(STAFF_SECURITY_PASS_FILE_PATH);
        Map<Staff, String> staffsSecurityPass = new TreeMap<>(new Staff.StaffComparator(""));

        try (Scanner input = new Scanner(staffSecurityPath)) {
            while (input.hasNextLine()) {
                String[] securityLine = input.nextLine().split(",");

                String email = securityLine[0];
                String password = securityLine[1];

                staffsSecurityPass.put(searchStaffByEmail(email), password);
            }
        } catch (FileNotFoundException fnfe) {
            handler(fnfe, STAFF_SECURITY_PASS_FILE_PATH);
        }

        return staffsSecurityPass;
    }

    /**
     * inner function for importData() that imports all OPEN Ticket data from Opened_Tickets.csv
     * @throws RuntimeException general unchecked exception
     */
    private static void importOpenedTickets() throws RuntimeException{
        importTickets(OPENEDTICKETS_FILE_PATH);
    }

    /**
     * inner function for importData() that imports all OPEN Ticket data from Ongoing_Tickets.csv
     * @throws RuntimeException general unchecked exception
     */
    private static void importOngoingTickets() throws RuntimeException{
        importTickets(ONGOINGTICKETS_FILE_PATH);
        distributeTicketsToStaff();
    }

    /**
     * inner function for importData() that imports all CLOSED Tickets from Completed_Tickets.csv
     * @throws RuntimeException general unchecked exception
     */
    private static void importCompletedTickets() throws RuntimeException{
        importTickets(COMPLETEDTICKETs_FILE_PATH);
    }

    /**
     * inner function that imports all Tickets from any ticket files
     * @param path the files with Ticket objects
     * @throws RuntimeException general unchecked exception
     */
    private static void importTickets(String path) throws RuntimeException {
        File ticketPath = new File(path);

        Queue<Ticket> newOpenTicketQueue = new PriorityQueue<>(new Ticket.TicketComparator(""));
        List<Ticket> newOngoingTicketList = new ArrayList<>();
        List<Ticket> newCompletedTicketList = new ArrayList<>();

        try (Scanner input = new Scanner(ticketPath)) {
            while (input.hasNextLine()) {
                String[] ticketLine = input.nextLine().split(",");

                int id = Integer.parseInt(ticketLine[0]);
                String title = ticketLine[1];
                Ticket.Type type = (ticketLine[2].equalsIgnoreCase("BUGREPORT")) ?
                        Ticket.Type.BUGREPORT : Ticket.Type.SUPPORT;
                String detail = ticketLine[3];
                Ticket.TicketStatus status = switch (ticketLine[4].toLowerCase()) {
                    case "processing" -> Ticket.TicketStatus.PROCESSING;
                    case "closed" -> Ticket.TicketStatus.CLOSED;
                    default -> Ticket.TicketStatus.OPEN;
                };
                String clientId = ticketLine[5];
                String staffId = (ticketLine[6].isBlank()) ? null : ticketLine[6];
                LocalDateTime creationTime = LocalDateTime.parse(ticketLine[7]);

                if (status.toString().equalsIgnoreCase("open")) {
                    newOpenTicketQueue.offer(new Ticket(title, detail, searchClient(Integer.parseInt(clientId)), type, status, null, creationTime));
                    if (!input.hasNextLine()) {
                        openedTickets = newOpenTicketQueue;
                    }
                }
                if (status.toString().equalsIgnoreCase("processing") && staffId != null) {
                    newOngoingTicketList.add(new Ticket(title, detail, searchClient(Integer.parseInt(clientId)), type, status, searchStaff(Integer.parseInt(staffId)), creationTime));
                    if (!input.hasNextLine()) {
                        ongoingTickets = newOngoingTicketList;
                    }
                }
                if (status.toString().equalsIgnoreCase("closed")  && staffId != null) {
                    newCompletedTicketList.add(new Ticket(title, detail, searchClient(Integer.parseInt(clientId)), type, status, searchStaff(Integer.parseInt(staffId)), creationTime));
                    if (!input.hasNextLine()) {
                        completedTickets = newCompletedTicketList;
                    }
                }
            }
        } catch (FileNotFoundException fnfe) {
            handler(fnfe, path);
        }
    }

    /**
     * distributes registered PROCESSING tickets to their respective staff
     */
    private static void distributeTicketsToStaff() {
        for (Ticket ticket : ongoingTickets) {
            if (searchStaff(ticket.getStaff().staffId) != null) {
                searchStaff(ticket.getStaff().staffId).getOngoingTickets().offer(ticket);
            }
        }
    }

    /**
     * finds the Client with the given id in the List of clients
     * @param clientId the id of the targeted client
     * @return the client with the given id
     */
    public static Client searchClient(Integer clientId) {
        if (clients == null || clients.isEmpty() || clientId == null) {
            return null;
        }

        for (Client client : clients) {
            if (client.getClientId() == clientId) {
                return client;
            }
        }

        System.out.println("Did not find client with given ID");

        return null;
    }

    /**
     * finds the Client with the given address in the List of clients
     * @param address the address of the targeted client
     * @return the client with the given address
     */
    public static Client searchClient(String address) {
        if (clients == null || clients.isEmpty() || address == null) {
            return null;
        }

        for (Client client : clients) {
            if (client.getAddress().equalsIgnoreCase(address)) {
                return client;
            }
        }

        System.out.println("Did not find staff with given address");

        return null;
    }

    /**
     * finds the Staff with the given id in the List of staffs
     * @param staffId the id of the targeted Staff
     * @return the Staff with the given id
     */
    public static Staff searchStaff(Integer staffId) {
        if (staffs == null || staffs.isEmpty() || staffId == null) {
            return null;
        }

        for (Staff staff : staffs) {
            if (staff.getStaffId() == staffId) {
                return staff;
            }
        }

        System.out.println("Did not find staff with given ID");

        return null;
    }

    /**
     * finds all users with the given name in the List of staffs & clients
     * @param name the name of the targeted staffs or clients
     * @return the List of staffs & clients with the given name
     */
    public static List<User> searchUserList(String name) {
        if (staffs == null || staffs.isEmpty() || clients == null || clients.isEmpty() ||
                email == null || email.isBlank()) {
            return new ArrayList<>();
        }

        List<User> users = new ArrayList<>();

        for (Staff staff : staffs) {
            if (staff.getName().equalsIgnoreCase(name)) {
                users.add(staff);
            }
        }

        for (Client client : clients) {
            if (client.getName().equalsIgnoreCase(name)) {
                users.add(client);
            }
        }

        return users;
    }

    /**
     * finds the Client with the given email in the List of clients
     * @param email the email of the targeted client
     * @return the client with the given email
     */
    public static Client searchClientByEmail(String email) {
        if (clients == null || clients.isEmpty() || email == null || email.isBlank()) {
            return null;
        }

        for (Client client : clients) {
            if (client.getEmail().equalsIgnoreCase(email)) {
                return client;
            }
        }

        return null;
    }

    /**
     * finds the Staff with the given email in the List of staffs
     * @param email the email of the targeted staff
     * @return the Staff with the given email
     */
    public static Staff searchStaffByEmail(String email) {
        if (staffs == null || staffs.isEmpty() || email == null || email.isBlank()) {
            return null;
        }

        for (Staff staff : staffs) {
            if (staff.getEmail().equalsIgnoreCase(email)) {
                return staff;
            }
        }

        return null;
    }

    /**
     * finds any User with the given email in both List of staffs & clients
     * @param email the email of the targeted staff or client
     * @return the staff & client with the given email
     */
    public static User searchUser(String email) {
        if (staffs == null || staffs.isEmpty() || clients == null || clients.isEmpty() ||
                email == null || email.isBlank()) {
            return null;
        }

        Staff staff = searchStaffByEmail(email);
        Client client = searchClientByEmail(email);

        if (staff != null) {
            return staff;
        }
        else {
            return client;
        }
    }

}
