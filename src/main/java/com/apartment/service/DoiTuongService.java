package com.apartment.service;

import com.apartment.entity.DoiTuong;
import com.apartment.repository.DoiTuongRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class DoiTuongService {
    
    @Autowired
    private DoiTuongRepository doiTuongRepository;
    
    @Autowired
    private PasswordEncoder passwordEncoder;
    
    public List<DoiTuong> findAll() {
        return doiTuongRepository.findAll();
    }
    
    public Optional<DoiTuong> findByCccd(String cccd) {
        return doiTuongRepository.findByCccd(cccd);
    }
    
    public List<DoiTuong> findAllActiveCuDan() {
        return doiTuongRepository.findAllActiveCuDan();
    }
    
    public List<DoiTuong> findByVaiTroAndActive(String vaiTro) {
        return doiTuongRepository.findByVaiTroAndActive(vaiTro);
    }
    
    public List<DoiTuong> searchByName(String hoVaTen) {
        return doiTuongRepository.findByHoVaTenContainingIgnoreCase(hoVaTen);
    }
    
    @Transactional
    public DoiTuong save(DoiTuong doiTuong) {
        // Mã hóa mật khẩu nếu là tạo mới hoặc thay đổi mật khẩu
        if (doiTuong.getMatKhau() != null && !doiTuong.getMatKhau().startsWith("$2a$")) {
            doiTuong.setMatKhau(passwordEncoder.encode(doiTuong.getMatKhau()));
        }
        return doiTuongRepository.save(doiTuong);
    }
    
    @Transactional
    public void updateLastLogin(String cccd) {
        doiTuongRepository.findByCccd(cccd).ifPresent(doiTuong -> {
            doiTuong.setLanDangNhapCuoi(LocalDateTime.now());
            doiTuongRepository.save(doiTuong);
        });
    }
    
    @Transactional
    public void delete(String cccd) {
        doiTuongRepository.deleteById(cccd);
    }
    
    public Long countCuDan() {
        return doiTuongRepository.countCuDan();
    }
}


