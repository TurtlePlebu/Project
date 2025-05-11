package org.example;

import java.util.*;

public class Staff extends User implements Registerable, UsersDeliveryManaging {
    protected static int nextId = 0;
    private static Queue<Parcel> processedParcels = new PriorityQueue<>();

    protected int staffId;
    private Queue<Ticket> ongoingTickets = new PriorityQueue<>();
    protected Role role;

    public Staff(String name, String email) {
        super(name, email);
        this.staffId = nextId++;
        this.role = Staff.Role.INDOORS;
    }

    /**
     * registers the Staff to the Post-Office system
     * updates the Post-Office data afterward
     */
    @Override
    public void register(String password) {
        PostOffice.staffs.add(this);
        PostOffice.staffSecurityPass.put(this, password);
        PostOffice.exportData();
    }

    /**
     * adds a Delivery in the Post-Center's list of Delivery
     * @param del the receiving Delivery
     * @return a true or false value indicating the success of the operation
     */
    @Override
    public boolean receiveDelivery(Delivery del) {
        if (!PostOffice.deliveries.contains(del)) {
            PostOffice.deliveries.add(del);
        }

        return !PostOffice.deliveries.contains(del);
    }

    /**
     * gives the ONGOING Parcel to the assigned Courier
     * @param c the assigned Courier to deliver the Parcel
     * @param parcel the ONGOING Parcel
     */
    public void assignProcessedParcel(Courier c, Parcel parcel) {
        for (Courier courier : searchCouriers()) {
            if (courier.equals(c)) {
                courier.pickupParcel(parcel);
            }
        }
        PostOffice.exportData();
    }

    /**
     * answers the next Ticket in queue
     * @param ticket the next Ticket
     * @param reply the reply from the Staff
     */
    public void replyTicket(Ticket ticket, String reply) {

        sendMail(reply,ticket.getTitle(), ticket.getClient().getEmail());

        ticket.setTicketStatus(Ticket.TicketStatus.CLOSED);
        ticket.setStaff(this);

        PostOffice.completedTickets.add(ticket);
        PostOffice.exportData();
    }

    /**
     * inner Comparator class sorting by:
     * name ascendingly, then id ascendingly
     * name descendingly, then id ascendingly
     * id descendingly
     * by default, id ascendingly
     */
    public static class StaffComparator implements Comparator<Staff> {
        private String type;

        public StaffComparator(String type) {
            this.type = type;
        }

        @Override
        public int compare(Staff o1, Staff o2) {
            return switch (type.toLowerCase()) {
                case "name ascendingly" -> o1.getName().compareTo(o2.getName()) * 100 + (o1.getStaffId() - o2.getStaffId());
                case "name descendingly" -> o2.getName().compareTo(o1.getName()) * 100 + (o1.getStaffId() - o2.getStaffId());
                case "id descendingly" -> (o2.getStaffId() - o1.getStaffId());
                default -> (o1.getStaffId() + o2.getStaffId());
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

    public Queue<Ticket> getOngoingTickets() {
        return ongoingTickets;
    }

    public static Queue<Parcel> getProcessedParcels() {
        return processedParcels;
    }
}
