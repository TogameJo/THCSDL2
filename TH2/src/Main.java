package src;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Scanner;

import static src.Flight.searchFlights;
import static src.Booking.bookFlight;
import static src.Bookingcheck.viewBookings;

public class Main {
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        Connection conn = DBConnection.getConnection();
        while (true) {
            System.out.println("Chuc nang:");
            System.out.println("1. Tim chuyen bay");
            System.out.println("2. Dat chuyen bay");
            System.out.println("3. Xem dat cho");
            System.out.println("4. Thoat");
            System.out.print("Chon chuc nang: ");
            int choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    searchFlights(conn);
                    break;
                case 2:
                    bookFlight(conn);
                    break;
                case 3:
                    viewBookings(conn);
                    break;
                case 4:
                    System.out.println("Thoat chuong trinh.");
                    return;
                default:
                    System.out.println("Lua chon khong hop le!");
            }
        }
    }

}

