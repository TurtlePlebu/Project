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
