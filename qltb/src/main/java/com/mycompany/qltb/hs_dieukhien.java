/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.qltb;

import java.util.List;

/**
 *
 * @author LanAnh
 */
public class hs_dieukhien {
    public hs_view view;

    public hs_dieukhien(hs_view view) {
        this.view = view;
        loadlop();
        loadhs();
        click_hs();
    }
    

        public void loadlop(){
        view.modelLop.clear();
        qll_dao  dao= new qll_dao();
        List<Lop> ds = dao.getAllLop();

        for (Lop l : ds) {
            view.modelLop.addElement(l);
            
        }
        
     
    }
        
            public void loadhs(){
        view.modelHocSinh.clear();
        hs_dao  dao= new hs_dao();
        List<hs> ds = dao.getAll();

        for (hs h : ds) {
            view.modelHocSinh.addElement(h);
            
        }
        
     
    }
    
    public void click_hs(){
            view.lstHocSinh.addListSelectionListener(e -> {
        if (!e.getValueIsAdjusting()) {
            hs sv = view.lstHocSinh.getSelectedValue();
            if (sv == null) return;

            view.txtmahs.setText(sv.getMahs());
            view.txttenhs.setText(sv.getTenhs());
            view.cbolop.setSelectedItem(sv.getMalop());
        }
    });
    }

    
    
}
