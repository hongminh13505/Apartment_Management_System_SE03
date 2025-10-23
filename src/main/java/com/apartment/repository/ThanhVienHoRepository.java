package com.apartment.repository;

import com.apartment.entity.ThanhVienHo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface ThanhVienHoRepository extends JpaRepository<ThanhVienHo, ThanhVienHo.ThanhVienHoId> {
    
    @Query("SELECT tv FROM ThanhVienHo tv WHERE tv.maHo = :maHo AND tv.ngayKetThuc IS NULL")
    List<ThanhVienHo> findActiveByMaHo(String maHo);
    
    @Query("SELECT tv FROM ThanhVienHo tv WHERE tv.cccd = :cccd AND tv.ngayKetThuc IS NULL")
    List<ThanhVienHo> findActiveByCccd(String cccd);
    
    @Query("SELECT tv FROM ThanhVienHo tv WHERE tv.maHo = :maHo AND tv.laChuHo = true AND tv.ngayKetThuc IS NULL")
    List<ThanhVienHo> findChuHoByMaHo(String maHo);
}


