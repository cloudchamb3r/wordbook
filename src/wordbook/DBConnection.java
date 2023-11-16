package wordbook;

import java.sql.Connection;
import java.sql.DriverManager;

import javax.swing.JOptionPane;

public class DBConnection {
    private Connection conn = null;

    public Connection getConnection() {
        if (conn != null) {
            return conn;
        }
        try {
            Class.forName("org.sqlite.JDBC");
            conn = DriverManager.getConnection("jdbc:sqlite:wordbook.db");
            return conn;
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "GetConnection Error", JOptionPane.ERROR_MESSAGE);
            return null;
        }
    }
}
