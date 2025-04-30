package org.example;

import java.time.LocalDateTime;
import java.util.Objects;

public class Ticket {
    private static int nextId = 0;

    private String title;
    private String detail;
    private Client client;
    private Type type;
    private Status status;
    private Staff staff;
    private int ticketId;

    public Ticket(String title, String detail, Client client, Type type) {
        this.ticketId = nextId++;
        this.title = title;
        this.detail = detail;
        this.client = client;
        this.type = type;
        this.status = Ticket.Status.OPEN;
        this.staff = null;
    }

    public Ticket(String title, String detail, Client client, Type type, Status status, Staff staff) {
        this.ticketId = nextId++;
        this.title = title;
        this.detail = detail;
        this.client = client;
        this.type = type;
        this.status = status;
        this.staff = staff;
    }

    public static enum Type {
        BUGREPORT,
        SUPPORT;

        private Type() {
        }
    }

    private static enum Status {
        OPEN,
        PROCESSING,
        CLOSED;

        private Status() {
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
                String.valueOf(status)
        );
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Ticket ticket = (Ticket) o;
        return ticketId == ticket.ticketId && Objects.equals(title, ticket.title) && Objects.equals(detail, ticket.detail) && Objects.equals(client, ticket.client) && type == ticket.type && status == ticket.status && Objects.equals(staff, ticket.staff);
    }

    @Override
    public int hashCode() {
        return Objects.hash(title, detail, client, type, status, staff, ticketId);
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

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
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
}
