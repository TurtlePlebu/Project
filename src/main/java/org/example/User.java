package org.example;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

public abstract class User {
    protected String name;
    protected String email;
    protected List<Delivery> deliveries;

    public User(String name, String email) {
        this.name = name;
        this.email = email;
        this.deliveries = new ArrayList<>();
    }

    /**
     * creates and sends a Mail to another User via email
     * @param description the description inside the message
     * @param title the title of the message
     * @param email the destination of the mail
     * @throws EmailNotFoundException unchecked exception for invalid or missing destination
     */
    protected void sendMail(String description, String title, String email) throws EmailNotFoundException {
        User receiver = PostOffice.searchUser(email);

        if (receiver == null) {
            throw new EmailNotFoundException();
        }

        String address;
        if (receiver instanceof Staff || receiver instanceof Manager) {
            address = PostOffice.address;
        }
        else {
            address = ((Client) receiver).getAddress();
        }

        Mail mail = new Mail(address, description, LocalDateTime.now(), title, email);

        receiver.getDeliveries().add(mail);
        mail.setStatus(Delivery.Status.DELIVERED);
        PostOffice.deliveries.add(mail);
        PostOffice.exportData();
    }

    /**
     * creates and sends a Parcel to another User via Email & the Post-Office
     * @param itemName the name of the Item inside the Parcel
     * @param description the description of the Item
     * @param weight the weight of the Item
     * @param quantity the quantity of Item inside the Parcel
     * @param email the destination of the mail
     * @throws EmailNotFoundException unchecked exception for invalid or missing destination
     */
    protected void sendParcel(String itemName, String description, double weight, int quantity, String email) throws EmailNotFoundException {
        User receiver = PostOffice.searchUser(email);

        if (receiver == null) {
            throw new EmailNotFoundException();
        }

        Parcel parcel;
        String address;

        if (receiver instanceof Staff || receiver instanceof Manager) {
            address = PostOffice.address;
            parcel = new Parcel(address, description, LocalDateTime.now().plusDays(5), new Item(itemName, weight, LocalDateTime.now()), quantity, null, email);
        }
        else {
            address = ((Client) receiver).getAddress();
            parcel = new Parcel(address, description, LocalDateTime.now().plusDays(5), new Item(itemName, weight, LocalDateTime.now()), quantity, null, email);
        }


        PostOffice.deliveries.add(parcel);

        PostOffice.exportData();
    }

    /**
     * voluntarily removes a Delivery in the Client's inbox
     * @param del the targeted Delivery to remove
     */
    protected void removeDelivery(Delivery del) {
        deliveries = deliveries.stream()
                .filter(delivery -> !delivery.equals(del))
                .toList();
        PostOffice.deliveries.remove(del);
        PostOffice.exportData();
    }

    /**
     * searches a Delivery within the user's deliveries
     * @param id the id of targeted Delivery
     * @return the targeted Delivery
     */
    protected Delivery searchDelivery(int id) {
        Delivery del = null;
        if (checkDelivery(id)) {
            for (Delivery delivery : deliveries) {
                if (delivery.getDeliveryId() == id) {
                    del = delivery;
                }
            }
        }

        return del;
    }

    /**
     * displays all the deliveries of the User
     * @param sorting the format of the display
     */
    protected void viewDelivery(String sorting) {
        deliveries = deliveries.stream()
                .sorted(new Delivery.DeliveryComparator(sorting))
                .toList();

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
     * checks if the Delivery exists within this user's deliveries
     * @param id the id of the Delivery
     * @return a true or false value indicating the presence of the Delivery
     */
    protected boolean checkDelivery(int id) {
        for (Delivery delivery : deliveries) {
            if (delivery.getDeliveryId() == id) {
                return true;
            }
        }
        return false;
    }

    @Override
    public String toString() {
        return String.format("Name : %s, " +
                "Email : %s, "
                , name
                , email
        );
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(name, user.name) && Objects.equals(email, user.email);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, email);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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
}
