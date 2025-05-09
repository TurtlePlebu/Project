package org.example;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;

public class Staff extends User implements Registerable {
    protected static int nextId = 0;

    protected int staffId;
    private List<Ticket> ongoingTickets = new ArrayList<>();
    protected Role role;

    public Staff(String name, String email) {
        super(name, email);
        this.staffId = nextId++;
        this.role = Staff.Role.INDOORS;
    }

    @Override
    public void register() {
        PostOffice.staffs.add(this);
        PostOffice.exportData();
    }

    @Override
    public void viewDelivery(String type) {
        PostOffice.deliveries.sort(new Delivery.DeliveryComparator(type));

        for (Delivery delivery : PostOffice.deliveries) {
            if (delivery instanceof Parcel p) {
                System.out.printf("Parcel : %s", p);
            }
            if (delivery instanceof Mail m) {
                System.out.printf("Message : %s", m);
            }
        }
    }

    @Override
    public boolean receiveDelivery(Delivery del) {
        if (!PostOffice.deliveries.contains(del)) {
            PostOffice.deliveries.add(del);
        }

        return !PostOffice.deliveries.contains(del);
    }

    public static class StaffComparator implements Comparator<Client> {
        private String type;

        public StaffComparator(String type) {
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

    public static enum Role {
        COURIER,
        INDOORS;

        private Role() {
        }
    }

    @Override
    public String toString() {
        return String.format("Staff :\n" +
                "%-10s: %d" +
                super.toString(),
                "Staff ID", staffId);
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Staff staff = (Staff) o;
        return staffId == staff.staffId && Objects.equals(ongoingTickets, staff.ongoingTickets) && role == staff.role;
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), staffId, ongoingTickets, role);
    }

    public int getStaffId() {
        return staffId;
    }

    public void setStaffId(int staffId) {
        this.staffId = staffId;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public List<Ticket> getOngoingTickets() {
        return ongoingTickets;
    }
}
