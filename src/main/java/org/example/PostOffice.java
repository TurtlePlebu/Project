package org.example;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.*;

public class PostOffice {
    private String name;
    private String address;
    private String email;
    private List<Delivery> deliveries;
    private List<Ticket> tickets;
    private List<Client> clients;
    private List<Staff> staffs;
    private Manager manager;

    public PostOffice(String name, String address, String email, Manager manager) {
        this.name = name;
        this.address = address;
        this.email = email;
        this.manager = manager;
        this.deliveries = new ArrayList<>();
        this.tickets= new ArrayList<>();
        this.clients = new ArrayList<>();
        this.staffs = new ArrayList<>();
    }

    public PostOffice(String name, String address, String email, List<Delivery> deliveries, List<Ticket> tickets, List<Client> clients, List<Staff> staffs, Manager manager) {
        this.name = name;
        this.address = address;
        this.email = email;
        this.deliveries = deliveries;
        this.tickets = tickets;
        this.clients = clients;
        this.staffs = staffs;
        this.manager = manager;
    }

    /**
     * exports all current data inside the post-center into their appropriate files in Resources
     */
    public void exportData() {
        try {
            exportPostOfficeInfo();
            exportDeliveries();
            exportClients();
            exportStaffs();
            exportTickets();

        } catch (IOException e) {
            throw new RuntimeException(e);

        } catch (RuntimeException e) {
            System.out.println(e.getMessage());
            for (StackTraceElement trace : e.getStackTrace()) {
                System.out.println(trace.toString());
            }
        }
    }

    /**
     * inner function for exportData() function that exports all the post-center's shared information
     * @throws IOException Output data exceptions during writing data
     * @throws RuntimeException General unchecked exception
     */
    private void exportPostOfficeInfo() throws IOException, RuntimeException {
        File file = new File("src/main/resources/Post-Center.csv");

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
    private void exportDeliveries() throws IOException, RuntimeException {
        for (Delivery delivery : deliveries) {
            if (delivery instanceof Parcel parcel1) {
                exportParcels(parcel1);
            }
            if (delivery instanceof Mail mail1) {
                exportMails(mail1);
            }
        }
    }

    /**
     * inner function for exportDeliveries() function that exports all the parcels of the post-center
     * @param parcel given parcel to export into the parcel resource file
     * @throws IOException Output data exceptions during writing data
     * @throws RuntimeException General unchecked exception
     */
    private void exportParcels(Parcel parcel) throws IOException, RuntimeException {
        File parcelFile = new File("src/main/resources/Parcels.csv");

        try (FileWriter fw = new FileWriter(parcelFile)) {
            fw.write(parcel.getParcelId() + ",");
            fw.write(parcel.getAddress() + ",");
            fw.write(parcel.getDescription() + ",");
            fw.write(parcel.getQuantity() + ",");
            fw.write(parcel.getItem().getName() + ",");
            fw.write(parcel.getItem().getWeight() + ",");
            fw.write(parcel.getItem().getPurchasedTime().toString() + ",");
            fw.write(parcel.getArrivalTime().toString() + ",");
            fw.write(parcel.getStatus().toString() + "\n");
        }
    }

    /**
     * inner function for exportDeliveries() function that exports all the mails of the post-center
     * separates Advertisements into a different file without duplicates
     * @param mail given mail to export into the mail resource file
     * @throws IOException Output data exceptions during writing data
     * @throws RuntimeException General unchecked exception
     */
    private void exportMails(Mail mail) throws IOException, RuntimeException {
        File mailPath = new File("src/main/resources/Mails.csv");
        Set<Advertisement> companyAdvertisements = new HashSet<>();

        if (mail instanceof Advertisement ad) {
            if (!companyAdvertisements.contains(ad)) {
                companyAdvertisements.add(ad);
            }
        }
        else {
            try (FileWriter fw = new FileWriter(mailPath)) {
                fw.write(mail.getTitle() + ",");
                fw.write(mail.getAddress() + ",");
                fw.write(mail.getDescription() + ",");
                fw.write(mail.getArrivalTime().toString() + ",");
                fw.write(mail.getStatus().toString() + "\n" );
            }
        }

        exportCompanyAdvertisements(companyAdvertisements);
    }

    /**
     * inner function for exportMails() function that includes each unique Advertisement
     * @param ads the Set of all different Advertisements to export
     * @throws IOException Output data exceptions during writing data
     * @throws RuntimeException General unchecked exception
     */
    private void exportCompanyAdvertisements(Set<Advertisement> ads) throws IOException, RuntimeException {
        if (ads.isEmpty()) {
            return;
        }

        File adPath = new File("src/main/resources/CompanyAdvertisements.csv");

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
    private void exportClients() throws IOException, RuntimeException {
        File clientPath = new File("src/main/resources/Clients.csv");

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
    private void exportStaffs() throws IOException, RuntimeException {
        File staffPath = new File("src/main/resources/Staffs.csv");

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
     * inner function for exportData() function that exports all Tickets in the post-center
     * @throws IOException Output data exceptions during writing data
     * @throws RuntimeException General unchecked exception
     */
    private void exportTickets() throws IOException, RuntimeException {
        File ticketPath = new File("src/main/resources/Tickets.csv");

        try (FileWriter fw = new FileWriter(ticketPath)) {
            for (Ticket ticket : tickets) {
                fw.write(ticket.getTicketId() + ",");
                fw.write(ticket.getTitle() + ",");
                fw.write(ticket.getType().toString() + ",");
                fw.write(ticket.getDetail() + ",");
                fw.write(ticket.getTicketStatus().toString() + ",");
                fw.write(ticket.getClient().getName() + ",");
                if (ticket.getStaff() == null) {
                    fw.write(" ,");
                }
                else {
                    fw.write(ticket.getStaff().getName() + ",");
                }
                fw.write(ticket.getCreationTime().toString() + "\n");
            }
        }
    }

    /**
     * loads all registered data from Resource file
     * @return the Post-Office with all registered data
     */
    public PostOffice importData() {
        try {

            String[] info = importPostOfficeInfo();
            String name = info[0];
            String address = info[1];
            String email = info[2];
            Manager manager = new Manager(info[3], info[4]);
            return new PostOffice(name, address, email, importDeliveries(), importTickets(), importClient(), importStaffs(), manager);

        } catch (RuntimeException e) {
            System.out.println(e.getMessage());
            for (StackTraceElement ste : e.getStackTrace()) {
                System.out.println(ste.toString());
            }
        }
        return null;
    }

    /**
     * inner function for importData() that imports all information data from Post-Center.csv
     * @return a String array containing the shared information of the post-office
     * @throws RuntimeException general unchecked exception
     */
    private String[] importPostOfficeInfo()  throws RuntimeException {
        File file = new File("src/main/resources/Post-Center.csv");

        String[] info = new String[]{};

        try (Scanner input = new Scanner(file)) {
            info = input.nextLine().split(",");

        } catch (FileNotFoundException fnfe) {
            System.out.println("Can not find Post-Center.csv");
            System.out.println(fnfe.getMessage());
            for (StackTraceElement ste : fnfe.getStackTrace()) {
                System.out.println(ste.toString());
            }
        }

        return info;
    }

    /**
     * inner function for importData() that imports all Delivery data from Delivery .csv files
     * @return a List of Delivery containing all the deliveries in the post-office
     * @throws RuntimeException general unchecked exception
     */
    private List<Delivery> importDeliveries() throws RuntimeException {
        List<Delivery> deliveries = new ArrayList<>();

        for (Delivery delivery : importParcels()) {
            deliveries.add(delivery);
        }
        for (Delivery delivery : importMails()) {
            deliveries.add(delivery);
        }

        return deliveries;
    }

    /**
     * inner function for importDeliveries() that imports all Parcels data from Parcels.csv
     * @return a List of Delivery containing all the parcels in the post-office
     * @throws RuntimeException general unchecked exception
     */
    private List<Delivery> importParcels() throws RuntimeException {
        File parcelFile = new File("src/main/resources/Parcels.csv");
        List<Delivery> parcels = new ArrayList<>();

        try (Scanner input = new Scanner(parcelFile)) {
            while (input.hasNextLine()) {
                String[] parcelLine = input.nextLine().split(",");

                int id = Integer.parseInt(parcelLine[0]); //Optional
                String address = parcelLine[1];
                String description = parcelLine[2];
                int quantity = Integer.parseInt(parcelLine[3]);
                String itemName = parcelLine[4];
                int itemWeight = Integer.parseInt(parcelLine[5]);
                LocalDateTime itemTime = LocalDateTime.parse(parcelLine[6]);
                LocalDateTime arrivalTime = LocalDateTime.parse(parcelLine[7]);
                Delivery.Status status = (parcelLine[8].equalsIgnoreCase("DELIVERED")) ?
                        Delivery.Status.DELIVERED : Delivery.Status.ONGOING;

                parcels.add(new Parcel(address, description, arrivalTime, status, new Item(itemName, itemWeight, itemTime), quantity));
            }

        } catch (FileNotFoundException fnfe) {
            System.out.println("Can not find Parcels.csv");
            System.out.println(fnfe.getMessage());
            for (StackTraceElement ste : fnfe.getStackTrace()) {
                System.out.println(ste.toString());
            }
        }

        return parcels;
    }

    /**
     * inner function for importDeliveries() that imports all Mails data from Mails.csv
     * @return a List of Delivery containing all the mails in the post-office
     * @throws RuntimeException general unchecked exception
     */
    private List<Delivery> importMails() throws RuntimeException {
        File mailPath = new File("src/main/resources/Mails.csv");
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

                mails.add(new Mail(address, description, arrivalTime, status, title));
            }

        } catch (FileNotFoundException fnfe) {
            System.out.println("Can not find Mails.csv");
            System.out.println(fnfe.getMessage());
            for (StackTraceElement ste : fnfe.getStackTrace()) {
                System.out.println(ste.toString());
            }
        }

        return mails;
    }

    /**
     * inner function for importData() that imports all Client data from Clients.csv
     * @return a List of Client containing all the clients in the post-office
     * @throws RuntimeException general unchecked exception
     */
    private List<Client> importClient() throws RuntimeException {
        File clientPath = new File("src/main/resources/Clients.csv");
        List<Client> clients = new ArrayList<>();

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
            System.out.println("Can not find Clients.csv");
            System.out.println(fnfe.getMessage());
            for (StackTraceElement ste : fnfe.getStackTrace()) {
                System.out.println(ste.toString());
            }
        }

        return clients;
    }

    /**
     * inner function for importData() that imports all Staff data from Staffs.csv
     * @return a List of Staff containing all the staffs in the post-office
     * @throws RuntimeException general unchecked exception
     */
    private List<Staff> importStaffs() throws RuntimeException {
        File staffPath = new File("src/main/resources/Staffs.csv");
        List<Staff> staffs = new ArrayList<>();

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
            System.out.println("Can not find Staffs.csv");
            System.out.println(fnfe.getMessage());
            for (StackTraceElement ste : fnfe.getStackTrace()) {
                System.out.println(ste.toString());
            }
        }

        return staffs;
    }

    /**
     * inner function for importData() that imports all Ticket data from Tickets.csv
     * @return a List of Ticket containing all the opened, processed or closed tickets in the post-office
     * @throws RuntimeException general unchecked exception
     */
    private List<Ticket> importTickets() throws RuntimeException{
        File ticketPath = new File("src/main/resources/Tickets.csv");
        List<Ticket> tickets = new ArrayList<>();

        try (Scanner input = new Scanner(ticketPath)) {
            while (input.hasNextLine()) {
                String[] ticketLine = input.nextLine().split(",");

                int id = Integer.parseInt(ticketLine[0]);
                String title = ticketLine[1];
                Ticket.Type type = (ticketLine[2].equalsIgnoreCase("BUGREPORT")) ?
                        Ticket.Type.BUGREPORT : Ticket.Type.SUPPORT;
                String detail = ticketLine[3];
                Ticket.TicketStatus status =
                        switch (ticketLine[4].toLowerCase()) {
                        case "processing" -> Ticket.TicketStatus.PROCESSING;
                        case "closed" -> Ticket.TicketStatus.CLOSED;
                        default -> Ticket.TicketStatus.OPEN;
                        };
                String clientName = ticketLine[5];
                String staffName = (ticketLine[6].isBlank()) ? null : ticketLine[6];
                LocalDateTime creationTime = LocalDateTime.parse(ticketLine[7]);

                tickets.add(new Ticket(title, detail, searchClient(clientName), type, status, searchStaff(staffName), creationTime));
            }
        } catch (FileNotFoundException fnfe) {
            System.out.println("Can not find Tickets.csv");
            System.out.println(fnfe.getMessage());
            for (StackTraceElement ste : fnfe.getStackTrace()) {
                System.out.println(ste.toString());
            }
        }

        return tickets;
    }



    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        PostOffice that = (PostOffice) o;
        return Objects.equals(name, that.name) && Objects.equals(address, that.address) && Objects.equals(email, that.email) && Objects.equals(deliveries, that.deliveries) && Objects.equals(tickets, that.tickets) && Objects.equals(clients, that.clients) && Objects.equals(staffs, that.staffs) && Objects.equals(manager, that.manager);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, address, email, deliveries, tickets, clients, staffs, manager);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public List<Delivery> getDeliveries() {
        return deliveries;
    }

    public void setDeliveries(List<Delivery> deliveries) {
        this.deliveries = deliveries;
    }

    public List<Ticket> getTickets() {
        return tickets;
    }

    public void setTickets(List<Ticket> tickets) {
        this.tickets = tickets;
    }

    public List<Client> getClients() {
        return clients;
    }

    public void setClients(List<Client> clients) {
        this.clients = clients;
    }

    public List<Staff> getStaffs() {
        return staffs;
    }

    public void setStaffs(List<Staff> staffs) {
        this.staffs = staffs;
    }

    public Manager getManager() {
        return manager;
    }

    public void setManager(Manager manager) {
        this.manager = manager;
    }
}
