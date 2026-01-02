package com.mycompany.qltb;

import java.awt.*;
import java.util.List;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class qltb_view extends JPanel {

    // ===== COMPONENT PUBLIC =====
    public JList<dmtb_thuoctinh> listDanhMuc;
    public DefaultListModel<dmtb_thuoctinh> listModelDm = new DefaultListModel<>();

    public JButton btnNewDm, btnUpdateDm, btnRemoveDm;
    public JTable tableSp;
    public DefaultTableModel tableModelSp;
    public JComboBox<dmtb_thuoctinh> cboCategory;
    public JComboBox<trangthai> cbotrangthai;
    public JTextField txtId, txtName, txtSoLuongTot, txtSoLuongHong;
    public JButton btnthem, btnsua, btnxoa;

    public qltb_view() {
        setLayout(new BorderLayout());

        // ===== PANEL TRÁI =====
        JPanel left = new JPanel(new BorderLayout());
        left.setPreferredSize(new Dimension(250, 0));
        left.setBorder(BorderFactory.createTitledBorder("Danh mục thiết bị"));

        listDanhMuc = new JList<>(listModelDm);
        left.add(new JScrollPane(listDanhMuc), BorderLayout.CENTER);

        JPanel pbtn = new JPanel();
        btnNewDm = new JButton("New");
        btnUpdateDm = new JButton("Update");
        btnRemoveDm = new JButton("Remove");

        pbtn.add(btnNewDm);
        pbtn.add(btnUpdateDm);
        pbtn.add(btnRemoveDm);
        left.add(pbtn, BorderLayout.SOUTH);

        add(left, BorderLayout.WEST);

        // ===== PANEL PHẢI =====
        JPanel right = new JPanel(new BorderLayout());
        right.setBorder(BorderFactory.createTitledBorder("Thông tin chi tiết"));

        tableModelSp = new DefaultTableModel(
            new String[]{"Mã TB", "Tên TB", "Danh mục", "Trạng thái", "SL Tốt", "SL Hỏng", "Tổng"}, 0
        ) {
            public boolean isCellEditable(int r, int c) { return false; }
        };

        tableSp = new JTable(tableModelSp);
        right.add(new JScrollPane(tableSp), BorderLayout.NORTH);

        JPanel form = new JPanel(new GridLayout(6, 2, 5, 5));
        cboCategory = new JComboBox<>();
        cbotrangthai = new JComboBox<>();
        txtId = new JTextField();
        txtName = new JTextField();
        txtSoLuongTot = new JTextField();
        txtSoLuongHong = new JTextField();

        form.add(new JLabel("Danh mục:")); form.add(cboCategory);
        form.add(new JLabel("Mã TB:")); form.add(txtId);
        form.add(new JLabel("Tên TB:")); form.add(txtName);
        form.add(new JLabel("SL Tốt:")); form.add(txtSoLuongTot);
        form.add(new JLabel("SL Hỏng:")); form.add(txtSoLuongHong);
        form.add(new JLabel("Trạng thái:")); form.add(cbotrangthai);

        right.add(form, BorderLayout.CENTER);

        JPanel pbtn2 = new JPanel();
        btnthem = new JButton("Thêm");
        btnsua = new JButton("Sửa");
        btnxoa = new JButton("Xóa");
        

        pbtn2.add(btnthem);
        pbtn2.add(btnsua);
        pbtn2.add(btnxoa);

        right.add(pbtn2, BorderLayout.SOUTH);
        add(right, BorderLayout.CENTER);
    }
}
