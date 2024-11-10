package src;


import java.sql.*;
import java.util.Scanner;

public class Bookingcheck {
    public static void viewBookings(Connection conn) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Nhap ma khach hang: ");
        String customerId = scanner.nextLine();

        String query = "SELECT b.booking_date, f.fid, f.origin_city, f.dest_city, f.departure_delay, f.arrival_delay " +
                "FROM Bookings b " +
                "JOIN Flights f ON b.flight_id = f.fid " +
                "WHERE b.customer_id = ?";

        try (PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, customerId);
            ResultSet rs = pstmt.executeQuery();

            boolean hasBookings = false;
            while (rs.next()) {
                String bookingDate = rs.getString("booking_date");
                int flightId = rs.getInt("fid");
                String origin = rs.getString("origin_city");
                String dest = rs.getString("dest_city");
                int depDelay = rs.getInt("departure_delay");
                int arrDelay = rs.getInt("arrival_delay");

                System.out.println("Booking Date: " + bookingDate +
                        ", Flight ID: " + flightId +
                        ", From: " + origin + " To: " + dest +
                        ", Departure Delay: " + depDelay + " minutes, Arrival Delay: " + arrDelay + " minutes");

                hasBookings = true;
            }

            if (!hasBookings) {
                System.out.println("Khong tim thay chuyen bay.");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        try (Connection conn = DBConnection.getConnection()) {
            if (conn != null) {
                viewBookings(conn);
            } else {
                System.out.println("Co loi vui long thu lai!");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
