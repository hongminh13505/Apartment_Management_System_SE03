package com.apartment.service;

import com.apartment.entity.DichVu;
import com.apartment.repository.DichVuRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Optional;

@Service
public class DichVuService {
    
    @Autowired
    private DichVuRepository dichVuRepository;
    
    public List<DichVu> findAll() {
        return dichVuRepository.findAll();
    }
    
    public Optional<DichVu> findById(Integer id) {
        return dichVuRepository.findById(id);
    }
    
    public List<DichVu> findAllActive() {
        return dichVuRepository.findAllActive();
    }
    
    public List<DichVu> findByLoaiDichVu(String loaiDichVu) {
        return dichVuRepository.findByLoaiDichVu(loaiDichVu);
    }
    
    public List<DichVu> searchByName(String tenDichVu) {
        return dichVuRepository.findByTenDichVuContainingIgnoreCase(tenDichVu);
    }
    
    @Transactional
    public DichVu save(DichVu dichVu) {
        return dichVuRepository.save(dichVu);
    }
    
    @Transactional
    public void delete(Integer id) {
        dichVuRepository.deleteById(id);
    }
}


