package com.mycompany.qltb;

import java.awt.*;
import javax.swing.*;

public class trangchu extends JFrame {

    CardLayout cardLayout;
    JPanel panelChinh;

    public trangchu() {
        setTitle("HỆ THỐNG QUẢN LÝ THIẾT BỊ");
        setSize(1100, 650);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        // ================= MENU =================
        JMenuBar menuBar = new JMenuBar();

        JMenu menuHeThong = new JMenu("Hệ thống");
        JMenuItem mTrangChu = new JMenuItem("Trang chủ");
        JMenuItem mQLTB = new JMenuItem("Quản lý thiết bị");
        JMenuItem mPhieuMuon = new JMenuItem("Phiếu mượn");
        JMenuItem mPhong = new JMenuItem("Quản lý phòng");

        menuHeThong.add(mTrangChu);
        menuHeThong.add(mQLTB);
        menuHeThong.add(mPhieuMuon);
        menuHeThong.add(mPhong);

        menuBar.add(menuHeThong);
        setJMenuBar(menuBar);

        // ================= CARDLAYOUT =================
        cardLayout = new CardLayout();
        panelChinh = new JPanel(cardLayout);

        // ===== Trang chủ =====
        JPanel panelHome = new JPanel(new BorderLayout());
        JLabel lblHome = new JLabel("TRANG CHỦ HỆ THỐNG", JLabel.CENTER);
        lblHome.setFont(new Font("Arial", Font.BOLD, 26));
        panelHome.add(lblHome, BorderLayout.CENTER);

        // ===== Các màn hình =====
        qltb_view panelQLTB = new qltb_view();      // ĐÃ là JPanel
        JPanel panelPhieuMuon = taoPanelTam("PHIẾU MƯỢN");
        JPanel panelPhong = taoPanelTam("QUẢN LÝ PHÒNG");

        panelChinh.add(panelHome, "HOME");
        panelChinh.add(panelQLTB, "QLTB");
        panelChinh.add(panelPhieuMuon, "PHIEUMUON");
        panelChinh.add(panelPhong, "PHONG");

        add(panelChinh, BorderLayout.CENTER);

        // ================= SỰ KIỆN MENU =================
        mTrangChu.addActionListener(e ->
                cardLayout.show(panelChinh, "HOME"));

        mQLTB.addActionListener(e ->
                cardLayout.show(panelChinh, "QLTB"));

        mPhieuMuon.addActionListener(e ->
                cardLayout.show(panelChinh, "PHIEUMUON"));

        mPhong.addActionListener(e ->
                cardLayout.show(panelChinh, "PHONG"));

        // Mặc định
        cardLayout.show(panelChinh, "HOME");
    }

    // Panel tạm (placeholder)
    private JPanel taoPanelTam(String title) {
        JPanel p = new JPanel(new BorderLayout());
        JLabel l = new JLabel(title, JLabel.CENTER);
        l.setFont(new Font("Arial", Font.BOLD, 22));
        p.add(l, BorderLayout.CENTER);
        return p;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() ->
                new trangchu().setVisible(true));
    }
}
