package com.mycompany.qltb;

import java.awt.*;
import java.util.List;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class qltb_view extends JPanel {

    public JMenuItem menuSave, menuOpen, menuExit;

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
                new String[]{"Mã TB", "Tên TB", "Danh mục", "Trạng thái", "SL Tốt", "SL Hỏng", "Tổng"}, 0) {
            public boolean isCellEditable(int r, int c) {
                return false;
            }
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

        loadDanhMuc();
        loadtrangthai();
        loadtb();
    }

    // ===== GIỮ NGUYÊN TOÀN BỘ HÀM DAO =====
       public void loadDanhMuc() {
    listModelDm.clear();
    cboCategory.removeAllItems();  
    dmtb_dao dao = new dmtb_dao();
  List<dmtb_thuoctinh> dsDanhMuc = dao.getAll();
   // Sử dụng đối tượng dao (thuộc lớp dmtb_dao) để gọi phương thức getAll()
// Phương thức này lấy toàn bộ dữ liệu danh mục từ bảng "loaitb" trong cơ sở dữ liệu
// Kết quả trả về được gán vào biến dsDanhMuc
// Biến dsDanhMuc có kiểu là List<dmtb_thuoctinh> → tức là một danh sách các đối tượng dmtb_thuoctinh
    for (dmtb_thuoctinh dm : dsDanhMuc) {
        // Thêm đối tượng danh mục vào mô hình của JList
        // JList sẽ tự động gọi toString() của đối tượng để hiển thị tên
        listModelDm.addElement(dm);
        
        // Thêm cùng đối tượng đó vào JComboBox
        // JComboBox cũng sẽ hiển thị tên nhờ override toString()
        cboCategory.addItem(dm);
    } 
}
     public void loadtrangthai() {
    cbotrangthai.removeAllItems();
    trangthai_dao dao = new trangthai_dao();
    // Chỉ lấy trạng thái Sẵn sàng (1) và Dừng hoạt động (8)
    List<trangthai> dsDanhMuc = dao.getTrangThaiQuanLy();

    for (trangthai dm : dsDanhMuc) {
        cbotrangthai.addItem(dm);
    }
}
     public void loadtb() {
    tableModelSp.setRowCount(0); //Xóa toàn bộ dữ liệu cũ trong bảng
     // Gọi DAO lấy danh sách thiết bị
    qltb_dao dao = new qltb_dao();
    List<qltb_thuoctinh> dsTB = dao.getAll();

    // Đổ dữ liệu vào JTable
    for (qltb_thuoctinh tb : dsTB) {
        tableModelSp.addRow(new Object[]{
            tb.getMaTB(),
            tb.getTenTB(),
            tb.gettenLoai(),        // hoặc tb.getMaLoai()
            tb.gettenTrangThai(),   // hoặc tb.getTrangThai()
            tb.getSoLuongTot(),     // Số lượng tốt
            tb.getSoLuongHong(),    // Số lượng hỏng
            tb.getTongSoLuong()     // Tổng số lượng
        });
    } 
}
     private void showDataFromTable(int row) {
    // Lấy dữ liệu từ JTable
    String maTB = tableSp.getValueAt(row, 0).toString();
    String tenTB = tableSp.getValueAt(row, 1).toString();
    String tenLoai = tableSp.getValueAt(row, 2).toString();
    String tenTrangThai = tableSp.getValueAt(row, 3).toString();
    String soLuongTot = tableSp.getValueAt(row, 4).toString();
    String soLuongHong = tableSp.getValueAt(row, 5).toString();

    // Đẩy lên TextField
    txtId.setText(maTB);
    txtName.setText(tenTB);
    txtSoLuongTot.setText(soLuongTot);
    txtSoLuongHong.setText(soLuongHong);

    // Set lại ComboBox danh mục
    for (int i = 0; i < cboCategory.getItemCount(); i++) {
        if (cboCategory.getItemAt(i).getTenloai().equals(tenLoai)) {
            cboCategory.setSelectedIndex(i);
            break;
        }
    }

    // Set lại ComboBox trạng thái
    for (int i = 0; i < cbotrangthai.getItemCount(); i++) {
        if (cbotrangthai.getItemAt(i).getTenTinhTrang().equals(tenTrangThai)) {
            cbotrangthai.setSelectedIndex(i);
            break;
        }
    }
}
private void clearForm() {
    txtId.setText("");
    txtName.setText("");
    txtSoLuongTot.setText("");
    txtSoLuongHong.setText("");
    cboCategory.setSelectedIndex(-1);
    cbotrangthai.setSelectedIndex(-1);
}

    public void loadtbByLoai(String maloai) {
    tableModelSp.setRowCount(0);

    qltb_dao dao = new qltb_dao();
    List<qltb_thuoctinh> ds = dao.getByLoai(maloai);

    for (qltb_thuoctinh tb : ds) {
        tableModelSp.addRow(new Object[]{
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
