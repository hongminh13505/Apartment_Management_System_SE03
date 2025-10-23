package com.apartment.repository;

import com.apartment.entity.DangKyDichVu;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface DangKyDichVuRepository extends JpaRepository<DangKyDichVu, Integer> {
    
    List<DangKyDichVu> findByCccdNguoiDung(String cccdNguoiDung);
    
    List<DangKyDichVu> findByTrangThai(String trangThai);
    
    @Query("SELECT dk FROM DangKyDichVu dk WHERE dk.trangThai = 'cho_duyet' ORDER BY dk.ngayDangKy DESC")
    List<DangKyDichVu> findPendingRegistrations();
}


