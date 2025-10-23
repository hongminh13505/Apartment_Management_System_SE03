package com.apartment.repository;

import com.apartment.entity.DichVu;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface DichVuRepository extends JpaRepository<DichVu, Integer> {
    
    List<DichVu> findByTrangThai(String trangThai);
    
    List<DichVu> findByLoaiDichVu(String loaiDichVu);
    
    List<DichVu> findByTenDichVuContainingIgnoreCase(String tenDichVu);
    
    @Query("SELECT dv FROM DichVu dv WHERE dv.trangThai = 'hoat_dong'")
    List<DichVu> findAllActive();
}


