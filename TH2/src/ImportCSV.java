package src;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvException;

import java.io.FileReader;
import java.io.IOException;
import java.sql.*;
import java.util.List;

public class ImportCSV {

    //import vao bang Carriers
    public static void importCarriers(String csvFilePath) {
        String sql = "INSERT OR IGNORE INTO Carriers (cid, name) VALUES (?, ?)";  //neu da ton tai khoa chinh thi bo qua

        try (CSVReader csvReader = new CSVReader(new FileReader(csvFilePath))) {
            List<String[]> allRows = csvReader.readAll(); //doc du lieu chia thanh mang

            try (Connection conn = DBConnection.getConnection()) {  //ket noi database
                // duyet qua tung dong va tham vao dtb
                for (String[] row : allRows) {
                    try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
                        pstmt.setString(1, row[0]);
                        pstmt.setString(2, row[1]);
                        pstmt.executeUpdate(); // update SQL
                    }
                }
            }
            System.out.println("Carriers them thanh cong");
        } catch (IOException | CsvException | SQLException e) { //neu co loi nem ra ngoai le va in loi
            e.printStackTrace();
        }
    }

    // import vao bang Months
    public static void importMonths(String csvFilePath) {
        String sql = "INSERT OR IGNORE INTO Months (mid, month) VALUES (?, ?)";

        try (CSVReader csvReader = new CSVReader(new FileReader(csvFilePath))) {
            List<String[]> allRows = csvReader.readAll(); //doc du lieu chia thanh mang

            try (Connection conn = DBConnection.getConnection()) {
                // duyet qua tung dong
                for (String[] row : allRows) {
                    try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
                        pstmt.setInt(1, Integer.parseInt(row[0]));
                        pstmt.setString(2, row[1]);
                        pstmt.executeUpdate(); //update sql
                    }
                }
            }
            System.out.println("Months them thanh cong");
        } catch (IOException | CsvException | SQLException e) {  //ngoai le
            e.printStackTrace();
        }
    }

    //import Weekdays
    public static void importWeekdays(String csvFilePath) {
        String sql = "INSERT OR IGNORE INTO Weekdays (did, day_of_week) VALUES (?, ?)";

        try (CSVReader csvReader = new CSVReader(new FileReader(csvFilePath))) {
            List<String[]> allRows = csvReader.readAll(); //doc du lieu

            try (Connection conn = DBConnection.getConnection()) {
                // duyet tung dong
                for (String[] row : allRows) {
                    try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
                        pstmt.setInt(1, Integer.parseInt(row[0]));
                        pstmt.setString(2, row[1]);
                        pstmt.executeUpdate();
                    }
                }
            }
            System.out.println("Weekdays them thanh cong");
        } catch (IOException | CsvException | SQLException e) {
            e.printStackTrace();
        }
    }
    //ham xu ly ngoai le Numberformatexception
    private static int parseIntSafe(String value) {
        if (value == null || value.isEmpty()) {
            return 0;
        }
        try {
            return Integer.parseInt(value);
        } catch (NumberFormatException e) {
            return 0;
        }
    }

    // import Flights
    public static void importFlights(String csvFilePath) {
        String sql = "INSERT OR IGNORE INTO Flights (fid, year, month_id, day_of_month, day_of_week_id, carrier_id, flight_num, origin_city, origin_state, dest_city, dest_state, departure_delay, taxi_out, arrival_delay, cancled, actual_time, distance) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (CSVReader csvReader = new CSVReader(new FileReader(csvFilePath))) {
            List<String[]> allRows = csvReader.readAll(); //doc du lieu chia thanh mang

            try (Connection conn = DBConnection.getConnection()) {
                for (String[] row : allRows) {
                    try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
                        pstmt.setInt(1, parseIntSafe(row[0]));
                        pstmt.setInt(2, parseIntSafe(row[1]));
                        pstmt.setInt(3, parseIntSafe(row[2]));
                        pstmt.setInt(4, parseIntSafe(row[3]));
                        pstmt.setInt(5, parseIntSafe(row[4]));
                        pstmt.setString(6, row[5]);
                        pstmt.setInt(7, parseIntSafe(row[6]));
                        pstmt.setString(8, row[7]);
                        pstmt.setString(9, row[8]);
                        pstmt.setString(10, row[9]);
                        pstmt.setString(11, row[10]);
                        pstmt.setInt(12, parseIntSafe(row[11]));
                        pstmt.setInt(13, parseIntSafe(row[12]));
                        pstmt.setInt(14, parseIntSafe(row[13]));
                        pstmt.setInt(15, parseIntSafe(row[14]));
                        pstmt.setInt(16, parseIntSafe(row[15]));
                        pstmt.setInt(17, parseIntSafe(row[16]));
                        pstmt.executeUpdate();
                    }
                }
            }
            System.out.println("Flights them thanh cong");
        } catch (IOException | CsvException | SQLException e) {
            e.printStackTrace();
        }
    }

//ham main de them du lieu vao bang
    public static void main(String[] args) {
        String carriersCsvFile = "C:\\Users\\Lenovo\\Documents\\Study\\CSDL\\TH2\\resources\\data\\carriers.csv";
        String monthsCsvFile = "C:\\Users\\Lenovo\\Documents\\Study\\CSDL\\TH2\\resources\\data\\months.csv";
        String weekdaysCsvFile = "C:\\Users\\Lenovo\\Documents\\Study\\CSDL\\TH2\\resources\\data\\weekdays.csv";
        String flightsCsvFile = "C:\\Users\\Lenovo\\Documents\\Study\\CSDL\\TH2\\resources\\data\\flights-small.csv";

        importCarriers(carriersCsvFile);
        importMonths(monthsCsvFile);
        importWeekdays(weekdaysCsvFile);
        importFlights(flightsCsvFile);
    }
}
