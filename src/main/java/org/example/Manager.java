package org.example;

import java.time.Period;
import java.util.Objects;

public class Manager extends User implements UsersDeliveryManaging {
    private static int nextID = 0;

    private int managerId;

    public Manager(String name, String email) {
        super(name, email);
        this.managerId = nextID;
    }

    public void reassign(Staff personnel) {
        if (personnel instanceof Courier) {
            personnel.setRole(Staff.Role.INDOORS);
        }
        else {
            personnel.setRole(Staff.Role.COURIER);
        }
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
