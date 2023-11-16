package wordbook.dao;

import java.util.ArrayList;
import java.util.List;

import wordbook.DBConnection;
import wordbook.dto.WordDto;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class WordDao {
    public List<WordDto> getAll(int collectionId) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        List<WordDto> ret = null;

        try {
            conn = new DBConnection().getConnection();
            var sql = "select id, word, mean, memorized from word where collection_id = ?";
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, collectionId);
            rs = pstmt.executeQuery();
            ret = new ArrayList<>();
            while (rs.next()) {
                int id = rs.getInt("id");
                String word = rs.getString("word");
                String mean = rs.getString("mean");
                boolean memorized = rs.getBoolean("memorized");
                ret.add(new WordDto(id, word, mean, memorized, collectionId));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (rs != null)
                    rs.close();
                if (pstmt != null)
                    pstmt.close();
                if (conn != null)
                    conn.close();
            } catch (Exception e) {
            }
        }
        return ret;
    }

    public List<WordDto> getAllNotMemorized(int collectionId) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        List<WordDto> ret = null;

        try {
            conn = new DBConnection().getConnection();
            var sql = "select id, word, mean, memorized from word where collection_id = ? and memorized = false";
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, collectionId);
            rs = pstmt.executeQuery();
            ret = new ArrayList<>();
            while (rs.next()) {
                int id = rs.getInt("id");
                String word = rs.getString("word");
                String mean = rs.getString("mean");
                boolean memorized = rs.getBoolean("memorized");
                ret.add(new WordDto(id, word, mean, memorized, collectionId));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (rs != null)
                    rs.close();
                if (pstmt != null)
                    pstmt.close();
                if (conn != null)
                    conn.close();
            } catch (Exception e) {
            }
        }
        return ret;
    }

    public boolean add(int collectionId, String word, String mean) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        boolean ret = false;
        try {
            conn = new DBConnection().getConnection();
            var sql = "insert into word (word, mean, memorized, collection_id) values (?, ?, ?, ?)";
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, word);
            pstmt.setString(2, mean);
            pstmt.setBoolean(3, false);
            pstmt.setInt(4, collectionId);
            ret = pstmt.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (pstmt != null)
                    pstmt.close();
                if (conn != null)
                    conn.close();
            } catch (Exception e) {
            }
        }
        return ret;
    }

    public boolean delete(int id) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        boolean ret = false;
        try {
            conn = new DBConnection().getConnection();
            var sql = "delete from word where id = ?";
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, id);
            ret = pstmt.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (pstmt != null)
                    pstmt.close();
                if (conn != null)
                    conn.close();
            } catch (Exception e) {
            }
        }
        return ret;
    }

    public void update(WordDto dto) {
        Connection conn = null;
        PreparedStatement pstmt = null;

        try {
            conn = new DBConnection().getConnection();
            var sql = "update word set memorized = true where id = ?";
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, dto.getId());
            pstmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (pstmt != null)
                    pstmt.close();
                if (conn != null)
                    conn.close();
            } catch (Exception e) {
            }
        }
    }
}
