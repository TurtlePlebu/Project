package org.example;

public interface UsersViewing {
    default void viewClient(String sorting) {
        PostOffice.clients.sort(new Client.ClientComparator(sorting));

        for (Client client : PostOffice.clients) {
            System.out.printf("Client : %s", client);
        }
    }

    default void viewStaff(String sorting) {
        PostOffice.staffs.sort(new Staff.StaffComparator(sorting));

        for (Staff staff : PostOffice.staffs) {
            if (staff instanceof Courier c) {
                System.out.printf("Courier : %s", c);
            }
            else {
                System.out.printf("Staff : %s", staff);
            }
        }
    }
}
