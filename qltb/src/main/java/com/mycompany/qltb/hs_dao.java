/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.qltb;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author LanAnh
 */
public class hs_dao {
        public boolean isExist(String maTB) {
        String sql = "SELECT mahs FROM hocsinh WHERE mahs = ?";
        try (Connection c = new db().getConnection(); PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setString(1, maTB);
            ResultSet rs = ps.executeQuery();
            return rs.next();
        } catch (Exception e) { e.printStackTrace(); }
        return false;
    }
    public boolean insert(hs h) {
        String sql = "INSERT INTO hocsinh VALUES (?,?,?)";
        try {
            db database = new db();
            Connection c = database.getConnection();
            PreparedStatement ps = c.prepareStatement(sql);

            ps.setString(1, h.getMahs());
            
            ps.setString(2, h.getTenhs());
            ps.setString(3, h.getMalop());

            return ps.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    // =========================
    // SỬA LOẠI THIẾT BỊ
    // =========================
    public boolean update(hs hs) {
        String sql = "UPDATE hocsinh SET tenhs=?,malop=? WHERE mahs=?";
        try {
            db database = new db();
            Connection c = database.getConnection();
            PreparedStatement ps = c.prepareStatement(sql);

            ps.setString(1, hs.getTenhs());
            ps.setString(2, hs.getMalop());
            ps.setString(3, hs.getMahs());

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
        String sql = "DELETE FROM hocsinh WHERE mahs=?";
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
           // Trong hàm getAll()
public List<hs> getAll() {
    List<hs> list = new ArrayList<>();
    // Lấy h.malop thay vì l.tenlop để phục vụ logic tìm kiếm trong ComboBox
    String sql = "SELECT mahs, tenhs, malop FROM hocsinh order by tenhs asc"; 

    try {
        db database = new db();
        Connection c = database.getConnection();
        Statement st = c.createStatement();
        ResultSet rs = st.executeQuery(sql);

        while (rs.next()) {
            list.add(new hs(
                rs.getString("mahs"),
                rs.getString("tenhs"),
                rs.getString("malop") // Đây phải là MÃ (vd: L01), không phải TÊN (vd: 10A1)
            ));
        }
    } catch (Exception e) {
        e.printStackTrace();
    }
    return list;
}
            
            public List<hs> laytheolop(String malop) {
        List<hs> list = new ArrayList<>();
        String sql = "SELECT s.mahs, s.tenhs, l.tenlop " +
                     "FROM  hocsinh s " +
                     "JOIN lop l ON s.malop = l.malop " +                   
                     "WHERE s.malop = ? order by s.tenhs asc ";

        try (Connection c = new db().getConnection(); 
                PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setString(1, malop);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                hs tb = new hs();
                tb.setMahs(rs.getString("mahs"));
                tb.setTenhs(rs.getString("tenhs"));
                tb.setMalop(rs.getString("tenlop"));
             
                list.add(tb);
            }
        } catch (Exception e) { e.printStackTrace(); }
        return list;
    }
         
          

}
