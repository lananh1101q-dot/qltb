package com.mycompany.qltb;

import java.awt.*;
import javax.swing.*;
import javax.swing.border.*;
import javax.swing.DefaultListModel;

public class hs_view extends JPanel {

    // ===== MODEL =====
    public DefaultListModel<Lop> modelLop = new DefaultListModel<>();
   

    public DefaultListModel<hs> modelHocSinh = new DefaultListModel<>();

    // ===== COMPONENT =====
    JLabel lblTitle;

    JPanel pnlLop;
    public JList<Lop> lstLop;

    JPanel pnlDSHS;
    JLabel lblDSHS;
    public JList<hs> lstHocSinh;

    JPanel pnlForm;
    JLabel lblMaHS, lblTenHS, lblLop;
    public JTextField txtmahs, txttenhs;
    public JComboBox<Lop> cbolop;


    JPanel pnlButton;
    public JButton btnthem, btnsua, btnxoa, btnlamsach;

    public hs_view() {
        initUI();
    }

    private void initUI() {
        setLayout(new BorderLayout(10, 10));
      

        // ===== TITLE =====
        lblTitle = new JLabel("QUẢN LÝ HỌC SINH", JLabel.CENTER);
        lblTitle.setFont(new Font("Segoe UI", Font.BOLD, 24));
        lblTitle.setForeground(new Color(0, 102, 204));
      
        add(lblTitle, BorderLayout.NORTH);

        // ===== CENTER =====
        JPanel center = new JPanel(new BorderLayout(10, 10));
        add(center, BorderLayout.CENTER);

        // ===== DANH SÁCH LỚP =====
        pnlLop = new JPanel(new BorderLayout());
        pnlLop.setPreferredSize(new Dimension(230, 0));
        pnlLop.setBorder(BorderFactory.createTitledBorder("Danh sách lớp"));


        lstLop = new JList<>(modelLop);
        lstLop.setFont(new Font("Tahoma", Font.PLAIN, 16));
        pnlLop.add(new JScrollPane(lstLop), BorderLayout.CENTER);

        center.add(pnlLop, BorderLayout.WEST);

        // ===== PHẢI =====
        JPanel right = new JPanel(new BorderLayout(10, 10));
        center.add(right, BorderLayout.CENTER);

        // ===== DANH SÁCH HỌC SINH =====
        pnlDSHS = new JPanel(new BorderLayout());
        pnlDSHS.setBorder(BorderFactory.createTitledBorder("Danh sách học sinh"));


        lstHocSinh = new JList<>(modelHocSinh);
        lstHocSinh.setFont(new Font("Tahoma", Font.PLAIN, 16));
        pnlDSHS.add(new JScrollPane(lstHocSinh), BorderLayout.CENTER);

        right.add(pnlDSHS, BorderLayout.CENTER);

        // ===== FORM =====
        pnlForm = new JPanel(new GridLayout(3, 2, 10, 10));
        pnlForm.setBorder(BorderFactory.createTitledBorder("Thông tin học sinh"));


        lblMaHS = new JLabel("Mã học sinh:");
        lblTenHS = new JLabel("Tên học sinh:");
        lblLop = new JLabel("Lớp:");

        setLabelFont(lblMaHS);
        setLabelFont(lblTenHS);
        setLabelFont(lblLop);

        txtmahs = new JTextField();
        txttenhs = new JTextField();
        cbolop = new JComboBox<>();

        setInputFont(txtmahs);
        setInputFont(txttenhs);
        setInputFont(cbolop);

        pnlForm.add(lblMaHS);
        pnlForm.add(txtmahs);

        pnlForm.add(lblTenHS);
        pnlForm.add(txttenhs);

        pnlForm.add(lblLop);
        pnlForm.add(cbolop);

        right.add(pnlForm, BorderLayout.SOUTH);

        // ===== BUTTON =====
        pnlButton = new JPanel();
    

        btnthem = new JButton("Thêm");
        btnsua = new JButton("Sửa");
        btnxoa = new JButton("Xóa");
        btnlamsach = new JButton("Làm mới");

     

        pnlButton.add(btnthem);
        pnlButton.add(btnsua);
        pnlButton.add(btnxoa);
        pnlButton.add(btnlamsach);

        add(pnlButton, BorderLayout.SOUTH);
    }

    // ===== STYLE HELPER =====
    private void setLabelFont(JLabel lbl) {
        lbl.setFont(new Font("Tahoma", Font.BOLD, 14));
    }

    private void setInputFont(JComponent c) {
        c.setFont(new Font("Tahoma", Font.PLAIN, 15));
    }
    


}
