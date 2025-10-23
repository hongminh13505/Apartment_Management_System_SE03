package com.apartment.repository;

import com.apartment.entity.ThongBao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface ThongBaoRepository extends JpaRepository<ThongBao, Integer> {
    
    @Query("SELECT tb FROM ThongBao tb WHERE tb.trangThai = 'hien' ORDER BY tb.ngayTaoThongBao DESC")
    List<ThongBao> findAllVisible();
    
    List<ThongBao> findByLoaiThongBao(String loaiThongBao);
    
    @Query("SELECT tb FROM ThongBao tb WHERE tb.doiTuongNhan = 'tat_ca' AND tb.trangThai = 'hien' ORDER BY tb.ngayTaoThongBao DESC")
    List<ThongBao> findPublicNotifications();
}


