package com.apartment.repository;

import com.apartment.entity.DoiTuong;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface DoiTuongRepository extends JpaRepository<DoiTuong, String> {
    
    Optional<DoiTuong> findByCccd(String cccd);
    
    @Query("SELECT d FROM DoiTuong d WHERE d.vaiTro = :vaiTro AND d.trangThaiTaiKhoan = 'hoat_dong'")
    List<DoiTuong> findByVaiTroAndActive(String vaiTro);
    
    @Query("SELECT d FROM DoiTuong d WHERE d.laCuDan = true AND d.trangThaiDanCu = 'o_chung_cu'")
    List<DoiTuong> findAllActiveCuDan();
    
    List<DoiTuong> findByHoVaTenContainingIgnoreCase(String hoVaTen);
    
    Optional<DoiTuong> findBySoDienThoai(String soDienThoai);
    
    Optional<DoiTuong> findByEmail(String email);
    
    @Query("SELECT COUNT(d) FROM DoiTuong d WHERE d.laCuDan = true")
    Long countCuDan();
}


