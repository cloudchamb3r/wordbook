package wordbook;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

import javax.swing.JOptionPane;

import wordbook.frame.WordbookFrame;

class Main {
    public static void main(String[] args) {
        Connection conn = null;
        Statement stmt = null;
        try {
            conn = new DBConnection().getConnection();
            stmt = conn.createStatement();

            stmt.executeUpdate(
                    "create table if not exists word_collection (" +
                            "id integer primary key autoincrement, " +
                            "name text not null)");

            stmt.executeUpdate(
                    "create table if not exists word (" +
                            "id integer primary key autoincrement, " +
                            "collection_id integer not null, " +
                            "word text not null, " +
                            "mean text not null, " +
                            "memorized boolean not null, " +
                            "foreign key (collection_id) references word_collection(id) on delete cascade)");

            new WordbookFrame();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "Main Error", JOptionPane.ERROR_MESSAGE);
        } finally {
            try {
                if (stmt != null)
                    stmt.close();
                if (conn != null)
                    conn.close();
            } catch (Exception e) {
            }
        }
    }
}