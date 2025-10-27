package com.apartment.entity;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "chi_so_dien_nuoc")
public class ChiSoDienNuoc {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ma_chi_so")
    private Long maChiSo;

    @Column(name = "ma_ho", nullable = false, length = 20)
    private String maHo;

    @Column(name = "ma_hoa_don")
    private Integer maHoaDon;

    // Kỳ thanh toán định dạng YYYY-MM
    @Column(name = "ky_thanh_toan", nullable = false, length = 7)
    private String kyThanhToan;

    @Column(name = "dien_cu")
    private Integer dienCu;

    @Column(name = "dien_moi")
    private Integer dienMoi;

    @Column(name = "nuoc_cu")
    private Integer nuocCu;

    @Column(name = "nuoc_moi")
    private Integer nuocMoi;

    @Column(name = "don_gia_dien", precision = 15, scale = 2)
    private BigDecimal donGiaDien = new BigDecimal("3500");

    @Column(name = "don_gia_nuoc", precision = 15, scale = 2)
    private BigDecimal donGiaNuoc = new BigDecimal("10000");

    @Column(name = "tien_dien", precision = 15, scale = 2)
    private BigDecimal tienDien;

    @Column(name = "tien_nuoc", precision = 15, scale = 2)
    private BigDecimal tienNuoc;

    @Column(name = "tien_dich_vu", precision = 15, scale = 2)
    private BigDecimal tienDichVu = BigDecimal.ZERO;

    @Column(name = "tong_tien", precision = 15, scale = 2)
    private BigDecimal tongTien;

    @Column(name = "ngay_nhap")
    private LocalDateTime ngayNhap;

    @PrePersist
    @PreUpdate
    protected void onPersist() {
        if (dienCu == null) dienCu = 0;
        if (nuocCu == null) nuocCu = 0;
        int soDien = Math.max(0, (dienMoi != null ? dienMoi : 0) - (dienCu != null ? dienCu : 0));
        int soNuoc = Math.max(0, (nuocMoi != null ? nuocMoi : 0) - (nuocCu != null ? nuocCu : 0));
        if (tienDichVu == null) tienDichVu = BigDecimal.ZERO;
        if (donGiaDien == null) donGiaDien = new BigDecimal("3500");
        if (donGiaNuoc == null) donGiaNuoc = new BigDecimal("10000");
        tienDien = donGiaDien.multiply(BigDecimal.valueOf(soDien));
        tienNuoc = donGiaNuoc.multiply(BigDecimal.valueOf(soNuoc));
        tongTien = tienDien.add(tienNuoc).add(tienDichVu);
        if (ngayNhap == null) ngayNhap = LocalDateTime.now();
    }

    public Long getMaChiSo() { return maChiSo; }
    public void setMaChiSo(Long maChiSo) { this.maChiSo = maChiSo; }
    public String getMaHo() { return maHo; }
    public void setMaHo(String maHo) { this.maHo = maHo; }
    public Integer getMaHoaDon() { return maHoaDon; }
    public void setMaHoaDon(Integer maHoaDon) { this.maHoaDon = maHoaDon; }
    public String getKyThanhToan() { return kyThanhToan; }
    public void setKyThanhToan(String kyThanhToan) { this.kyThanhToan = kyThanhToan; }
    public Integer getDienCu() { return dienCu; }
    public void setDienCu(Integer dienCu) { this.dienCu = dienCu; }
    public Integer getDienMoi() { return dienMoi; }
    public void setDienMoi(Integer dienMoi) { this.dienMoi = dienMoi; }
    public Integer getNuocCu() { return nuocCu; }
    public void setNuocCu(Integer nuocCu) { this.nuocCu = nuocCu; }
    public Integer getNuocMoi() { return nuocMoi; }
    public void setNuocMoi(Integer nuocMoi) { this.nuocMoi = nuocMoi; }
    public BigDecimal getDonGiaDien() { return donGiaDien; }
    public void setDonGiaDien(BigDecimal donGiaDien) { this.donGiaDien = donGiaDien; }
    public BigDecimal getDonGiaNuoc() { return donGiaNuoc; }
    public void setDonGiaNuoc(BigDecimal donGiaNuoc) { this.donGiaNuoc = donGiaNuoc; }
    public BigDecimal getTienDien() { return tienDien; }
    public void setTienDien(BigDecimal tienDien) { this.tienDien = tienDien; }
    public BigDecimal getTienNuoc() { return tienNuoc; }
    public void setTienNuoc(BigDecimal tienNuoc) { this.tienNuoc = tienNuoc; }
    public BigDecimal getTienDichVu() { return tienDichVu; }
    public void setTienDichVu(BigDecimal tienDichVu) { this.tienDichVu = tienDichVu; }
    public BigDecimal getTongTien() { return tongTien; }
    public void setTongTien(BigDecimal tongTien) { this.tongTien = tongTien; }
    public LocalDateTime getNgayNhap() { return ngayNhap; }
    public void setNgayNhap(LocalDateTime ngayNhap) { this.ngayNhap = ngayNhap; }
}


