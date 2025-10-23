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
    
    // Manual getter/setter methods for Lombok compatibility
    public Integer getMaBaoCao() {
        return maBaoCao;
    }
    
    public void setMaBaoCao(Integer maBaoCao) {
        this.maBaoCao = maBaoCao;
    }
    
    public String getCccdNguoiBaoCao() {
        return cccdNguoiBaoCao;
    }
    
    public void setCccdNguoiBaoCao(String cccdNguoiBaoCao) {
        this.cccdNguoiBaoCao = cccdNguoiBaoCao;
    }
    
    public String getCccdNguoiNhap() {
        return cccdNguoiNhap;
    }
    
    public void setCccdNguoiNhap(String cccdNguoiNhap) {
        this.cccdNguoiNhap = cccdNguoiNhap;
    }
    
    public String getTieuDe() {
        return tieuDe;
    }
    
    public void setTieuDe(String tieuDe) {
        this.tieuDe = tieuDe;
    }
    
    public String getMoTaSuCo() {
        return moTaSuCo;
    }
    
    public void setMoTaSuCo(String moTaSuCo) {
        this.moTaSuCo = moTaSuCo;
    }
    
    public String getViTriSuCo() {
        return viTriSuCo;
    }
    
    public void setViTriSuCo(String viTriSuCo) {
        this.viTriSuCo = viTriSuCo;
    }
    
    public String getMucDoUuTien() {
        return mucDoUuTien;
    }
    
    public void setMucDoUuTien(String mucDoUuTien) {
        this.mucDoUuTien = mucDoUuTien;
    }
    
    public String getPhuongThucBaoCao() {
        return phuongThucBaoCao;
    }
    
    public void setPhuongThucBaoCao(String phuongThucBaoCao) {
        this.phuongThucBaoCao = phuongThucBaoCao;
    }
    
    public LocalDateTime getNgayBaoCao() {
        return ngayBaoCao;
    }
    
    public void setNgayBaoCao(LocalDateTime ngayBaoCao) {
        this.ngayBaoCao = ngayBaoCao;
    }
    
    public String getTrangThai() {
        return trangThai;
    }
    
    public void setTrangThai(String trangThai) {
        this.trangThai = trangThai;
    }
    
    public String getCccdNguoiXuLy() {
        return cccdNguoiXuLy;
    }
    
    public void setCccdNguoiXuLy(String cccdNguoiXuLy) {
        this.cccdNguoiXuLy = cccdNguoiXuLy;
    }
    
    public LocalDateTime getNgayBatDauXuLy() {
        return ngayBatDauXuLy;
    }
    
    public void setNgayBatDauXuLy(LocalDateTime ngayBatDauXuLy) {
        this.ngayBatDauXuLy = ngayBatDauXuLy;
    }
    
    public LocalDateTime getNgayHoanThanh() {
        return ngayHoanThanh;
    }
    
    public void setNgayHoanThanh(LocalDateTime ngayHoanThanh) {
        this.ngayHoanThanh = ngayHoanThanh;
    }
    
    public String getKetQuaXuLy() {
        return ketQuaXuLy;
    }
    
    public void setKetQuaXuLy(String ketQuaXuLy) {
        this.ketQuaXuLy = ketQuaXuLy;
    }
    
    public BigDecimal getChiPhiXuLy() {
        return chiPhiXuLy;
    }
    
    public void setChiPhiXuLy(BigDecimal chiPhiXuLy) {
        this.chiPhiXuLy = chiPhiXuLy;
    }
    
    public String getGhiChu() {
        return ghiChu;
    }
    
    public void setGhiChu(String ghiChu) {
        this.ghiChu = ghiChu;
    }
    
    public DoiTuong getNguoiBaoCao() {
        return nguoiBaoCao;
    }
    
    public void setNguoiBaoCao(DoiTuong nguoiBaoCao) {
        this.nguoiBaoCao = nguoiBaoCao;
    }
    
    public DoiTuong getNguoiNhap() {
        return nguoiNhap;
    }
    
    public void setNguoiNhap(DoiTuong nguoiNhap) {
        this.nguoiNhap = nguoiNhap;
    }
    
    public DoiTuong getNguoiXuLy() {
        return nguoiXuLy;
    }
    
    public void setNguoiXuLy(DoiTuong nguoiXuLy) {
        this.nguoiXuLy = nguoiXuLy;
    }
}


