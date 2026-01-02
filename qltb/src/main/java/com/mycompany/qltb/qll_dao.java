/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.qltb;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Admin
 */
public class qll_dao {

    // 1. Hàm kết nối CSDL (Giống file mẫu của bạn)
    private Connection getConnection() throws SQLException {
        return DriverManager.getConnection("jdbc:mysql://localhost:3306/qltb_thpt", "root", ""); 
    }

    // 2. Lấy danh sách tất cả các lớp
    public List<Lop> getAllLop() {
        List<Lop> list = new ArrayList<>();
        String sql = "SELECT * FROM lop"; // Giả sử tên bảng là 'lop'

        try (Connection conn = getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Lop lop = new Lop();
                lop.setMaLop(rs.getString("malop"));
                lop.setTenLop(rs.getString("tenlop"));
                lop.setGvcn(rs.getString("gv_chunhiem"));
                
                list.add(lop);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    // 3. Thêm lớp mới (INSERT)
    public boolean insert(Lop lop) {
        String sql = "INSERT INTO lop (malop, tenlop, gv_chunhiem) VALUES (?, ?, ?)";
        
        try (Connection conn = getConnection(); 
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, lop.getMaLop());
            pstmt.setString(2, lop.getTenLop());
            pstmt.setString(3, lop.getGvcn());
            
            return pstmt.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    // 4. Cập nhật thông tin lớp (UPDATE)
    public boolean update(Lop lop) {
        // Cập nhật Tên và GVCN dựa theo Mã Lớp
        String sql = "UPDATE lop SET tenlop = ?, gv_chunhiem = ? WHERE malop = ?";
        
        try (Connection conn = getConnection(); 
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, lop.getTenLop());
            pstmt.setString(2, lop.getGvcn());
            pstmt.setString(3, lop.getMaLop());
            
            return pstmt.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    // 5. Xóa lớp (DELETE)
    public boolean delete(String maLop) {
        String sql = "DELETE FROM lop WHERE malop = ?";
        
        try (Connection conn = getConnection(); 
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, maLop);
            
            return pstmt.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
            // Lưu ý: Nếu lớp này đã có học sinh hoặc phiếu mượn, CSDL có thể chặn xóa (Foreign Key)
        }
        return false;
    }

    // 6. Kiểm tra mã lớp đã tồn tại chưa (Validate)
    public boolean isExist(String maLop) {
        String sql = "SELECT count(*) FROM lop WHERE malop = ?";
        
        try (Connection conn = getConnection(); 
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, maLop);
            ResultSet rs = pstmt.executeQuery();
            
            if (rs.next()) {
                return rs.getInt(1) > 0;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
}