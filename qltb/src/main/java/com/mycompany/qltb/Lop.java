package com.mycompany.qltb;

import java.util.Objects;

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
 @Override
public boolean equals(Object obj) {
    if (obj instanceof Lop) {
        Lop other = (Lop) obj;
        return this.maLop != null && other.maLop != null 
               && this.maLop.trim().equals(other.maLop.trim());
    }
    return false;
}


}