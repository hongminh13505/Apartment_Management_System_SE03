package com.apartment.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "tai_san_chung_cu")
@NoArgsConstructor
@AllArgsConstructor
public class TaiSanChungCu {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ma_tai_san")
    private Integer maTaiSan;
    
    @Column(name = "ten_tai_san", nullable = false, length = 100)
    private String tenTaiSan;
    
    @Column(name = "loai_tai_san", nullable = false, length = 30)
    private String loaiTaiSan;
    
    @Column(name = "ma_ho", length = 20)
    private String maHo;
    
    @Column(name = "trang_thai", length = 20)
    private String trangThai;
    
    @Column(name = "dien_tich", precision = 10, scale = 2)
    private BigDecimal dienTich;
    
    @Column(name = "vi_tri", columnDefinition = "TEXT")
    private String viTri;
    
    @Column(name = "gia_tri", precision = 15, scale = 2)
    private BigDecimal giaTri;
    
    @Column(name = "ngay_them")
    private LocalDateTime ngayThem;
    
    @PrePersist
    protected void onCreate() {
        ngayThem = LocalDateTime.now();
        if (trangThai == null) {
            trangThai = "hoat_dong";
        }
    }
    
    public Integer getMaTaiSan() {
        return maTaiSan;
    }
    
    public void setMaTaiSan(Integer maTaiSan) {
        this.maTaiSan = maTaiSan;
    }
    
    public String getTenTaiSan() {
        return tenTaiSan;
    }
    
    public void setTenTaiSan(String tenTaiSan) {
        this.tenTaiSan = tenTaiSan;
    }
    
    public String getLoaiTaiSan() {
        return loaiTaiSan;
    }
    
    public void setLoaiTaiSan(String loaiTaiSan) {
        this.loaiTaiSan = loaiTaiSan;
    }
    
    public String getMaHo() {
        return maHo;
    }
    
    public void setMaHo(String maHo) {
        this.maHo = maHo;
    }
    
    public String getTrangThai() {
        return trangThai;
    }
    
    public void setTrangThai(String trangThai) {
        this.trangThai = trangThai;
    }
    
    public BigDecimal getDienTich() {
        return dienTich;
    }
    
    public void setDienTich(BigDecimal dienTich) {
        this.dienTich = dienTich;
    }
    
    public String getViTri() {
        return viTri;
    }
    
    public void setViTri(String viTri) {
        this.viTri = viTri;
    }
    
    public BigDecimal getGiaTri() {
        return giaTri;
    }
    
    public void setGiaTri(BigDecimal giaTri) {
        this.giaTri = giaTri;
    }
    
    public LocalDateTime getNgayThem() {
        return ngayThem;
    }
    
    public void setNgayThem(LocalDateTime ngayThem) {
        this.ngayThem = ngayThem;
    }
}


