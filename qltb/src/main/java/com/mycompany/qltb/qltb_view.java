package com.mycompany.qltb;

import java.awt.*;
import java.util.List;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class qltb_view extends JPanel {

    // ===== đối tượng giao diện COMPONENT PUBLIC =====
    public JList<dmtb_thuoctinh> listDanhMuc;
    public DefaultListModel<dmtb_thuoctinh> listModelDm = new DefaultListModel<>();

    public JButton btnNewDm, btnUpdateDm, btnRemoveDm;
    public JTable tableSp;
    public DefaultTableModel tableModelSp;
    public JComboBox<dmtb_thuoctinh> cboCategory;
    public JComboBox<trangthai> cbotrangthai;
    public JTextField txtId, txtName, txtSoLuongTot, txtSoLuongHong;
    public JButton btnthem, btnsua, btnxoa,btntk,btnlammoi;

    public qltb_view() {
        setLayout(new BorderLayout());

        // ===== PANEL TRÁI =====
        JPanel left = new JPanel(new BorderLayout());
        left.setPreferredSize(new Dimension(300, 0));
        left.setBorder(BorderFactory.createTitledBorder(null, "Danh mục thiết bị", 0, 0, new Font("Tahoma", Font.BOLD, 14)));

        listDanhMuc = new JList<>(listModelDm);
        setBigFont(listDanhMuc);
        left.add(new JScrollPane(listDanhMuc), BorderLayout.CENTER);

        JPanel pbtn = new JPanel();
        btnNewDm = new JButton("New");setBigFont(btnNewDm);
        btnUpdateDm = new JButton("Update");setBigFont(btnUpdateDm);
        btnRemoveDm = new JButton("Remove");setBigFont(btnRemoveDm);

        pbtn.add(btnNewDm);
        pbtn.add(btnUpdateDm);
        pbtn.add(btnRemoveDm);
        left.add(pbtn, BorderLayout.SOUTH);

        add(left, BorderLayout.WEST);

        // ===== PANEL PHẢI =====
        JPanel right = new JPanel(new BorderLayout());
        right.setBorder(BorderFactory.createTitledBorder(null, "Chi tiết thiết bị", 0, 0, new Font("Tahoma", Font.BOLD, 14)));

        tableModelSp = new DefaultTableModel(
            new String[]{"Mã TB", "Tên TB", "Danh mục", "Trạng thái", "SL Tốt", "SL Hỏng", "Tổng"}, 0
        ) {
            public boolean isCellEditable(int r, int c) { return false; }
        };

        tableSp = new JTable(tableModelSp);setBigFont(tableSp);
        
       tableSp.setRowHeight(30); // Tăng chiều cao hàng cho dễ nhìn
        JScrollPane scrollTable = new JScrollPane(tableSp);
        scrollTable.setPreferredSize(new Dimension(0, 250)); // Cho bảng chiếm khoảng 300px chiều cao
        right.add(scrollTable, BorderLayout.NORTH);
        
        JPanel form = new JPanel(new GridLayout(6, 2, 5, 5));
        cboCategory = new JComboBox<>();setBigFont(cboCategory);
        cbotrangthai = new JComboBox<>();setBigFont(cbotrangthai);
        txtId = new JTextField();setBigFont(txtId);
        txtName = new JTextField();setBigFont(txtName);
        txtSoLuongTot = new JTextField();setBigFont(txtSoLuongTot);
        txtSoLuongHong = new JTextField();setBigFont(txtSoLuongHong);

        form.add(new JLabel("Danh mục:")); form.add(cboCategory);
        form.add(new JLabel("Mã TB:")); form.add(txtId);
        form.add(new JLabel("Tên TB:")); form.add(txtName);
        form.add(new JLabel("SL Tốt:")); form.add(txtSoLuongTot);
        form.add(new JLabel("SL Hỏng:")); form.add(txtSoLuongHong);
        form.add(new JLabel("Trạng thái:")); form.add(cbotrangthai);

        right.add(form, BorderLayout.CENTER);

        JPanel pbtn2 = new JPanel();
        btnthem = new JButton("Thêm");setBigFont(btnthem);
        btnsua = new JButton("Sửa");setBigFont(btnsua);
        btnxoa = new JButton("Xóa");setBigFont(btnxoa);
        btntk = new JButton("Tìm kiếm");setBigFont(btntk);
        btnlammoi = new JButton("Làm mới");setBigFont(btnlammoi);

        pbtn2.add(btnthem);
        pbtn2.add(btnsua);
        pbtn2.add(btnxoa);
        pbtn2.add(btntk);
        pbtn2.add(btnlammoi);

        right.add(pbtn2, BorderLayout.SOUTH);
        add(right, BorderLayout.CENTER);
    }
    private void setBigFont(java.awt.Component comp) {
    comp.setFont(new java.awt.Font("Tahoma", java.awt.Font.PLAIN, 16)); // Chữ cỡ 18
}   
}
