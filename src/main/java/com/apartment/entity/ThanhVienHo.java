package com.apartment.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.io.Serializable;
import java.time.LocalDate;

@Entity
@Table(name = "thanh_vien_ho")
@Data
@NoArgsConstructor
@AllArgsConstructor
@IdClass(ThanhVienHo.ThanhVienHoId.class)
public class ThanhVienHo {
    
    @Id
    @Column(name = "cccd", length = 12)
    private String cccd;
    
    @Id
    @Column(name = "ngay_bat_dau")
    private LocalDate ngayBatDau;
    
    @Column(name = "ma_ho", nullable = false, length = 20)
    private String maHo;
    
    @Column(name = "la_chu_ho")
    private Boolean laChuHo = false;
    
    @Column(name = "quan_he_voi_chu_ho", length = 50)
    private String quanHeVoiChuHo;
    
    @Column(name = "ngay_ket_thuc")
    private LocalDate ngayKetThuc;
    
    @Column(name = "ly_do_ket_thuc", length = 100)
    private String lyDoKetThuc;
    
    @Column(name = "ghi_chu", columnDefinition = "TEXT")
    private String ghiChu;
    
    @ManyToOne
    @JoinColumn(name = "ma_ho", referencedColumnName = "ma_ho", insertable = false, updatable = false)
    private HoGiaDinh hoGiaDinh;
    
    @ManyToOne
    @JoinColumn(name = "cccd", referencedColumnName = "cccd", insertable = false, updatable = false)
    private DoiTuong doiTuong;
    
    // Composite Key Class
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ThanhVienHoId implements Serializable {
        private String cccd;
        private LocalDate ngayBatDau;
    }
    
    @PrePersist
    protected void onCreate() {
        if (ngayBatDau == null) {
            ngayBatDau = LocalDate.now();
        }
    }
}


