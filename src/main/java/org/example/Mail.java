package org.example;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.Objects;

public class Mail extends Delivery {

    protected String title;

    public Mail(String address, String description, LocalDateTime arrivalTime, String title) {
        super(address, description, arrivalTime);
        this.title = title;
    }

    public Mail(String address, String description, LocalDateTime arrivalTime, Status status, String title) {
        super(address, description, arrivalTime, status);
        this.title = title;
    }

    /**
     * inner Comparator class sorting by:
     * the title ascendingly, then the arrival time of the Delivery ascendingly
     * the title descendingly, then the arrival time of the Delivery ascendingly
     * the arrival time of the Delivery descendingly, then the title ascendingly
     * by default, the arrival time of the Delivery ascendingly, then the title ascendingly
     */
    public static class MailComparator implements Comparator<Mail> {
        private String type;

        public MailComparator(String type) {
            this.type = type;
        }

        @Override
        public int compare(Mail o1, Mail o2) {
            return switch (type.toLowerCase()) {
                case "title ascendingly" -> (o1.getTitle().compareTo(o2.getTitle())) * 100 + (o1.getArrivalTime().compareTo(o2.getArrivalTime()));
                case "title descendingly" -> (o2.getTitle().compareTo(o1.getTitle())) * 100 + (o1.getArrivalTime().compareTo(o2.getArrivalTime()));
                case "time descendingly" -> (o2.getArrivalTime().compareTo(o1.getArrivalTime())) * 100 + (o1.getTitle().compareTo(o2.getTitle()));
                default -> (o1.getArrivalTime().compareTo(o2.getArrivalTime())) * 100 + (o1.getTitle().compareTo(o2.getTitle()));
            };
        }
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
