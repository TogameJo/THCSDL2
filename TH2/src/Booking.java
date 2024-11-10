package src;

import java.sql.*;
import java.util.Scanner;

public class Booking {
    public static void bookFlight(Connection conn) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Nhap ma khach hang: ");
        String customerId = scanner.nextLine();
        System.out.print("Nhap ma chuyen bay: ");
        int flightId = scanner.nextInt();
        scanner.nextLine();
        System.out.print("Nhap ngay bay (DD-MM-YYYY): ");
        String bookingDate = scanner.nextLine();

        // Truy vấn thông tin chuyến bay từ bảng Flights
        String fquery = "SELECT f.origin_city, f.dest_city FROM Flights f WHERE f.fid = ?";

        // Truy vấn để chèn đặt chỗ vào bảng Bookings
        String bquery = "INSERT INTO Bookings (customer_id, flight_id, booking_date) VALUES (?, ?, ?)";

        try (PreparedStatement pstmt1 = conn.prepareStatement(fquery)) {
            pstmt1.setInt(1, flightId);  // ma chuyen bay
            ResultSet rs = pstmt1.executeQuery();  // truy van ma chuyn bay

            if (rs.next()) {
                //lay tt tu res
                String originCity = rs.getString("origin_city");
                String destCity = rs.getString("dest_city");


                try (PreparedStatement pstmt2 = conn.prepareStatement(bquery)) {
                    pstmt2.setString(1, customerId);  // them ma khach hang
                    pstmt2.setInt(2, flightId);  // them ma chuyen bay
                    pstmt2.setString(3, bookingDate);  // them ngay bay
                    pstmt2.executeUpdate();  //insert

                    System.out.println("Dat lich thanh cong!");
                    System.out.println("So hieu chuyen bay cua ban la: " + flightId + ", Ngay bay chinh thuc: " + bookingDate);
                    System.out.println("Tu: " + originCity + ", Den: " + destCity);
                }
            } else {
                System.out.println("Khong ton tai chuyen bay.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        try (Connection conn = DBConnection.getConnection()) {
            if (conn != null) {
                bookFlight(conn);
            } else {
                System.out.println("Co loi vui long thu lai sau!");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
