package com.apartment.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "dich_vu")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DichVu {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ma_dich_vu")
    private Integer maDichVu;
    
    @Column(name = "ten_dich_vu", nullable = false, length = 100)
    private String tenDichVu;
    
    @Column(name = "cccd_ban_quan_tri", length = 12)
    private String cccdBanQuanTri;
    
    @Column(name = "mo_ta", columnDefinition = "TEXT")
    private String moTa;
    
    @Column(name = "gia_thanh", nullable = false, precision = 15, scale = 2)
    private BigDecimal giaThanh;
    
    @Column(name = "don_vi", length = 20)
    private String donVi;
    
    @Column(name = "loai_dich_vu", length = 30)
    private String loaiDichVu;
    
    @Column(name = "trang_thai", length = 20)
    private String trangThai = "hoat_dong";
    
    @Column(name = "ngay_tao")
    private LocalDateTime ngayTao;
    
    @ManyToOne
    @JoinColumn(name = "cccd_ban_quan_tri", referencedColumnName = "cccd", insertable = false, updatable = false)
    private DoiTuong banQuanTri;
    
    @PrePersist
    protected void onCreate() {
        ngayTao = LocalDateTime.now();
    }
}


