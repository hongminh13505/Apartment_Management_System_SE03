package com.apartment.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Entity
@Table(name = "thong_bao_ho")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ThongBaoHo {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;
    
    @Column(name = "ma_thong_bao", nullable = false)
    private Integer maThongBao;
    
    @Column(name = "ma_ho", nullable = false, length = 20)
    private String maHo;
    
    @Column(name = "da_xem")
    private Boolean daXem = false;
    
    @Column(name = "ngay_xem")
    private LocalDateTime ngayXem;
    
    @ManyToOne
    @JoinColumn(name = "ma_thong_bao", referencedColumnName = "ma_thong_bao", insertable = false, updatable = false)
    private ThongBao thongBao;
    
    @ManyToOne
    @JoinColumn(name = "ma_ho", referencedColumnName = "ma_ho", insertable = false, updatable = false)
    private HoGiaDinh hoGiaDinh;
}


