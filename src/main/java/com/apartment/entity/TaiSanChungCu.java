package com.apartment.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "tai_san_chung_cu")
@Data
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
    private String trangThai = "hoat_dong";
    
    @Column(name = "dien_tich", precision = 10, scale = 2)
    private BigDecimal dienTich;
    
    @Column(name = "vi_tri", columnDefinition = "TEXT")
    private String viTri;
    
    @Column(name = "gia_tri", precision = 15, scale = 2)
    private BigDecimal giaTri;
    
    @Column(name = "ngay_them")
    private LocalDateTime ngayThem;
    
    @ManyToOne
    @JoinColumn(name = "ma_ho", referencedColumnName = "ma_ho", insertable = false, updatable = false)
    private HoGiaDinh hoGiaDinh;
    
    @PrePersist
    protected void onCreate() {
        ngayThem = LocalDateTime.now();
    }
}


