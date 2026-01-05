/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.qltb;

import java.util.List;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JOptionPane;

/**
 *
 * @author LanAnh
 */
public class hs_dieukhien {
    public hs_view view;
    public String maLopDangChon = null;
    
    // Biến cờ quan trọng để chặn vòng lặp sự kiện
    private boolean isSyncing = false; 

    public hs_dieukhien(hs_view view) {
        this.view = view;
        loadl();   // Load lớp vào List và Combo
        loadhs();  // Load toàn bộ học sinh (Toàn trường)
        click();   // Cài đặt sự kiện chọn
        nutbam();  // Cài đặt sự kiện nút
    }

    public void loadl() {
        qll_dao daol = new qll_dao();
        view.cbolop.removeAllItems();
        view.modelLop.clear();
        List<Lop> dsl = daol.getAllLop();
        for (Lop l : dsl) {
            view.modelLop.addElement(l);
            view.cbolop.addItem(l);
        }
    }

    public void loadhs() {
        hs_dao daohs = new hs_dao();
        view.modelHocSinh.clear();
        List<hs> dss = daohs.getAll(); // Đảm bảo hàm này SELECT * FROM hocsinh
        for (hs h : dss) {
            view.modelHocSinh.addElement(h);
        }
    }

    public void click() {
        
            view.lstHocSinh.addListSelectionListener(e -> {

               if (!e.getValueIsAdjusting()) {
            hs h = view.lstHocSinh.getSelectedValue();
            if (h == null) return;

            view.txtmahs.setEditable(false);
            view.txtmahs.setText(h.getMahs());
            view.txttenhs.setText(h.getTenhs());

            String maLopCuaHS = h.getMalop();

            for (int i = 0; i < view.cbolop.getItemCount(); i++) {
                Lop l = view.cbolop.getItemAt(i);
                if (l.getMaLop().equals(maLopCuaHS)) {
                    view.cbolop.setSelectedIndex(i);
                    break;
                }
            }
        }

    });

    // Sự kiện khi click vào JList Lớp để lọc
    view.lstLop.addListSelectionListener(e -> {
        if (e.getValueIsAdjusting() || isSyncing) return;

    Lop l = view.lstLop.getSelectedValue();
    loadHocSinhTheoLop(l);
        
    });
}
    
    public void loadHocSinhTheoLop(Lop l) {
    if (l == null) return;

    maLopDangChon = l.getMaLop();

    // Load lại danh sách học sinh theo lớp
    view.modelHocSinh.clear();
    new hs_dao()
            .laytheolop(maLopDangChon)
            .forEach(view.modelHocSinh::addElement);

    // Reset form nhập liệu
    view.txtmahs.setText("");
    view.txttenhs.setText("");
    view.cbolop.setSelectedItem(l); // đồng bộ combobox
}
    public void reloadHocSinhSauThaoTac() {
    view.modelHocSinh.clear();

    hs_dao dao = new hs_dao();

    // Nếu đang chọn lớp → load theo lớp
    if (maLopDangChon != null) {
        dao.laytheolop(maLopDangChon)
           .forEach(view.modelHocSinh::addElement);
    } 
    // Ngược lại → load toàn trường
    else {
        dao.getAll()
           .forEach(view.modelHocSinh::addElement);
    }
}


    public void nutbam() {
        // Nút làm sạch: Trở về trạng thái hiện Toàn trường
        view.btnlamsach.addActionListener(e -> {
            view.txtmahs.setEditable(true);
            view.txtmahs.setText("");
            view.txttenhs.setText("");
            view.cbolop.setSelectedIndex(-1);
            view.lstLop.clearSelection();
             maLopDangChon = null; // ⭐ CỰC KỲ QUAN TRỌNG
            loadhs(); // Hiển thị lại học sinh toàn trường
            loadl();
            
        });

        // Nút sửa (Cần cập nhật cả mã lớp nếu người dùng chọn combo khác)
        view.btnsua.addActionListener(e -> {
            hs h = view.lstHocSinh.getSelectedValue();
            if (h == null) {
                JOptionPane.showMessageDialog(view, "Chưa chọn học sinh!");
                return;
            }

            Lop l = (Lop) view.cbolop.getSelectedItem();
            h.setTenhs(view.txttenhs.getText().trim());
            h.setMalop(l.getMaLop());

            if (new hs_dao().update(h)) {
                JOptionPane.showMessageDialog(view, "Sửa thành công!");
                reloadHocSinhSauThaoTac(); // Load lại để cập nhật danh sách
            }
        });


    
    view.btnthem.addActionListener(e -> {
        hs_dao  daohs= new hs_dao();
            String ma = view.txtmahs.getText().trim();
            String ten = view.txttenhs.getText().trim();
            Lop l = (Lop) view.cbolop.getSelectedItem();

            if (ma.isEmpty() || ten.isEmpty()) {
                JOptionPane.showMessageDialog(view,
                        "Không được để trống dữ liệu!",
                        "Cảnh báo",
                        JOptionPane.WARNING_MESSAGE);
                return;
            }
               if (daohs.isExist(ma)) {
            JOptionPane.showMessageDialog(view, "Mã học sinh đã tồn tại!");
            return;
        }

            hs s=new hs();
            s.setMahs(ma);
            s.setTenhs(ten);
            s.setMalop(l.getMaLop());
               
            

            if (daohs.insert(s)) {
                JOptionPane.showMessageDialog(view, "Thêm danh mục thành công!");

                
            } else {
                JOptionPane.showMessageDialog(view,
                        "Thêm danh mục thất bại!",
                        "Lỗi",
                        JOptionPane.ERROR_MESSAGE);
            }
            reloadHocSinhSauThaoTac();
        });
        
        view.btnxoa.addActionListener(e -> {
             hs h=new hs();
               hs_dao  daohs= new hs_dao();
                    h = view.lstHocSinh.getSelectedValue();
                if (h == null) {
                    JOptionPane.showMessageDialog(view, "Chon sinh vien!");
                    return;
                }

                int kq = JOptionPane.showConfirmDialog(
                    view,
                    "Ban co muon xoa sinh vien nay?",
                    "Xac nhan",
                    JOptionPane.YES_NO_OPTION
                );

                if (kq == JOptionPane.YES_OPTION) {
                    daohs.delete(h.getMahs());

                    view.modelHocSinh.clear();
                   reloadHocSinhSauThaoTac();
                }
            });
    
    
    }
    
    
    
}