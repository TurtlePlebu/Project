package org.example;

import java.time.LocalDateTime;
import java.util.Objects;

public class Mail extends Delivery{

    protected String title;

    public Mail(String address, String description, LocalDateTime arrivalTime, String title) {
        super(address, description, arrivalTime);
        this.title = title;
    }

    public Mail(String address, String description, LocalDateTime arrivalTime, Status status, String title) {
        super(address, description, arrivalTime, status);
        this.title = title;
    }

    @Override
    public String toString() {
        return String.format("Mail : %-10s" + super.toString(),
                title);
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Mail mail = (Mail) o;
        return Objects.equals(title, mail.title);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), title);
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
