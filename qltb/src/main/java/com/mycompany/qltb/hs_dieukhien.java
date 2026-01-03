/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.qltb;

import java.util.List;
import javax.swing.JOptionPane;

/**
 *
 * @author LanAnh
 */
public class hs_dieukhien {
    public hs_view view;
    public qll_dao  daol= new qll_dao();
    public hs_dao  daohs= new hs_dao();
    public String maLopDangChon =null;

    public hs_dieukhien(hs_view view) {
        this.view = view;
        load();
        
        click();
        
        nutbam();
    }
    

        public void load(){
            //lop
        view.modelLop.clear();       
        List<Lop> dsl = daol.getAllLop();
        for (Lop l : dsl) {
            view.modelLop.addElement(l);
            view.cbolop.addItem(l);
            
        }
        
     //hpcsinh
 
        view.modelHocSinh.clear();
        
        List<hs> dss = daohs.getAll();

        for (hs h : dss) {
            view.modelHocSinh.addElement(h);
            
        }
        
     
    }
    
    public void click() {
        //hócinh
    view.lstHocSinh.addListSelectionListener(e -> {
        if (!e.getValueIsAdjusting()) {
            hs sv = view.lstHocSinh.getSelectedValue();
            if (sv == null) return;

            view.txtmahs.setText(sv.getMahs());
            view.txttenhs.setText(sv.getTenhs());

            for (int i = 0; i < view.cbolop.getItemCount(); i++) {
                Lop l = (Lop) view.cbolop.getItemAt(i);
                if (l.getMaLop().equals(sv.getMalop())) {
                    view.cbolop.setSelectedIndex(i);
                    break;
                }
            }
        }
    });
    //lop

                view.lstLop.addListSelectionListener(e -> {
                if (!e.getValueIsAdjusting()) {
                    Lop l = view.lstLop.getSelectedValue();
                    if (l == null) return;

                    maLopDangChon = l.getMaLop();
                    view.lstHocSinh.clearSelection(); 

                    view.modelHocSinh.clear();
                    daohs.laytheolop(maLopDangChon)
                         .forEach(view.modelHocSinh::addElement);
                }
            });
        }
    public void nutbam(){
        
    
    
     view.btnlamsach.addActionListener(e -> {
        view.txtmahs.setText("");
        view.txttenhs.setText("");
        view.cbolop.setSelectedIndex(-1);
        
    view.lstHocSinh.clearSelection(); // ⭐ RẤT QUAN TRỌNG
    });


    
    view.btnthem.addActionListener(e -> {
            String ma = view.txtmahs.getText().trim();
            String ten = view.txttenhs.getText().trim();
            Lop l = (Lop) view.cbolop.getSelectedItem();

            if (ma.isEmpty() || ten.isEmpty()) {
                JOptionPane.showMessageDialog(view,
                        "Không được để trống dữ liệu!",
                        "Cảnh báo",
                        JOptionPane.WARNING_MESSAGE);
                return;
            }

            hs h=new hs();
            h.setMahs(ma);
            h.setTenhs(ten);
            h.setMalop(l.getMaLop());
            

            if (daohs.insert(h)) {
                JOptionPane.showMessageDialog(view, "Thêm danh mục thành công!");

                
            } else {
                JOptionPane.showMessageDialog(view,
                        "Thêm danh mục thất bại!",
                        "Lỗi",
                        JOptionPane.ERROR_MESSAGE);
            }
        });
        load();
    
    
    }
    
    
}
