package org.example;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.Objects;

public class Advertisement extends Mail implements Comparable<Advertisement> {

    private String companyName;

    public Advertisement(String description, LocalDateTime arrivalTime, String title, String companyName) {
        super(null, description, arrivalTime, title);
        this.companyName = companyName;
    }

    /**
     * naturally sorts the advertisements by company names
     * @param o the object to be compared.
     * @return
     */
    @Override
    public int compareTo(Advertisement o) {
        return this.companyName.compareTo(o.companyName);
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Advertisement that = (Advertisement) o;
        return Objects.equals(companyName, that.companyName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), companyName);
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }
}
