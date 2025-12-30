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

        // ===== MENU =====
        JMenuBar menuBar = new JMenuBar();

        JMenu menuHeThong = new JMenu("Hệ thống");
        JMenuItem mTrangChu = new JMenuItem("Trang chủ");
        JMenuItem mQLTB = new JMenuItem("Quản lý thiết bị");

        menuHeThong.add(mTrangChu);
        menuHeThong.add(mQLTB);
        menuBar.add(menuHeThong);

        setJMenuBar(menuBar);

        // ===== CARDLAYOUT =====
        cardLayout = new CardLayout();
        panelChinh = new JPanel(cardLayout);

        // Trang chào
        JPanel panelHome = new JPanel(new BorderLayout());
        JLabel lbl = new JLabel("TRANG CHỦ HỆ THỐNG", JLabel.CENTER);
        lbl.setFont(new Font("Arial", Font.BOLD, 26));
        panelHome.add(lbl, BorderLayout.CENTER);

        // Màn hình quản lý thiết bị
        qltb_view panelQLTB = new qltb_view();

        panelChinh.add(panelHome, "HOME");
        panelChinh.add(panelQLTB, "QLTB");

        add(panelChinh);

        // ===== SỰ KIỆN MENU =====
        mTrangChu.addActionListener(e ->
                cardLayout.show(panelChinh, "HOME"));

        mQLTB.addActionListener(e ->
                cardLayout.show(panelChinh, "QLTB"));
    }

    public static void main(String[] args) {
        new trangchu().setVisible(true);
    }
}
