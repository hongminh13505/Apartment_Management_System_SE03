package com.apartment.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Entity
@Table(name = "phan_hoi")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PhanHoi {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ma_phan_hoi")
    private Integer maPhanHoi;
    
    @Column(name = "cccd_nguoi_dung", nullable = false, length = 12)
    private String cccdNguoiDung;
    
    @Column(name = "ma_thong_bao", nullable = false)
    private Integer maThongBao;
    
    @Column(name = "noi_dung_phan_hoi", nullable = false, columnDefinition = "TEXT")
    private String noiDungPhanHoi;
    
    @Column(name = "ngay_phan_hoi")
    private LocalDateTime ngayPhanHoi;
    
    @ManyToOne
    @JoinColumn(name = "cccd_nguoi_dung", referencedColumnName = "cccd", insertable = false, updatable = false)
    private DoiTuong nguoiDung;
    
    @ManyToOne
    @JoinColumn(name = "ma_thong_bao", referencedColumnName = "ma_thong_bao", insertable = false, updatable = false)
    private ThongBao thongBao;
    
    @PrePersist
    protected void onCreate() {
        ngayPhanHoi = LocalDateTime.now();
    }
}


