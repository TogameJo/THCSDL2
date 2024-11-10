package src;

import java.sql.*;
import java.util.Scanner;

public class Flight {

    public static void searchFlights(Connection conn) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Nhap diem bat dau: ");
        String originCity = scanner.nextLine();
        System.out.print("Nhap diem den: ");
        String destCity = scanner.nextLine();

        String query = "SELECT f.fid, f.year, w.day_of_week, f.month_id, c.name, f.origin_city, f.dest_city, f.departure_delay, f.arrival_delay " +
                "FROM Flights f , Weekdays w, Carriers c " + //join bang weekday de lay did, Carriers de lay ten hang bay
                "WHERE f.origin_city = ? AND f.dest_city = ? AND w.did = f.day_of_week_id AND c.cid = f.carrier_id";


        try (PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, originCity);
            pstmt.setString(2, destCity);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                int fid = rs.getInt("fid");
                String day = rs.getString("day_of_week");
                int year = rs.getInt("year");
                int month = rs.getInt("month_id");
                String carrier = rs.getString("name");
                String origin = rs.getString("origin_city");
                String dest = rs.getString("dest_city");
                int depDelay = rs.getInt("departure_delay");
                int arrDelay = rs.getInt("arrival_delay");

                System.out.println("Ma hieu chuyen bay: " + fid + ", Thoi gian: " +day+"/"+month+"/"+year + ", Ten hang bay: " + carrier+ ", Tu: " + origin + ", Den: " + dest +
                        ", Departure Delay: " + depDelay + " minutes, Arrival Delay: " + arrDelay + " minutes");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public static void main(String[] args) {
        try (Connection conn = DBConnection.getConnection()) {
            if (conn != null) {
                searchFlights(conn);
            } else {
                System.out.println("Co loi vui long thu lai!");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
