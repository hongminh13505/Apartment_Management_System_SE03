package com.apartment.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Entity
@Table(name = "thong_bao")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ThongBao {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ma_thong_bao")
    private Integer maThongBao;
    
    @Column(name = "cccd_ban_quan_tri", nullable = false, length = 12)
    private String cccdBanQuanTri;
    
    @Column(name = "tieu_de", nullable = false, length = 200)
    private String tieuDe;
    
    @Column(name = "noi_dung_thong_bao", nullable = false, columnDefinition = "TEXT")
    private String noiDungThongBao;
    
    @Column(name = "ngay_tao_thong_bao")
    private LocalDateTime ngayTaoThongBao;
    
    @Column(name = "trang_thai", length = 20)
    private String trangThai = "hien";
    
    @Column(name = "loai_thong_bao", length = 30)
    private String loaiThongBao = "binh_thuong";
    
    @Column(name = "doi_tuong_nhan", length = 20)
    private String doiTuongNhan = "tat_ca";
    
    @ManyToOne
    @JoinColumn(name = "cccd_ban_quan_tri", referencedColumnName = "cccd", insertable = false, updatable = false)
    private DoiTuong banQuanTri;
    
    @PrePersist
    protected void onCreate() {
        ngayTaoThongBao = LocalDateTime.now();
    }
}


