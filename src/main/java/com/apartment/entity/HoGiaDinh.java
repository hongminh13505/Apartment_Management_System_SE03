package com.apartment.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "ho_gia_dinh")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class HoGiaDinh {
    
    @Id
    @Column(name = "ma_ho", length = 20)
    private String maHo;
    
    @Column(name = "ten_ho", nullable = false, length = 100)
    private String tenHo;
    
    @Column(name = "ma_can_ho", unique = true)
    private Integer maCanHo;
    
    @Column(name = "ngay_thanh_lap")
    private LocalDate ngayThanhLap;
    
    @Column(name = "trang_thai", length = 20)
    private String trangThai = "hoat_dong";
    
    @Column(name = "ghi_chu", columnDefinition = "TEXT")
    private String ghiChu;
    
    @Column(name = "ngay_cap_nhat")
    private LocalDateTime ngayCapNhat;
    
    @PrePersist
    protected void onCreate() {
        if (ngayThanhLap == null) {
            ngayThanhLap = LocalDate.now();
        }
        ngayCapNhat = LocalDateTime.now();
    }
    
    @PreUpdate
    protected void onUpdate() {
        ngayCapNhat = LocalDateTime.now();
    }

    // ---- Explicit getters and setters to ensure runtime property access ----
    public String getMaHo() {
        return maHo;
    }

    public void setMaHo(String maHo) {
        this.maHo = maHo;
    }

    public String getTenHo() {
        return tenHo;
    }

    public void setTenHo(String tenHo) {
        this.tenHo = tenHo;
    }

    public Integer getMaCanHo() {
        return maCanHo;
    }

    public void setMaCanHo(Integer maCanHo) {
        this.maCanHo = maCanHo;
    }

    public LocalDate getNgayThanhLap() {
        return ngayThanhLap;
    }

    public void setNgayThanhLap(LocalDate ngayThanhLap) {
        this.ngayThanhLap = ngayThanhLap;
    }

    public String getTrangThai() {
        return trangThai;
    }

    public void setTrangThai(String trangThai) {
        this.trangThai = trangThai;
    }

    public String getGhiChu() {
        return ghiChu;
    }

    public void setGhiChu(String ghiChu) {
        this.ghiChu = ghiChu;
    }

    public LocalDateTime getNgayCapNhat() {
        return ngayCapNhat;
    }

    public void setNgayCapNhat(LocalDateTime ngayCapNhat) {
        this.ngayCapNhat = ngayCapNhat;
    }
}


