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
    public boolean insert(hs hs) {
        String sql = "INSERT INTO hocsinh VALUES (?, ?,?)";
        try {
            db database = new db();
            Connection c = database.getConnection();
            PreparedStatement ps = c.prepareStatement(sql);

            ps.setString(1, hs.getMahs());
            ps.setString(3, hs.getMalop());
            ps.setString(2, hs.getTenhs());

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
            public List<hs> getAll() {
            List<hs> list = new ArrayList<>();

            String sql = "SELECT h.mahs, h.tenhs, l.tenlop "
                       + "FROM hocsinh h "
                       + "JOIN lop l ON h.malop = l.malop";

            try {
                db database = new db();
                Connection c = database.getConnection();
                Statement st = c.createStatement();
                ResultSet rs = st.executeQuery(sql);

                while (rs.next()) {
                    list.add(new hs(
                        rs.getString("mahs"),
                        rs.getString("tenhs"),
                        rs.getString("tenlop")
                    ));
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
            return list;
        }
            
            public List<hs> laytheolop(String malop) {
        List<hs> list = new ArrayList<>();
        String sql = "SELECT s.mahs, s.tenhs, l.tenlop" +
                     "FROM  hocsinh s " +
                     "JOIN lop l ON s.malop = l.malop " +                   
                     "WHERE s.malop = ?";

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
