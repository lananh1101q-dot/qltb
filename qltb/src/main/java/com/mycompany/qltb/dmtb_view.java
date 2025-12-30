
package com.mycompany.qltb;

// Các import: nhập các class từ thư viện Java để sử dụng
import java.awt.BorderLayout;    // Layout sắp xếp NORTH, SOUTH, CENTER, EAST, WEST
import java.awt.FlowLayout;     // Layout sắp xếp các phần tử theo dòng ngang
import java.awt.Font;           // Class để tạo kiểu chữ (font)
import java.awt.GridLayout;     // Layout sắp xếp theo bảng lưới (hàng x cột)
import javax.swing.BorderFactory;   // Tạo viền (border) cho panel
import javax.swing.JButton;         // Nút bấm
import javax.swing.JFrame;          // Cửa sổ chính
import javax.swing.JLabel;          // Nhãn hiển thị chữ
import javax.swing.JPanel;          // //Khung chứa các thành phần con
import javax.swing.JTextField;      // Ô nhập văn bản
import javax.swing.JOptionPane;     // Hộp thoại thông báo (message dialog)

// Đây là khai báo class tên là dmtb_view
// Class này kế thừa từ JFrame → nghĩa là nó chính là một cửa sổ
// Mục đích: tạo form để thêm danh mục thiết bị mới
public class dmtb_view extends JFrame {

    private qltb_dieukhien parent;
    public JTextField txtId = new JTextField();
    public JTextField txtHoten = new JTextField();
    public JButton btnadd = new JButton("Thêm");
    public JButton btnhuy = new JButton("Hủy");

    public dmtb_dao dao = new dmtb_dao();

    public dmtb_view() {
        this(null);
    }

    public dmtb_view(qltb_dieukhien parent) {
        this.parent = parent;

        setTitle("Thêm danh mục");
        setSize(400, 220);
        setLayout(new BorderLayout(10, 10));
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        JLabel title = new JLabel("THÊM DANH MỤC THIẾT BỊ", JLabel.CENTER);
        title.setFont(new Font("Segoe UI", Font.BOLD, 15));
        add(title, BorderLayout.NORTH);

        JPanel form = new JPanel(new GridLayout(2, 2, 10, 10));
        form.setBorder(BorderFactory.createEmptyBorder(15, 20, 10, 20));
        form.add(new JLabel("Mã danh mục:"));
        form.add(txtId);
        form.add(new JLabel("Tên danh mục:"));
        form.add(txtHoten);
        add(form, BorderLayout.CENTER);

        JPanel pbtn = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 5));
        pbtn.add(btnadd);
        pbtn.add(btnhuy);
        add(pbtn, BorderLayout.SOUTH);

        btnadd.addActionListener(e -> {
            String ma = txtId.getText().trim();
            String ten = txtHoten.getText().trim();

            if (ma.isEmpty() || ten.isEmpty()) {
                JOptionPane.showMessageDialog(this,
                        "Không được để trống dữ liệu!",
                        "Cảnh báo",
                        JOptionPane.WARNING_MESSAGE);
                return;
            }

            boolean result = dao.insert(new dmtb_thuoctinh(ma, ten));

            if (result) {
                JOptionPane.showMessageDialog(this, "Thêm danh mục thành công!");

                if (parent != null) {
                    parent.loadDanhMuc();
                }

                dispose();
            } else {
                JOptionPane.showMessageDialog(this,
                        "Thêm danh mục thất bại!",
                        "Lỗi",
                        JOptionPane.ERROR_MESSAGE);
            }
        });

        btnhuy.addActionListener(e -> dispose());

        setVisible(true);
    }
}
