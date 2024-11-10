package src;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

//ket noi database
public class DBConnection {
    private static final String URL = "jdbc:sqlite:C:\\Users\\Lenovo\\Documents\\Study\\CSDL\\TH2\\db\\database.db";

    public static Connection getConnection() {
        try {
            return DriverManager.getConnection(URL);
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

   /* public static void main(String[] args) {
        try (Connection conn = getConnection()) {
            if (conn != null) {
                System.out.println("success");
            }
        } catch (SQLException e) {
            System.out.println("error!");
            e.printStackTrace();
        }
    } */
}
