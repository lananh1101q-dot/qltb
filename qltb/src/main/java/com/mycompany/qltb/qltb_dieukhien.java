package com.mycompany.qltb;

import java.util.List;
import javax.swing.*;

public class qltb_dieukhien {

    private qltb_view view;
    private String maLoaiDangChon = null;


    public qltb_dieukhien(qltb_view view) {
        this.view = view;
        init();
        loadDanhMuc();
        loadTrangThai();
        loadThietBi();
        nutchodm();
        
    }

    public void init() {
        

        // Click danh mục
        view.listDanhMuc.addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                dmtb_thuoctinh dm = view.listDanhMuc.getSelectedValue();
                if (dm != null) {
                    loadThietBiByLoai(dm.getMaloai());
                }
            }
        });

        // Click bảng thiết bị
        view.tableSp.getSelectionModel().addListSelectionListener(e -> {
            int row = view.tableSp.getSelectedRow();
            if (row >= 0) hienthi_jtext(row);
        });

        // Thêm mới
        view.btnthem.addActionListener(e -> themTB());
        view.btnlammoi.addActionListener(e -> clearForm());
        view.btnsua.addActionListener(e -> suaTB());
        view.btnxoa.addActionListener(e -> xoaTB());
        view.btntk.addActionListener(e -> timThietBi()
        );
        
    }

    // ===== LOAD DANH MỤC =====
    public void loadDanhMuc() {
        view.listModelDm.clear();
        view.cboCategory.removeAllItems();

        dmtb_dao dao = new dmtb_dao();
        List<dmtb_thuoctinh> ds = dao.getAll();

        for (dmtb_thuoctinh dm : ds) {
            view.listModelDm.addElement(dm);
            view.cboCategory.addItem(dm);
        }
    }

    public void loadTrangThai() {
        view.cbotrangthai.removeAllItems();
        trangthai_dao dao = new trangthai_dao();
        for (trangthai tt : dao.getTrangThaiQuanLy()) {
            view.cbotrangthai.addItem(tt);
        }
    }

    public void loadThietBi() {
        view.tableModelSp.setRowCount(0);
        qltb_dao dao = new qltb_dao();

        for (qltb_thuoctinh tb : dao.getAll()) {
            view.tableModelSp.addRow(new Object[]{
                tb.getMaTB(),
                tb.getTenTB(),
                tb.gettenLoai(),
                tb.gettenTrangThai(),
                tb.getSoLuongTot(),
                tb.getSoLuongHong(),
                tb.getTongSoLuong()
            });
        }
    }

    public void loadThietBiByLoai(String maLoai) {
        view.tableModelSp.setRowCount(0);
        qltb_dao dao = new qltb_dao();

        for (qltb_thuoctinh tb : dao.getByLoai(maLoai)) {
            view.tableModelSp.addRow(new Object[]{
                tb.getMaTB(),
                tb.getTenTB(),
                tb.gettenLoai(),
                tb.gettenTrangThai(),
                tb.getSoLuongTot(),
                tb.getSoLuongHong(),
                tb.getTongSoLuong()
            });
        }
    }

    public void hienthi_jtext(int row) {
        view.txtId.setText(view.tableSp.getValueAt(row, 0).toString());
        view.txtName.setText(view.tableSp.getValueAt(row, 1).toString());
        view.cboCategory.setSelectedItem(view.tableSp.getValueAt(row, 2).toString());
        view.txtSoLuongTot.setText(view.tableSp.getValueAt(row, 3).toString());
        view.txtSoLuongHong.setText(view.tableSp.getValueAt(row, 4).toString());
        view.cbotrangthai.setSelectedItem(view.tableSp.getValueAt(row, 5).toString());
    }

    public void clearForm() {
        view.txtId.setText("");
        view.txtName.setText("");
        view.txtSoLuongTot.setText("");
        view.txtSoLuongHong.setText("");
        view.cboCategory.setSelectedIndex(-1);
        view.cbotrangthai.setSelectedIndex(-1);
        loadDanhMuc();
        loadThietBi();
    }
    public void nutchodm(){
        view.btnNewDm.addActionListener(e -> {                       
            new dmtb_view(this).setVisible(true); 
        });
        view.btnUpdateDm.addActionListener(e -> {                             // Sự kiện khi nhấn nút New
            dmtb_thuoctinh selected = view.listDanhMuc.getSelectedValue();
            if (selected == null) {
                JOptionPane.showMessageDialog(view, "Vui lòng chọn danh mục cần sửa!", "Thông báo", JOptionPane.WARNING_MESSAGE);
                return;
            }
            new dmtb_sua(this, selected);
        });
        view.btnRemoveDm.addActionListener(e -> {
            dmtb_thuoctinh selected = view.listDanhMuc.getSelectedValue();
            if (selected == null) {
                JOptionPane.showMessageDialog(view, "Vui lòng chọn danh mục cần xóa!", "Thông báo", JOptionPane.WARNING_MESSAGE);
                return;
            }

            int confirm = JOptionPane.showConfirmDialog(
                view,
                "Bạn có chắc chắn muốn xóa danh mục \"" + selected.getTenloai() + "\" không?\n(Các thiết bị thuộc danh mục này có thể bị ảnh hưởng)",
                "Xác nhận xóa",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE
            );

            if (confirm == JOptionPane.YES_OPTION) {
                dmtb_dao dao = new dmtb_dao();
                if (dao.delete(selected.getMaloai())) {  // hoặc selected.getId() tùy field của bạn
                    JOptionPane.showMessageDialog(view, "Xóa danh mục thành công!");
                    loadDanhMuc();  // Reload lại JList và ComboBox
                } else {
                    JOptionPane.showMessageDialog(view, "Xóa thất bại! (Có thể danh mục đang được sử dụng)", "Lỗi", JOptionPane.ERROR_MESSAGE);
                }
            }
       });



    }
    
        private void themTB() {
        String maTB = view.txtId.getText().trim();
        String tenTB = view.txtName.getText().trim();
        String slTotStr = view.txtSoLuongTot.getText().trim();
        String slHongStr = view.txtSoLuongHong.getText().trim();

        dmtb_thuoctinh loai = (dmtb_thuoctinh) view.cboCategory.getSelectedItem();
        trangthai tt = (trangthai) view.cbotrangthai.getSelectedItem();
        
        qltb_dao dao=new qltb_dao();

        if (maTB.isEmpty() || tenTB.isEmpty() || slTotStr.isEmpty() || slHongStr.isEmpty()
                || loai == null || tt == null) {
            JOptionPane.showMessageDialog(view, "Vui lòng nhập đầy đủ thông tin!");
            return;
        }

        int slTot, slHong;
        try {
            slTot = Integer.parseInt(slTotStr);
            slHong = Integer.parseInt(slHongStr);
            if (slTot < 0 || slHong < 0 || slTot + slHong == 0) {
                JOptionPane.showMessageDialog(view, "Số lượng không hợp lệ!");
                return;
            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(view, "Số lượng phải là số!");
            return;
        }

        if (dao.isExist(maTB)) {
            JOptionPane.showMessageDialog(view, "Mã thiết bị đã tồn tại!");
            return;
        }

        qltb_thuoctinh tb = new qltb_thuoctinh();
        tb.setMaTB(maTB);
        tb.setTenTB(tenTB);
        tb.setMaLoai(loai.getMaloai());
        tb.setTrangThai(tt.getMaTinhTrang());
        tb.setSoLuongTot(slTot);
        tb.setSoLuongHong(slHong);

        if (dao.insert(tb)) {
            JOptionPane.showMessageDialog(view, "Thêm thành công!");
           loadThietBi();
        } else {
            JOptionPane.showMessageDialog(view, "Thêm thất bại!");
        }
    }
            private void suaTB() {
                
        int row = view.tableSp.getSelectedRow();
        if (row < 0) {
            JOptionPane.showMessageDialog(view, "Chọn thiết bị cần sửa!");
            return;
        }

        qltb_thuoctinh tb = new qltb_thuoctinh();
        tb.setMaTB(view.txtId.getText());
        tb.setTenTB(view.txtName.getText());
        tb.setMaLoai(((dmtb_thuoctinh) view.cboCategory.getSelectedItem()).getMaloai());
        tb.setTrangThai(((trangthai) view.cbotrangthai.getSelectedItem()).getMaTinhTrang());
        tb.setSoLuongTot(Integer.parseInt(view.txtSoLuongTot.getText()));
        tb.setSoLuongHong(Integer.parseInt(view.txtSoLuongHong.getText()));
        qltb_dao dao=new qltb_dao();
        if (dao.update(tb)) {
            JOptionPane.showMessageDialog(view, "Cập nhật thành công!");
            loadThietBi();
        } else {
            JOptionPane.showMessageDialog(view, "Cập nhật thất bại!");
        }
    }

         private void xoaTB() {
        int row = view.tableSp.getSelectedRow();
        if (row < 0) {
            JOptionPane.showMessageDialog(view, "Chọn thiết bị cần xóa!");
            return;
        }

        String maTB = view.tableSp.getValueAt(row, 0).toString();

        int confirm = JOptionPane.showConfirmDialog(
                view,
                "Xóa thiết bị mã: " + maTB + " ?",
                "Xác nhận",
                JOptionPane.YES_NO_OPTION
        );
        qltb_dao dao=new qltb_dao();
        if (confirm == JOptionPane.YES_OPTION) {
            if (dao.delete(maTB)) {
                JOptionPane.showMessageDialog(view, "Xóa thành công!");
                loadThietBi();
                clearForm();
            }
        }
    }
         private void timThietBi() {
    String key = JOptionPane.showInputDialog(view, "Nhập mã hoặc tên thiết bị");

    // Người dùng bấm Cancel
    if (key == null) return;

    key = key.trim();
    if (key.isEmpty()) {
        JOptionPane.showMessageDialog(view, "Từ khóa không được rỗng!");
        return;
    }

    view.tableModelSp.setRowCount(0);
    qltb_dao dao = new qltb_dao();
    List<qltb_thuoctinh> ds;

    // Có chọn danh mục → tìm theo danh mục
    if (maLoaiDangChon != null) {
        ds = dao.searchma(key, maLoaiDangChon);
    } 
    // Không chọn danh mục → tìm toàn bộ
    else {
        ds = dao.search(key);
    }

    if (ds.isEmpty()) {
        JOptionPane.showMessageDialog(view, "Không tìm thấy thiết bị!");
        return;
    }

    for (qltb_thuoctinh tb : ds) {
        view.tableModelSp.addRow(new Object[]{
            tb.getMaTB(),
            tb.getTenTB(),
            tb.gettenLoai(),
            tb.gettenTrangThai(),
            tb.getSoLuongTot(),
            tb.getSoLuongHong(),
            tb.getTongSoLuong()
        });
    }
}



    
}
