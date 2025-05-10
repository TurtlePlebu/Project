package org.example;

import java.util.*;

public class Client extends User implements Registerable {
    private static int nextId = 0;

    private int clientId;
    private String address;

    public Client(String name, String email, String address) {
        super(name, email);
        this.clientId = nextId++;
        this.address = address;
    }

    public Client(String name, String email, String address, List<Delivery> deliveries) {
        super(name, email);
        this.clientId = nextId++;
        this.address = address;
        this.deliveries = deliveries;
    }

    /**
     * registers the client to the Post-Office system
     * updates the Post-Office data afterward
     */
    @Override
    public void register() {
        PostOffice.clients.add(this);
        PostOffice.exportData();
    }

    /**
     * displays all the deliveries and advertisements with this Client's address
     * @param sorting the format of the display
     */
    @Override
    protected void viewDelivery(String sorting) {
        if (deliveries.isEmpty()) {
            this.deliveries = List.copyOf(PostOffice.deliveries)
                    .stream()
                    .filter(client -> client.getAddress().equalsIgnoreCase(this.address))
                    .toList();
        }

        deliveries.sort(new Delivery.DeliveryComparator(sorting));

        for (Advertisement ad : PostOffice.advertisements) {
            System.out.printf("Ad : %s", ad);
        }

        for (Delivery delivery : deliveries) {
            if (delivery instanceof Parcel p) {
                System.out.printf("Parcel : %s", p);
            }
            if (delivery instanceof Mail m) {
                System.out.printf("Message : %s", m);
            }
        }

    }

    /**
     * sends a BUG REPORT Ticket in Post-Office's opened ticket queue
     * @param title the title of the ticket
     * @param description the description of the problem
     */
    public void sendBugReport(String title, String description) {
        PostOffice.openedTickets.offer(new Ticket(title, description, this, Ticket.Type.BUGREPORT));
        PostOffice.exportData();
    }

    /**
     * sends a SUPPORT Ticket in Post-Office's opened ticket queue
     * @param title the title of the ticket
     * @param description the description of the problem
     */
    public void sendSupportRequest(String title, String description) {
        PostOffice.openedTickets.offer(new Ticket(title, description, this, Ticket.Type.SUPPORT));
        PostOffice.exportData();
    }

    /**
     * adds a Delivery in the Client's list of Delivery
     * and adds the Delivery to the Post-Office's data if not already existed
     * @param del the receiving Delivery
     * @return a true or false value indicating the success of the operation
     */
    @Override
    public boolean receiveDelivery(Delivery del) {
        if (del != null) {
            deliveries.add(del);
        }

        if (!PostOffice.deliveries.contains(del)) {
            PostOffice.deliveries.add(del);
            PostOffice.exportData();
        }

        return !PostOffice.deliveries.contains(del);
    }

    /**
     * inner Comparator class sorting by:
     * name ascendingly, then id ascendingly
     * name descendingly, then id ascendingly
     * id descendingly
     * by default, id ascendingly
     */
    public static class ClientComparator implements Comparator<Client> {
        private String type;

        public ClientComparator(String type) {
            this.type = type;
        }

        @Override
        public int compare(Client o1, Client o2) {
            return switch (type.toLowerCase()) {
                case "name ascendingly" -> o1.getName().compareTo(o2.getName()) * 100 + (o1.getClientId() - o2.getClientId());
                case "name descendingly" -> o2.getName().compareTo(o1.getName()) * 100 + (o1.getClientId() - o2.getClientId());
                case "id descendingly" -> (o2.getClientId() - o1.getClientId());
                default -> (o1.getClientId() + o2.getClientId());
            };
        }
    }

    @Override
    public String toString() {
        return String.format("Client :\n" +
                "%-10s: %d\n" +
                super.toString() +
                "%-10s: %s\n",
                "Client ID", clientId,
                "Address", address);
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Client client = (Client) o;
        return clientId == client.clientId && Objects.equals(address, client.address);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), clientId, address);
    }

    public int getClientId() {
        return clientId;
    }

    public void setClientId(int clientId) {
        this.clientId = clientId;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

}
