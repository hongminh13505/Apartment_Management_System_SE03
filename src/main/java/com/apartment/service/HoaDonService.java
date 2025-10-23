package com.apartment.service;

import com.apartment.entity.HoaDon;
import com.apartment.repository.HoaDonRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class HoaDonService {
    
    private final HoaDonRepository hoaDonRepository;
    
    public List<HoaDon> findAll() {
        return hoaDonRepository.findAll();
    }
    
    public Optional<HoaDon> findById(Integer id) {
        return hoaDonRepository.findById(id);
    }
    
    public List<HoaDon> findByMaHo(String maHo) {
        return hoaDonRepository.findByMaHo(maHo);
    }
    
    public List<HoaDon> findUnpaidByMaHo(String maHo) {
        return hoaDonRepository.findUnpaidByMaHo(maHo);
    }
    
    public List<HoaDon> findByTrangThai(String trangThai) {
        return hoaDonRepository.findByTrangThai(trangThai);
    }
    
    @Transactional
    public HoaDon save(HoaDon hoaDon) {
        return hoaDonRepository.save(hoaDon);
    }
    
    @Transactional
    public void delete(Integer id) {
        hoaDonRepository.deleteById(id);
    }
    
    public BigDecimal sumPaidAmount() {
        BigDecimal sum = hoaDonRepository.sumPaidAmount();
        return sum != null ? sum : BigDecimal.ZERO;
    }
    
    public BigDecimal sumUnpaidAmount() {
        BigDecimal sum = hoaDonRepository.sumUnpaidAmount();
        return sum != null ? sum : BigDecimal.ZERO;
    }
}


