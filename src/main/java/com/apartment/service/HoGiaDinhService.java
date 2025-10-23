package com.apartment.service;

import com.apartment.entity.HoGiaDinh;
import com.apartment.repository.HoGiaDinhRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class HoGiaDinhService {
    
    private final HoGiaDinhRepository hoGiaDinhRepository;
    
    public List<HoGiaDinh> findAll() {
        return hoGiaDinhRepository.findAll();
    }
    
    public Optional<HoGiaDinh> findByMaHo(String maHo) {
        return hoGiaDinhRepository.findByMaHo(maHo);
    }
    
    public List<HoGiaDinh> findByTrangThai(String trangThai) {
        return hoGiaDinhRepository.findByTrangThai(trangThai);
    }
    
    public List<HoGiaDinh> searchByName(String tenHo) {
        return hoGiaDinhRepository.findByTenHoContainingIgnoreCase(tenHo);
    }
    
    @Transactional
    public HoGiaDinh save(HoGiaDinh hoGiaDinh) {
        return hoGiaDinhRepository.save(hoGiaDinh);
    }
    
    @Transactional
    public void delete(String maHo) {
        hoGiaDinhRepository.deleteById(maHo);
    }
    
    public Long countActiveHo() {
        return hoGiaDinhRepository.countActiveHo();
    }
}


