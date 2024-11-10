package src;

import java.sql.*;
import java.util.Scanner;

public class Main {
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        while (true) {
            System.out.println("Menu:");
            System.out.println("1. Tìm chuyến bay");
            System.out.println("2. Đặt chuyến bay");
            System.out.println("3. Xem đặt chỗ");
            System.out.println("4. Thoát");
            System.out.print("Chọn chức năng: ");
            int choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    searchFlights();
                    break;
                case 2:
                    bookFlight();
                    break;
                case 3:
                    viewBookings();
                    break;
                case 4:
                    System.out.println("Thoát chương trình.");
                    return;
                default:
                    System.out.println("Lựa chọn không hợp lệ!");
            }
        }
    }

    // Tìm chuyến bay theo thành phố xuất phát và điểm đến
    private static void searchFlights() {
        System.out.print("Nhập thành phố xuất phát: ");
        String originCity = scanner.next();
        System.out.print("Nhập thành phố điểm đến: ");
        String destCity = scanner.next();

        try (Connection conn = DBConnection.getConnection()) {
            String sql = "SELECT * FROM Flights WHERE origin_city = ? AND dest_city = ?";
            try (PreparedStatement ps = conn.prepareStatement(sql)) {
                ps.setString(1, originCity);
                ps.setString(2, destCity);
                ResultSet rs = ps.executeQuery();

                while (rs.next()) {
                    System.out.println("Flight ID: " + rs.getInt("fid") + ", Carrier: " + rs.getString("carrier_id") +
                            ", Flight Number: " + rs.getInt("flight_num"));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Đặt chuyến bay cho khách hàng
    private static void bookFlight() {
        System.out.print("Nhập mã khách hàng: ");
        int customerId = scanner.nextInt();
        System.out.print("Nhập mã chuyến bay: ");
        int flightId = scanner.nextInt();

        try (Connection conn = DBConnection.getConnection()) {
            String sql = "INSERT INTO Bookings (customer_id, flight_id, booking_date) VALUES (?, ?, CURDATE())";
            try (PreparedStatement ps = conn.prepareStatement(sql)) {
                ps.setInt(1, customerId);
                ps.setInt(2, flightId);
                int rowsAffected = ps.executeUpdate();
                if (rowsAffected > 0) {
                    System.out.println("Đặt chỗ thành công!");
                } else {
                    System.out.println("Đặt chỗ thất bại.");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Xem tất cả các đặt chỗ của một khách hàng
    private static void viewBookings() {
        System.out.print("Nhập mã khách hàng: ");
        int customerId = scanner.nextInt();

        try (Connection conn = DBConnection.getConnection()) {
            String sql = "SELECT * FROM Bookings WHERE customer_id = ?";
            try (PreparedStatement ps = conn.prepareStatement(sql)) {
                ps.setInt(1, customerId);
                ResultSet rs = ps.executeQuery();

                while (rs.next()) {
                    System.out.println("Booking ID: " + rs.getInt("booking_id") + ", Flight ID: " + rs.getInt("flight_id") +
                            ", Booking Date: " + rs.getDate("booking_date"));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

