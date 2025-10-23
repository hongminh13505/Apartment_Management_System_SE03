package com.apartment.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Entity
@Table(name = "anh_bao_cao_su_co")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AnhBaoCaoSuCo {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ma_anh")
    private Integer maAnh;
    
    @Column(name = "ma_bao_cao", nullable = false)
    private Integer maBaoCao;
    
    @Column(name = "duong_dan", nullable = false, columnDefinition = "TEXT")
    private String duongDan;
    
    @Column(name = "mo_ta", length = 200)
    private String moTa;
    
    @Column(name = "ngay_upload")
    private LocalDateTime ngayUpload;
    
    @ManyToOne
    @JoinColumn(name = "ma_bao_cao", referencedColumnName = "ma_bao_cao", insertable = false, updatable = false)
    private BaoCaoSuCo baoCaoSuCo;
    
    @PrePersist
    protected void onCreate() {
        ngayUpload = LocalDateTime.now();
    }
}


