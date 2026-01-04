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

    public String maLopDangChon =null;


    public hs_dieukhien(hs_view view) {
        this.view = view;
        loadl();
        loadhs();
        
        click();
        
        nutbam();
    }
    

        public void loadl(){
                qll_dao  daol= new qll_dao();
    hs_dao  daohs= new hs_dao();
            //lop
            view.cbolop.removeAllItems(); 
        view.modelLop.clear();       
        List<Lop> dsl = daol.getAllLop();
        for (Lop l : dsl) {
            view.modelLop.addElement(l);
            view.cbolop.addItem(l);
            
        }
        }
        public void loadhs(){
     //hpcsinh
     
    hs_dao  daohs= new hs_dao();
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
              hs h=new hs();
            view.lstLop.clearSelection(); // ⭐ CHỐT LỖI
            h = view.lstHocSinh.getSelectedValue();
            
            if (h == null) return;

            view.txtmahs.setText(h.getMahs());
            view.txttenhs.setText(h.getTenhs());

           for (int i = 0; i < view.cbolop.getItemCount(); i++) {
            Lop l = view.cbolop.getItemAt(i);
            if (l.getMaLop().equals(h.getMalop())) {
                view.cbolop.setSelectedItem(l); // ⭐ QUAN TRỌNG
                break;
            }
}

        }
    });
    //lop

                view.lstLop.addListSelectionListener(e -> {
                   hs_dao  daohs= new hs_dao();
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

            hs s=new hs();
            s.setMahs(ma);
            s.setTenhs(ten);
            s.setMalop(l.getMaLop());
               hs_dao  daohs= new hs_dao();
            

            if (daohs.insert(s)) {
                JOptionPane.showMessageDialog(view, "Thêm danh mục thành công!");

                
            } else {
                JOptionPane.showMessageDialog(view,
                        "Thêm danh mục thất bại!",
                        "Lỗi",
                        JOptionPane.ERROR_MESSAGE);
            }
            loadhs();
        });
        view.btnsua.addActionListener(e-> {
            String ten = view.txttenhs.getText().trim();
            Lop l = (Lop) view.cbolop.getSelectedItem();
            if (ten.isEmpty() || l== null) {
                JOptionPane.showMessageDialog(view, "Không được để trống!", "Cảnh báo", JOptionPane.WARNING_MESSAGE);
                return;
            }
            
              
                  hs_dao  daohs= new hs_dao();
                  hs h = view.lstHocSinh.getSelectedValue();
                if (h == null) {
                    JOptionPane.showMessageDialog(view, "Chưa chọn học sinh!");
                    return;
}


            h.setTenhs(ten);
            h.setMalop(l.getMaLop());
            if (daohs.update(h)) {
                JOptionPane.showMessageDialog(view, "Sửa danh mục thành công!");
               
            } else {
                JOptionPane.showMessageDialog(view, "Sửa thất bại!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            }
            loadhs();
            
        });
        view.btnxoa.addActionListener(e -> {
             hs h=new hs();
               hs_dao  daohs= new hs_dao();
                    h = view.lstHocSinh.getSelectedValue();
                if (h == null) {
                    JOptionPane.showMessageDialog(view, "Chon sinh vien!");
                    return;
                }

                int kq = JOptionPane.showConfirmDialog(
                    view,
                    "Ban co muon xoa sinh vien nay?",
                    "Xac nhan",
                    JOptionPane.YES_NO_OPTION
                );

                if (kq == JOptionPane.YES_OPTION) {
                    daohs.delete(h.getMahs());

                    view.modelHocSinh.clear();
                   loadhs();
                }
            });
    
    
    }
    
    
    
}
