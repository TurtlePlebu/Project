package org.example;

import java.util.Objects;

public class Manager extends User{
    private static int nextID = 0;

    private int managerId;

    public Manager(String name, String email) {
        super(name, email);
        this.managerId = nextID;
    }

    @Override
    public void viewDelivery() {
        for (Delivery delivery : PostOffice.deliveries) {
            if (delivery instanceof Parcel p) {
                System.out.printf("Parcel : %s", p);
            }
            if (delivery instanceof Mail m) {
                System.out.printf("Message : %s", m);
            }
        }
    }

    protected void viewStaff() {
        for (Staff staff : PostOffice.staffs) {
            if (staff instanceof Courier c) {
                System.out.printf("Courier : %s", c);
            }
            else {
                System.out.printf("Staff : %s", staff);
            }
        }
    }

    protected Staff searchStaff(String name) {
        if (name == null || name.isBlank()) {
            return null;
        }

        return PostOffice.searchStaff(name);
    }

    protected Staff searchStaff(int id) {
        return searchStaff(id);
    }

    @Override
    public String toString() {
        return String.format("Manager :\n" +
                "%-10s: %d\n" +
                super.toString(),
                "Manager ID", managerId);
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Manager manager = (Manager) o;
        return managerId == manager.managerId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), managerId);
    }

    public int getManagerId() {
        return managerId;
    }

    public void setManagerId(int managerId) {
        this.managerId = managerId;
    }
}
