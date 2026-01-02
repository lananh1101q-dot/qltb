package com.mycompany.qltb;

public class Lop {
    private String maLop;
    private String tenLop;
    private String gvcn;

    public Lop() {
    }

    public Lop(String maLop, String tenLop, String gvcn) {
        this.maLop = maLop;
        this.tenLop = tenLop;
        this.gvcn = gvcn;
    }

    public String getMaLop() {
        return maLop;
    }

    public void setMaLop(String maLop) {
        this.maLop = maLop;
    }

    public String getTenLop() {
        return tenLop;
    }

    public void setTenLop(String tenLop) {
        this.tenLop = tenLop;
    }

    public String getGvcn() {
        return gvcn;
    }

    public void setGvcn(String gvcn) {
        this.gvcn = gvcn;
    }
    
    @Override
    public String toString() {
        return tenLop; // Dùng để hiển thị trên ComboBox nếu cần
    }
}