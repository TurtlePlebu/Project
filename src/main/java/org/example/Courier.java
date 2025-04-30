package org.example;

import java.util.ArrayList;
import java.util.Objects;

public class Courier extends Staff{

    private List<Delivery> deliveries;

    public Courier(String name, String email) {
        super(name, email);
        this.staffId = Staff.nextId++;
        this.role = Role.COURIER;
        this.deliveries = new ArrayList<>();
    }


    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Courier courier = (Courier) o;
        return Objects.equals(deliveries, courier.deliveries);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), deliveries);
    }

    public List<Delivery> getDeliveries() {
        return deliveries;
    }

    public void setDeliveries(List<Delivery> deliveries) {
        this.deliveries = deliveries;
    }
}
