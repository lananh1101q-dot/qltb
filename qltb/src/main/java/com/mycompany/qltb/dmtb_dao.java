package com.mycompany.qltb;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 * DAO - Data Access Object
 * Xử lý dữ liệu bảng loaitb
 */
public class dmtb_dao {
   
        public boolean isExist(String maTB) {
        String sql = "SELECT maloai FROM loaitb WHERE maloai = ?";
        try (Connection c = new db().getConnection(); PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setString(1, maTB);
            ResultSet rs = ps.executeQuery();
            return rs.next();
        } catch (Exception e) { e.printStackTrace(); }
        return false;
    }

    // =========================
    // THÊM LOẠI THIẾT BỊ
    // =========================
    public boolean insert(dmtb_thuoctinh ltb) {
        String sql = "INSERT INTO loaitb VALUES (?, ?)";
        try {
            db database = new db();
            Connection c = database.getConnection();
            PreparedStatement ps = c.prepareStatement(sql);

            ps.setString(1, ltb.getMaloai());
            ps.setString(2, ltb.getTenloai());

            return ps.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    // =========================
    // SỬA LOẠI THIẾT BỊ
    // =========================
    public boolean update(dmtb_thuoctinh ltb) {
        String sql = "UPDATE loaitb SET tenloai=? WHERE maloai=?";
        try {
            db database = new db();
            Connection c = database.getConnection();
            PreparedStatement ps = c.prepareStatement(sql);

            ps.setString(1, ltb.getTenloai());
            ps.setString(2, ltb.getMaloai());

            return ps.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    // =========================
    // XÓA LOẠI THIẾT BỊ
    // =========================
    public boolean delete(String maloai) {
        String sql = "DELETE FROM loaitb WHERE maloai=?";
        try {
            db database = new db();
            Connection c = database.getConnection();
            PreparedStatement ps = c.prepareStatement(sql);

            ps.setString(1, maloai);

            return ps.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    // =========================
    // LẤY DANH SÁCH
    // =========================
    public List<dmtb_thuoctinh> getAll() {
        List<dmtb_thuoctinh> list = new ArrayList<>();
        String sql = "SELECT * FROM loaitb order by tenloai asc";

        try {
            db database = new db();
            Connection c = database.getConnection();
            Statement st = c.createStatement();
            ResultSet rs = st.executeQuery(sql);

            while (rs.next()) {
                list.add(new dmtb_thuoctinh(
                        rs.getString("maloai"),
                        rs.getString("tenloai")
                ));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    // =========================
    // TÌM THEO MÃ
    // =========================
    


}
 

