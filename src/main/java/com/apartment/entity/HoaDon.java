package com.apartment.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "hoa_don")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class HoaDon {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ma_hoa_don")
    private Integer maHoaDon;
    
    @Column(name = "ma_ho", nullable = false, length = 20)
    private String maHo;
    
    @Column(name = "so_tien", nullable = false, precision = 15, scale = 2)
    private BigDecimal soTien;
    
    @Column(name = "ma_dich_vu")
    private Integer maDichVu;
    
    @Column(name = "ma_bao_cao")
    private Integer maBaoCao;
    
    @Column(name = "ma_tai_san")
    private Integer maTaiSan;
    
    @Column(name = "loai_hoa_don", length = 30)
    private String loaiHoaDon;
    
    @Column(name = "trang_thai", length = 20)
    private String trangThai = "chua_thanh_toan";
    
    @Column(name = "ngay_tao")
    private LocalDateTime ngayTao;
    
    @Column(name = "han_thanh_toan")
    private LocalDate hanThanhToan;
    
    @Column(name = "ngay_thanh_toan")
    private LocalDateTime ngayThanhToan;
    
    @Column(name = "phuong_thuc_thanh_toan", length = 30)
    private String phuongThucThanhToan;
    
    @Column(name = "ghi_chu", columnDefinition = "TEXT")
    private String ghiChu;
    
    @ManyToOne
    @JoinColumn(name = "ma_ho", referencedColumnName = "ma_ho", insertable = false, updatable = false)
    private HoGiaDinh hoGiaDinh;
    
    @ManyToOne
    @JoinColumn(name = "ma_dich_vu", referencedColumnName = "ma_dich_vu", insertable = false, updatable = false)
    private DichVu dichVu;
    
    @ManyToOne
    @JoinColumn(name = "ma_bao_cao", referencedColumnName = "ma_bao_cao", insertable = false, updatable = false)
    private BaoCaoSuCo baoCaoSuCo;
    
    @ManyToOne
    @JoinColumn(name = "ma_tai_san", referencedColumnName = "ma_tai_san", insertable = false, updatable = false)
    private TaiSanChungCu taiSan;
    
    @PrePersist
    protected void onCreate() {
        ngayTao = LocalDateTime.now();
    }
}


