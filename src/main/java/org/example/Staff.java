package org.example;

import java.util.Objects;

public class Staff extends User implements Registerable {
    protected static int nextId = 0;

    protected int staffId;
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

    @Override
    public boolean receiveDelivery(Delivery del) {
        if (!PostOffice.deliveries.contains(del)) {
            PostOffice.deliveries.add(del);
        }

        return !PostOffice.deliveries.contains(del);
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
        return staffId == staff.staffId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), staffId);
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
}
