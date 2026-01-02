/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.qltb;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Admin
 */
public class QuanLyLop extends JPanel {

    // Khai báo các component
    private JTextField txtMaLop;
    private JTextField txtTenLop;
    private JTextField txtGVCN;

    private JButton btnThem;
    private JButton btnSua;
    private JButton btnXoa;
    private JButton btnLamMoi;

    private JTable tableLop;
    private DefaultTableModel tableModel;

    public QuanLyLop() {
        // Cấu hình Panel chính
        setLayout(new BorderLayout(10, 10));
        setBorder(new EmptyBorder(10, 10, 10, 10)); // Khoảng cách với viền ngoài

        // 1. Khởi tạo giao diện
        initUI();

        // 2. Thêm dữ liệu mẫu (hoặc load từ DB)
        loadDataToTable();

        // 3. Bắt sự kiện
        addEvents();
    }

    private void initUI() {
        // --- PHẦN 1: FORM NHẬP LIỆU (NORTH) ---
        JPanel pnlInput = new JPanel(new BorderLayout(5, 5));
        pnlInput.setBorder(new TitledBorder("Thông tin Lớp học"));
        
        // Panel chứa Label và Textfield
        JPanel pnlFields = new JPanel(new GridLayout(3, 2, 10, 10));
        pnlFields.setBorder(new EmptyBorder(10, 20, 10, 20));

        pnlFields.add(new JLabel("Mã Lớp:"));
        txtMaLop = new JTextField();
        pnlFields.add(txtMaLop);

        pnlFields.add(new JLabel("Tên Lớp:"));
        txtTenLop = new JTextField();
        pnlFields.add(txtTenLop);

        pnlFields.add(new JLabel("Giáo Viên Chủ Nhiệm:"));
        txtGVCN = new JTextField();
        pnlFields.add(txtGVCN);

        // Panel chứa các Nút bấm
        JPanel pnlButtons = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 10));
        btnThem = new JButton("Thêm");
        btnSua = new JButton("Sửa");
        btnXoa = new JButton("Xóa");
        btnLamMoi = new JButton("Làm mới");

        // Thêm icon (nếu muốn, ở đây để text cho đơn giản)
        pnlButtons.add(btnThem);
        pnlButtons.add(btnSua);
        pnlButtons.add(btnXoa);
        pnlButtons.add(btnLamMoi);

        // Gép vào pnlInput
        pnlInput.add(pnlFields, BorderLayout.CENTER);
        pnlInput.add(pnlButtons, BorderLayout.SOUTH);

        // --- PHẦN 2: BẢNG DỮ LIỆU (CENTER) ---
        JPanel pnlTable = new JPanel(new BorderLayout());
        pnlTable.setBorder(new TitledBorder("Danh sách Lớp"));

        // Cấu hình bảng
        String[] columnNames = {"Mã Lớp", "Tên Lớp", "GVCN"};
        tableModel = new DefaultTableModel(columnNames, 0) {
            // Không cho phép sửa trực tiếp trên ô
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        tableLop = new JTable(tableModel);
        tableLop.setRowHeight(25);
        tableLop.setSelectionMode(ListSelectionModel.SINGLE_SELECTION); // Chỉ chọn 1 dòng
        
        // Thêm bảng vào ScrollPane
        JScrollPane scrollPane = new JScrollPane(tableLop);
        pnlTable.add(scrollPane, BorderLayout.CENTER);

        // Thêm 2 phần chính vào Panel Lớp
        add(pnlInput, BorderLayout.NORTH);
        add(pnlTable, BorderLayout.CENTER);
    }

    private void loadDataToTable() {
        tableModel.setRowCount(0); // Xóa dữ liệu cũ
        qll_dao dao = new qll_dao();
        for (Lop lop : dao.getAllLop()) {
            tableModel.addRow(new Object[]{lop.getMaLop(), lop.getTenLop(), lop.getGvcn()});
        }
    }

    private void addEvents() {
        // 1. Sự kiện Click vào dòng trong bảng -> Đổ dữ liệu lên TextField
        tableLop.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int selectedRow = tableLop.getSelectedRow();
                if (selectedRow != -1) {
                    String ma = tableModel.getValueAt(selectedRow, 0).toString();
                    String ten = tableModel.getValueAt(selectedRow, 1).toString();
                    String gv = tableModel.getValueAt(selectedRow, 2).toString();

                    txtMaLop.setText(ma);
                    txtTenLop.setText(ten);
                    txtGVCN.setText(gv);

                    // Khi chọn sửa, thường sẽ khóa Mã Lớp (Khóa chính)
                    txtMaLop.setEditable(false);
                }
            }
        });

        // 2. Sự kiện nút THÊM
        btnThem.addActionListener(e -> {
            String ma = txtMaLop.getText().trim();
            String ten = txtTenLop.getText().trim();
            String gv = txtGVCN.getText().trim();

            if (ma.isEmpty() || ten.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Mã lớp và Tên lớp không được để trống!");
                return;
            }

            // ... Kiểm tra rỗng ...
            qll_dao dao = new qll_dao();
            if (dao.isExist(ma)) {
                JOptionPane.showMessageDialog(this, "Mã lớp đã tồn tại!");
                return;
            }
            Lop lopMoi = new Lop(ma, ten, gv);
            if (dao.insert(lopMoi)) {
                JOptionPane.showMessageDialog(this, "Thêm thành công!");
                loadDataToTable(); // Refresh bảng
                clearForm();
            } else {
                JOptionPane.showMessageDialog(this, "Thêm thất bại!");
            }
        });

        // 3. Sự kiện nút SỬA
        btnSua.addActionListener(e -> {
            int selectedRow = tableLop.getSelectedRow();
            if (selectedRow == -1) {
                JOptionPane.showMessageDialog(this, "Vui lòng chọn lớp cần sửa!");
                return;
            }

            String ma = txtMaLop.getText().trim(); // Mã không sửa được (đã disable) nhưng vẫn lấy để làm điều kiện WHERE
            String ten = txtTenLop.getText().trim();
            String gv = txtGVCN.getText().trim();

            Lop lopSua = new Lop(ma, ten, gv);
                new qll_dao().update(lopSua);
                JOptionPane.showMessageDialog(this, "Cập nhật thành công!");
                loadDataToTable();
                clearForm();
        });

        // 4. Sự kiện nút XÓA
        btnXoa.addActionListener(e -> {
            int selectedRow = tableLop.getSelectedRow();
            if (selectedRow == -1) {
                JOptionPane.showMessageDialog(this, "Vui lòng chọn lớp cần xóa!");
                return;
            }
            
            String ma = tableModel.getValueAt(selectedRow, 0).toString();
            int confirm = JOptionPane.showConfirmDialog(this, "Bạn có chắc muốn xóa lớp " + ma + "?", "Xác nhận", JOptionPane.YES_NO_OPTION);
            
            if (confirm == JOptionPane.YES_OPTION) {
                new qll_dao().delete(ma);
                JOptionPane.showMessageDialog(this, "Xóa thành công!");
                loadDataToTable();
                clearForm();         
            }
        });

        // 5. Sự kiện nút LÀM MỚI
        btnLamMoi.addActionListener(e -> {
            clearForm();
        });
    }

    // Hàm reset form về trạng thái ban đầu
    private void clearForm() {
        txtMaLop.setText("");
        txtTenLop.setText("");
        txtGVCN.setText("");
        txtMaLop.setEditable(true); // Mở khóa lại mã lớp
        tableLop.clearSelection();
    }
}