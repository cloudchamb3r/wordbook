package wordbook.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import wordbook.DBConnection;
import wordbook.dto.CollectionDto;

public class CollectionDao {
    public List<CollectionDto> getAll() {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        List<CollectionDto> ret = null;
        try {
            conn = new DBConnection().getConnection();
            String sql = "select id, name from word_collection order by id asc";
            pstmt = conn.prepareStatement(sql);
            rs = pstmt.executeQuery();
            ret = new ArrayList<>();
            while (rs.next()) {
                int id = rs.getInt("id");
                String name = rs.getString("name");
                ret.add(new CollectionDto(id, name));
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

    public boolean delete(int id) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        boolean ret = false;
        try {
            conn = new DBConnection().getConnection();
            var sql = "delete from word_collection where id = ?";
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

    public boolean rename(int id, String name) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        boolean ret = false;
        try {
            conn = new DBConnection().getConnection();
            var sql = "update word_collection set name = ? where id = ?";
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, name);
            pstmt.setInt(2, id);
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

    public int create(String name) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        int ret = -1;
        try {
            conn = new DBConnection().getConnection();
            var sql = "insert into word_collection (name) values (?)";
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, name);
            if (pstmt.executeUpdate() > 0) {
                var list = getAll();
                ret = list.get(list.size() - 1).getId();
            }
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
}
