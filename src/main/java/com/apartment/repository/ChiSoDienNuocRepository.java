package com.apartment.repository;

import com.apartment.entity.ChiSoDienNuoc;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ChiSoDienNuocRepository extends JpaRepository<ChiSoDienNuoc, Long> {
    Optional<ChiSoDienNuoc> findByMaHoAndKyThanhToan(String maHo, String kyThanhToan);

    @Query("SELECT c FROM ChiSoDienNuoc c WHERE (:maHo IS NULL OR c.maHo = :maHo) AND (:ky IS NULL OR c.kyThanhToan = :ky) ORDER BY c.ngayNhap DESC")
    List<ChiSoDienNuoc> search(String maHo, String ky);
}


