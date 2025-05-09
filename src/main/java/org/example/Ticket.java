package org.example;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.Objects;

public class Ticket {
    private static int nextId = 0;

    private String title;
    private String detail;
    private Client client;
    private Type type;
    private TicketStatus ticketStatus;
    private Staff staff;
    private int ticketId;
    private LocalDateTime creationTime;

    public Ticket(String title, String detail, Client client, Type type) {
        this.ticketId = nextId++;
        this.title = title;
        this.detail = detail;
        this.client = client;
        this.type = type;
        this.ticketStatus = TicketStatus.OPEN;
        this.staff = null;
        this.creationTime = LocalDateTime.now();
    }

    public Ticket(String title, String detail, Client client, Type type, TicketStatus ticketStatus, Staff staff, LocalDateTime creationTime) {
        this.ticketId = nextId++;
        this.title = title;
        this.detail = detail;
        this.client = client;
        this.type = type;
        this.ticketStatus = ticketStatus;
        this.staff = staff;
        this.creationTime = creationTime;
    }

    public static class TicketComparator implements Comparator<Ticket> {
        private String type;

        public TicketComparator(String type) {
            this.type = type;
        }

        @Override
        public int compare(Ticket o1, Ticket o2) {
            return switch (type.toLowerCase()) {
                case "time ascendingly" -> o1.getCreationTime().compareTo(o2.getCreationTime()) * 100 + o1.getTicketId() - o2.getTicketId();
                case "time descedingly" -> o2.getCreationTime().compareTo(o1.getCreationTime()) * 100 + o1.getTicketId() - o2.getTicketId();
                case "id descendingly" -> o2.getTicketId() - o1.getTicketId();
                default -> o1.getTicketId() - o2.getTicketId();
            };
        }
    }

    public static enum Type {
        BUGREPORT,
        SUPPORT;

        private Type() {
        }
    }

    public static enum TicketStatus {
        OPEN,
        PROCESSING,
        CLOSED;

        private TicketStatus() {
        }
    }

    @Override
    public String toString() {
        return String.format("%s :\n" +
                "%d\n" +
                "%s\n" +
                "%s\n" +
                "%s\n" +
                "%s\n",
                String.valueOf(type),
                ticketId,
                title,
                detail,
                client.getName(),
                (staff != null) ? staff.getName() : null,
                String.valueOf(ticketStatus)
        );
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Ticket ticket = (Ticket) o;
        return ticketId == ticket.ticketId && Objects.equals(title, ticket.title) && Objects.equals(detail, ticket.detail) && Objects.equals(client, ticket.client) && type == ticket.type && ticketStatus == ticket.ticketStatus && Objects.equals(staff, ticket.staff);
    }

    @Override
    public int hashCode() {
        return Objects.hash(title, detail, client, type, ticketStatus, staff, ticketId);
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public TicketStatus getTicketStatus() {
        return ticketStatus;
    }

    public void setTicketStatus(TicketStatus ticketStatus) {
        this.ticketStatus = ticketStatus;
    }

    public Staff getStaff() {
        return staff;
    }

    public void setStaff(Staff staff) {
        this.staff = staff;
    }

    public int getTicketId() {
        return ticketId;
    }

    public void setTicketId(int ticketId) {
        this.ticketId = ticketId;
    }

    public LocalDateTime getCreationTime() {
        return creationTime;
    }

    public void setCreationTime(LocalDateTime creationTime) {
        this.creationTime = creationTime;
    }
}
