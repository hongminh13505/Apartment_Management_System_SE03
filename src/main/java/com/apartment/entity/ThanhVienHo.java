package com.apartment.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDate;

@Entity
@Table(name = "thanh_vien_ho")
@NoArgsConstructor
@AllArgsConstructor
@IdClass(ThanhVienHo.ThanhVienHoId.class)
public class ThanhVienHo {
    
    @Id
    @Column(name = "cccd", length = 12)
    private String cccd;
    
    @Id
    @Column(name = "ngay_bat_dau")
    private LocalDate ngayBatDau;
    
    @Column(name = "ma_ho", nullable = false, length = 20)
    private String maHo;
    
    @Column(name = "la_chu_ho")
    private Boolean laChuHo = false;
    
    @Column(name = "quan_he_voi_chu_ho", length = 50)
    private String quanHeVoiChuHo;
    
    @Column(name = "ngay_ket_thuc")
    private LocalDate ngayKetThuc;
    
    @Column(name = "ly_do_ket_thuc", length = 100)
    private String lyDoKetThuc;
    
    @Column(name = "ghi_chu", columnDefinition = "TEXT")
    private String ghiChu;
    
    @ManyToOne
    @JoinColumn(name = "ma_ho", referencedColumnName = "ma_ho", insertable = false, updatable = false)
    private HoGiaDinh hoGiaDinh;
    
    @ManyToOne
    @JoinColumn(name = "cccd", referencedColumnName = "cccd", insertable = false, updatable = false)
    private DoiTuong doiTuong;
    
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ThanhVienHoId implements Serializable {
        private String cccd;
        private LocalDate ngayBatDau;
        
        public String getCccd() {
            return cccd;
        }
        
        public void setCccd(String cccd) {
            this.cccd = cccd;
        }
        
        public LocalDate getNgayBatDau() {
            return ngayBatDau;
        }
        
        public void setNgayBatDau(LocalDate ngayBatDau) {
            this.ngayBatDau = ngayBatDau;
        }
        
        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            ThanhVienHoId that = (ThanhVienHoId) o;
            return cccd.equals(that.cccd) && ngayBatDau.equals(that.ngayBatDau);
        }
        
        @Override
        public int hashCode() {
            return cccd.hashCode() + ngayBatDau.hashCode();
        }
    }
    
    @PrePersist
    protected void onCreate() {
        if (ngayBatDau == null) {
            ngayBatDau = LocalDate.now();
        }
    }
    
    public String getCccd() {
        return cccd;
    }
    
    public void setCccd(String cccd) {
        this.cccd = cccd;
    }
    
    public LocalDate getNgayBatDau() {
        return ngayBatDau;
    }
    
    public void setNgayBatDau(LocalDate ngayBatDau) {
        this.ngayBatDau = ngayBatDau;
    }
    
    public String getMaHo() {
        return maHo;
    }
    
    public void setMaHo(String maHo) {
        this.maHo = maHo;
    }
    
    public Boolean getLaChuHo() {
        return laChuHo;
    }
    
    public void setLaChuHo(Boolean laChuHo) {
        this.laChuHo = laChuHo;
    }
    
    public String getQuanHeVoiChuHo() {
        return quanHeVoiChuHo;
    }
    
    public void setQuanHeVoiChuHo(String quanHeVoiChuHo) {
        this.quanHeVoiChuHo = quanHeVoiChuHo;
    }
    
    public LocalDate getNgayKetThuc() {
        return ngayKetThuc;
    }
    
    public void setNgayKetThuc(LocalDate ngayKetThuc) {
        this.ngayKetThuc = ngayKetThuc;
    }
    
    public String getLyDoKetThuc() {
        return lyDoKetThuc;
    }
    
    public void setLyDoKetThuc(String lyDoKetThuc) {
        this.lyDoKetThuc = lyDoKetThuc;
    }
    
    public String getGhiChu() {
        return ghiChu;
    }
    
    public void setGhiChu(String ghiChu) {
        this.ghiChu = ghiChu;
    }
    
    public HoGiaDinh getHoGiaDinh() {
        return hoGiaDinh;
    }
    
    public void setHoGiaDinh(HoGiaDinh hoGiaDinh) {
        this.hoGiaDinh = hoGiaDinh;
    }
    
    public DoiTuong getDoiTuong() {
        return doiTuong;
    }
    
    public void setDoiTuong(DoiTuong doiTuong) {
        this.doiTuong = doiTuong;
    }
}


