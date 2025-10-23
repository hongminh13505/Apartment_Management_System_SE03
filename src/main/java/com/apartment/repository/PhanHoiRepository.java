package com.apartment.repository;

import com.apartment.entity.PhanHoi;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface PhanHoiRepository extends JpaRepository<PhanHoi, Integer> {
    
    List<PhanHoi> findByMaThongBao(Integer maThongBao);
    
    List<PhanHoi> findByCccdNguoiDung(String cccdNguoiDung);
}


