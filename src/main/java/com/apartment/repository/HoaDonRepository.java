package com.apartment.repository;

import com.apartment.entity.HoaDon;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.math.BigDecimal;
import java.util.List;

@Repository
public interface HoaDonRepository extends JpaRepository<HoaDon, Integer> {
    
    List<HoaDon> findByMaHo(String maHo);
    
    List<HoaDon> findByTrangThai(String trangThai);
    
    @Query("SELECT hd FROM HoaDon hd WHERE hd.maHo = :maHo AND hd.trangThai = 'chua_thanh_toan'")
    List<HoaDon> findUnpaidByMaHo(String maHo);
    
    @Query("SELECT SUM(hd.soTien) FROM HoaDon hd WHERE hd.trangThai = 'da_thanh_toan'")
    BigDecimal sumPaidAmount();
    
    @Query("SELECT SUM(hd.soTien) FROM HoaDon hd WHERE hd.trangThai = 'chua_thanh_toan'")
    BigDecimal sumUnpaidAmount();
}


