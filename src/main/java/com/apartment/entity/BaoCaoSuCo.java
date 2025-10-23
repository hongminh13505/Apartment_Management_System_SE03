package com.apartment.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "bao_cao_su_co")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class BaoCaoSuCo {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ma_bao_cao")
    private Integer maBaoCao;
    
    @Column(name = "cccd_nguoi_bao_cao", nullable = false, length = 12)
    private String cccdNguoiBaoCao;
    
    @Column(name = "cccd_nguoi_nhap", length = 12)
    private String cccdNguoiNhap;
    
    @Column(name = "tieu_de", nullable = false, length = 200)
    private String tieuDe;
    
    @Column(name = "mo_ta_su_co", nullable = false, columnDefinition = "TEXT")
    private String moTaSuCo;
    
    @Column(name = "vi_tri_su_co", columnDefinition = "TEXT")
    private String viTriSuCo;
    
    @Column(name = "muc_do_uu_tien", length = 20)
    private String mucDoUuTien = "binh_thuong";
    
    @Column(name = "phuong_thuc_bao_cao", length = 20)
    private String phuongThucBaoCao = "truc_tuyen";
    
    @Column(name = "ngay_bao_cao")
    private LocalDateTime ngayBaoCao;
    
    @Column(name = "trang_thai", length = 20)
    private String trangThai = "moi_tiep_nhan";
    
    @Column(name = "cccd_nguoi_xu_ly", length = 12)
    private String cccdNguoiXuLy;
    
    @Column(name = "ngay_bat_dau_xu_ly")
    private LocalDateTime ngayBatDauXuLy;
    
    @Column(name = "ngay_hoan_thanh")
    private LocalDateTime ngayHoanThanh;
    
    @Column(name = "ket_qua_xu_ly", columnDefinition = "TEXT")
    private String ketQuaXuLy;
    
    @Column(name = "chi_phi_xu_ly", precision = 15, scale = 2)
    private BigDecimal chiPhiXuLy = BigDecimal.ZERO;
    
    @Column(name = "ghi_chu", columnDefinition = "TEXT")
    private String ghiChu;
    
    @ManyToOne
    @JoinColumn(name = "cccd_nguoi_bao_cao", referencedColumnName = "cccd", insertable = false, updatable = false)
    private DoiTuong nguoiBaoCao;
    
    @ManyToOne
    @JoinColumn(name = "cccd_nguoi_nhap", referencedColumnName = "cccd", insertable = false, updatable = false)
    private DoiTuong nguoiNhap;
    
    @ManyToOne
    @JoinColumn(name = "cccd_nguoi_xu_ly", referencedColumnName = "cccd", insertable = false, updatable = false)
    private DoiTuong nguoiXuLy;
    
    @PrePersist
    protected void onCreate() {
        ngayBaoCao = LocalDateTime.now();
    }
}


