/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.qltb;

/**
 *
 * @author LanAnh
 */
public class hs {
    private String mahs;
    private String tenhs;
    private String malop;

    public hs() {
    }

    public hs(String mahs, String tenhs, String malop) {
        this.mahs = mahs;
        this.tenhs = tenhs;
        this.malop = malop;
    }

    public String getMahs() {
        return mahs;
    }

    public String getMalop() {
        return malop;
    }

    public String getTenhs() {
        return tenhs;
    }

    public void setMahs(String mahs) {
        this.mahs = mahs;
    }

    public void setMalop(String malop) {
        this.malop = malop;
    }

    public void setTenhs(String tenhs) {
        this.tenhs = tenhs;
    }

    @Override
    public String toString() {
        return tenhs;
    }
    
    
}
