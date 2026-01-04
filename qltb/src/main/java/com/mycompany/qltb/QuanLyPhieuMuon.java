package com.mycompany.qltb;

import java.awt.*;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

// [SỬA 1] Đổi extends JFrame -> extends JPanel
public class QuanLyPhieuMuon extends JPanel {

    // ... (Giữ nguyên các khai báo biến ở đây) ...
    private JTextField txtSearch;
    private JComboBox<String> cboStatus;
    private JComboBox<String> cboMonth;
    private JComboBox<String> cboYear;
    private JTable tblPhieu;
    private DefaultTableModel modelPhieu;
    private JButton btnTaoMoi;
    private JButton btnTraPhieu;
    private JTextField txtMaPM, txtMaHS, txtTenHS, txtNgayMuon, txtHanTra, txtGhiChu;
    private JComboBox<String> cboTenLop;
    private JComboBox<String> cboTenPhong;
    private JTable tblChiTietTB;
    private DefaultTableModel modelChiTietTB;
    private JButton btnUpdate; 
    private PhieuMuonDao dao = new PhieuMuonDao();
    private qltb_view parent; 

    public QuanLyPhieuMuon(qltb_view parent) {
        this.parent = parent;
        initComponents();
        loadComboBoxLop();
        loadComboBoxPhong();
        loadData();
    }

    private void initComponents() {
        // [SỬA 2] Xóa các dòng thiết lập JFrame (setTitle, setSize, setLocation...)
        // setTitle("QUẢN LÝ PHIẾU MƯỢN");
        // setSize(1200, 700);
        // setLocationRelativeTo(null);
        // setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        
        setLayout(new BorderLayout()); // Giữ lại layout

        // ... (Giữ nguyên toàn bộ phần code bên dưới của hàm initComponents) ...
        
        // ==========================================
        // 1. PANEL BÊN TRÁI 
        // ==========================================
        JPanel pLeft = new JPanel(new BorderLayout(5, 5));
        pLeft.setPreferredSize(new Dimension(500, 0));
        pLeft.setBorder(BorderFactory.createTitledBorder("Danh sách phiếu mượn"));
        
        javax.swing.border.TitledBorder borderLeft = BorderFactory.createTitledBorder("Danh sách phiếu mượn");
        borderLeft.setTitleColor(Color.BLACK); // Đặt màu chữ tiêu đề là ĐEN
        borderLeft.setTitleFont(new Font("Arial", Font.BOLD, 14)); // (Tùy chọn) Tăng kích thước font
        pLeft.setBorder(borderLeft);
       
        JPanel pFilter = new JPanel(new GridLayout(2, 1, 5, 5));
        JPanel pRow1 = new JPanel(new FlowLayout(FlowLayout.LEFT));
        txtSearch = new JTextField(15);
        txtSearch.addActionListener(e -> loadData()); 
        cboStatus = new JComboBox<>(new String[]{"Tất cả", "Đang mượn", "Đã trả"});
        cboStatus.addActionListener(e -> loadData());
        JLabel lblTimTen = new JLabel("Tìm tên HS:");
        lblTimTen.setForeground(Color.BLACK); // Màu chữ đen
        pRow1.add(lblTimTen);
        pRow1.add(txtSearch);
        pRow1.add(cboStatus);

        JPanel pRow2 = new JPanel(new FlowLayout(FlowLayout.LEFT));
        cboMonth = new JComboBox<>();
        cboMonth.addItem("Tất cả tháng");
        for (int i = 1; i <= 12; i++) cboMonth.addItem("Tháng " + i);
        cboYear = new JComboBox<>();
        int currentYear = Calendar.getInstance().get(Calendar.YEAR);
        int currentMonth = Calendar.getInstance().get(Calendar.MONTH) + 1;
        for (int i = currentYear + 1; i >= 2020; i--) cboYear.addItem(String.valueOf(i));
        cboMonth.setSelectedIndex(currentMonth); 
        cboYear.setSelectedItem(String.valueOf(currentYear));
        cboMonth.addActionListener(e -> loadData());
        cboYear.addActionListener(e -> loadData());
        JLabel lblThoiGian = new JLabel("Thời gian:");
        lblThoiGian.setForeground(Color.BLACK); // Màu chữ đen
        pRow2.add(lblThoiGian);
        pRow2.add(cboMonth);
        pRow2.add(cboYear);

        pFilter.add(pRow1); pFilter.add(pRow2);
        pLeft.add(pFilter, BorderLayout.NORTH);

        modelPhieu = new DefaultTableModel(new String[]{"Mã PM", "Tên HS", "Ngày mượn", "Trạng thái"}, 0) {
            @Override public boolean isCellEditable(int row, int col) { return false; }
        };
        tblPhieu = new JTable(modelPhieu);
        tblPhieu.setRowHeight(25);
        tblPhieu.getTableHeader().setForeground(Color.BLACK);
        tblPhieu.getTableHeader().setFont(new Font("Arial", Font.BOLD, 12));
        tblPhieu.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) showDetail();
        });
        pLeft.add(new JScrollPane(tblPhieu), BorderLayout.CENTER);

        // ==========================================
        // 2. PANEL BÊN PHẢI 
        // ==========================================
        JPanel pRight = new JPanel(new BorderLayout());
        javax.swing.border.TitledBorder borderRight = BorderFactory.createTitledBorder("Chi tiết phiếu mượn");
        borderRight.setTitleColor(Color.BLACK); // Màu chữ đen
        borderRight.setTitleFont(new Font("Arial", Font.BOLD, 14));
        pRight.setBorder(borderRight);

        // --- Nút Chức Năng (Góc trên) ---
        JPanel pRightTop = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 5));
        
        btnTaoMoi = new JButton("+ TẠO PHIẾU MỚI");
        btnTaoMoi.setBackground(new Color(0, 153, 76)); 
        btnTaoMoi.setForeground(Color.DARK_GRAY);
        btnTaoMoi.setFont(new Font("Arial", Font.BOLD, 12));
        btnTaoMoi.addActionListener(e -> new TaoPhieuMuonFrm(this).setVisible(true));
        
        btnTraPhieu = new JButton("TRẢ PHIẾU / HOÀN TẤT");
        btnTraPhieu.setBackground(new Color(0, 102, 204)); 
        btnTraPhieu.setForeground(Color.DARK_GRAY);
        btnTraPhieu.setFont(new Font("Arial", Font.BOLD, 12));
        btnTraPhieu.addActionListener(e -> xuLyTraPhieu()); 

        pRightTop.add(btnTaoMoi);
        pRightTop.add(btnTraPhieu);
        pRight.add(pRightTop, BorderLayout.NORTH);

        // --- Khu vực Form + Bảng ---
        JPanel pCenterRight = new JPanel(new BorderLayout(5, 5));
        
        JPanel pForm = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5); gbc.fill = GridBagConstraints.HORIZONTAL;
        
        txtMaPM = new JTextField(15); txtMaPM.setEditable(false); txtMaPM.setBackground(new Color(240,240,240));
        txtMaHS = new JTextField(15);
        txtTenHS = new JTextField(15); txtTenHS.setEditable(false);
        cboTenLop = new JComboBox<>();
        cboTenPhong = new JComboBox<>();
        txtNgayMuon = new JTextField(15); txtNgayMuon.setEditable(false);
        txtHanTra = new JTextField(15);
        txtGhiChu = new JTextField(15);

        gbc.gridx=0; gbc.gridy=0;
        JLabel lblMaPM = new JLabel("Mã Phiếu:"); lblMaPM.setForeground(Color.BLACK);
        pForm.add(lblMaPM, gbc);
        gbc.gridx=1; pForm.add(txtMaPM, gbc);

        gbc.gridx=2;
        JLabel lblMaHS = new JLabel("Mã HS:"); lblMaHS.setForeground(Color.BLACK);
        pForm.add(lblMaHS, gbc);
        gbc.gridx=3; pForm.add(txtMaHS, gbc);

        gbc.gridx=0; gbc.gridy=1;
        JLabel lblHoTen = new JLabel("Họ Tên:"); lblHoTen.setForeground(Color.BLACK);
        pForm.add(lblHoTen, gbc);
        gbc.gridx=1; pForm.add(txtTenHS, gbc);

        gbc.gridx=2;
        JLabel lblLop = new JLabel("Lớp:"); lblLop.setForeground(Color.BLACK);
        pForm.add(lblLop, gbc);
        gbc.gridx=3; pForm.add(cboTenLop, gbc);

        gbc.gridx=0; gbc.gridy=2;
        JLabel lblPhong = new JLabel("Phòng:"); lblPhong.setForeground(Color.BLACK);
        pForm.add(lblPhong, gbc);
        gbc.gridx=1; pForm.add(cboTenPhong, gbc);

        gbc.gridx=2;
        JLabel lblNgayMuon = new JLabel("Ngày mượn:"); lblNgayMuon.setForeground(Color.BLACK);
        pForm.add(lblNgayMuon, gbc);
        gbc.gridx=3; pForm.add(txtNgayMuon, gbc);

        gbc.gridx=0; gbc.gridy=3;
        JLabel lblHanTra = new JLabel("Hạn trả:"); lblHanTra.setForeground(Color.BLACK);
        pForm.add(lblHanTra, gbc);
        gbc.gridx=1; pForm.add(txtHanTra, gbc);

        gbc.gridx=2;
        JLabel lblGhiChu = new JLabel("Ghi chú:"); lblGhiChu.setForeground(Color.BLACK);
        pForm.add(lblGhiChu, gbc);
        gbc.gridx=3; pForm.add(txtGhiChu, gbc);

        pCenterRight.add(pForm, BorderLayout.NORTH);

        JPanel pTableDevice = new JPanel(new BorderLayout());
        javax.swing.border.TitledBorder borderDevice = BorderFactory.createTitledBorder("Các thiết bị trong phiếu này");
        borderDevice.setTitleColor(Color.BLACK); // Màu chữ đen
        pTableDevice.setBorder(borderDevice);
        pTableDevice.setBorder(BorderFactory.createTitledBorder("Các thiết bị trong phiếu này"));
        modelChiTietTB = new DefaultTableModel(new String[]{"Mã TB", "Tên TB", "Loại", "Số lượng", "Tình trạng"}, 0) {
            @Override public boolean isCellEditable(int row, int col) { return false; }
        };
        tblChiTietTB = new JTable(modelChiTietTB);
        tblChiTietTB.setRowHeight(22);
        tblChiTietTB.getTableHeader().setForeground(Color.BLACK);
        tblChiTietTB.getTableHeader().setFont(new Font("Arial", Font.BOLD, 12));
        tblChiTietTB.getColumnModel().getColumn(4).setPreferredWidth(200);
        pTableDevice.add(new JScrollPane(tblChiTietTB), BorderLayout.CENTER);

        pCenterRight.add(pTableDevice, BorderLayout.CENTER);
        pRight.add(pCenterRight, BorderLayout.CENTER);

        JPanel pRightBottom = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        btnUpdate = new JButton("CẬP NHẬT THÔNG TIN");
        btnUpdate.setBackground(new Color(230, 230, 230)); // Nền xám nhạt
        btnUpdate.setForeground(Color.BLACK); // Chữ đen
        btnUpdate.setFont(new Font("Arial", Font.BOLD, 12));
        btnUpdate.setPreferredSize(new Dimension(180, 35));
        btnUpdate.addActionListener(e -> updatePhieuMuon());
        pRightBottom.add(btnUpdate);
        pRight.add(pRightBottom, BorderLayout.SOUTH);

        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, pLeft, pRight);
        splitPane.setDividerLocation(500);
        add(splitPane, BorderLayout.CENTER);
    }

    // ... (Giữ nguyên các hàm loadComboBox, loadData, showDetail...)
    private void loadComboBoxLop() {
        cboTenLop.removeAllItems();
        List<String> listLop = dao.getAllTenLop();
        for (String tenLop : listLop) cboTenLop.addItem(tenLop);
    }
    private void loadComboBoxPhong() {
        cboTenPhong.removeAllItems();
        List<String> listPhong = dao.getAllTenPhong();
        for (String tenPhong : listPhong) cboTenPhong.addItem(tenPhong);
    }
    public void loadData() {
        modelPhieu.setRowCount(0);
        String keyword = txtSearch.getText().trim();
        int status = cboStatus.getSelectedIndex(); 
        int month = cboMonth.getSelectedIndex() > 0 ? cboMonth.getSelectedIndex() : 0;
        int year = Integer.parseInt(cboYear.getSelectedItem().toString());
        List<PhieuMuon> list = dao.findPhieuMuon(keyword, status, month, year);
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        for (PhieuMuon pm : list) {
            String trangThaiText = (pm.getNgayTra() == null) ? "Đang mượn" : "Đã trả";
            modelPhieu.addRow(new Object[]{ pm.getMaPM(), pm.getTenHS(), sdf.format(pm.getNgayMuon()), trangThaiText });
        }
    }
    private void showDetail() {
        int row = tblPhieu.getSelectedRow();
        if (row == -1) return;
        int maPM = Integer.parseInt(tblPhieu.getValueAt(row, 0).toString());
        PhieuMuon pm = dao.findById(maPM);
        if (pm != null) {
            txtMaPM.setText(String.valueOf(pm.getMaPM()));
            txtMaHS.setText(pm.getMaHS());
            txtTenHS.setText(pm.getTenHS());
            String tenLop = dao.getTenLopByMa(pm.getMaLop());
            if (tenLop != null) cboTenLop.setSelectedItem(tenLop);
            String tenPhong = dao.getTenPhongByMa(pm.getMaPhong());
            if (tenPhong != null) cboTenPhong.setSelectedItem(tenPhong);
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            txtNgayMuon.setText(pm.getNgayMuon() != null ? sdf.format(pm.getNgayMuon()) : "");
            txtHanTra.setText(pm.getHanTra() != null ? sdf.format(pm.getHanTra()) : "");
            txtGhiChu.setText(pm.getGhiChu());
            btnTraPhieu.setEnabled(pm.getNgayTra() == null);
        }
        modelChiTietTB.setRowCount(0);
        List<qltb_thuoctinh> listTB = dao.getThietBiMuonByPhieu(maPM);
        for (qltb_thuoctinh tb : listTB) {
            modelChiTietTB.addRow(new Object[]{
                tb.getMaTB(), tb.getTenTB(), tb.getMaLoai(), tb.getTongSoLuong(), tb.getGhiChu()
            });
        }
    }
    private void xuLyTraPhieu() {
        if (txtMaPM.getText().isEmpty()) { JOptionPane.showMessageDialog(this, "Chưa chọn phiếu nào!"); return; }
        int row = tblPhieu.getSelectedRow();
        if (row != -1) {
            String status = tblPhieu.getValueAt(row, 3).toString();
            if (status.equals("Đã trả")) { JOptionPane.showMessageDialog(this, "Phiếu này đã trả rồi!"); return; }
        }
        int maPM = Integer.parseInt(txtMaPM.getText());
        TraPhieuDialog dialog = new TraPhieuDialog(this, maPM);
        dialog.setVisible(true);
    }
    private void updatePhieuMuon() {
        try {
             PhieuMuon pm = new PhieuMuon();
             pm.setMaPM(Integer.parseInt(txtMaPM.getText()));
             pm.setMaHS(txtMaHS.getText());
             String tenLop = (String) cboTenLop.getSelectedItem();
             String maLop = dao.getMaLopByTen(tenLop);
             pm.setMaLop(maLop);
             String tenPhong = (String) cboTenPhong.getSelectedItem();
             String maPhong = dao.getMaPhongByTen(tenPhong);
             pm.setMaPhong(maPhong);
             pm.setGhiChu(txtGhiChu.getText());
             SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
             Date d = sdf.parse(txtHanTra.getText());
             pm.setHanTra(new Timestamp(d.getTime()));
             if (dao.updatePhieuMuon(pm)) {
                 JOptionPane.showMessageDialog(this, "Cập nhật thành công!");
                 loadData(); 
             } else { JOptionPane.showMessageDialog(this, "Cập nhật thất bại!"); }
        } catch (Exception e) { e.printStackTrace(); }
    }
}