package org.example;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
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
        File parcelFile = new File("src/main/resources/parcels.csv");

        try (FileWriter fw = new FileWriter(parcelFile)) {
            fw.write(parcel.getParcelId() + ",");
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
    private void exportMails(Mail mail) throws IOException, RuntimeException{
        File mailPath = new File("src/main/resources/mails.csv");
        Set<Advertisement> companyAdvertisements = new HashSet<>();

        if (mail instanceof Advertisement ad) {
            if (!companyAdvertisements.contains(ad)) {
                companyAdvertisements.add(ad);
            }
        }
        else {
            try (FileWriter fw = new FileWriter(mailPath)) {
                fw.write(mail.getTitle() + ",");
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
    private void exportCompanyAdvertisements(Set<Advertisement> ads) throws IOException, RuntimeException{
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
    private void exportClients() throws IOException, RuntimeException{
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
    private void exportTickets() throws IOException, RuntimeException{
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
