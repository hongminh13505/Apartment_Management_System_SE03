package com.apartment.service;

import com.apartment.entity.ThongBao;
import com.apartment.repository.ThongBaoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Optional;

@Service
public class ThongBaoService {
    
    @Autowired
    private ThongBaoRepository thongBaoRepository;
    
    public List<ThongBao> findAll() {
        return thongBaoRepository.findAll();
    }
    
    public Optional<ThongBao> findById(Integer id) {
        return thongBaoRepository.findById(id);
    }
    
    public List<ThongBao> findAllVisible() {
        return thongBaoRepository.findAllVisible();
    }
    
    public List<ThongBao> findPublicNotifications() {
        return thongBaoRepository.findPublicNotifications();
    }
    
    @Transactional
    public ThongBao save(ThongBao thongBao) {
        return thongBaoRepository.save(thongBao);
    }
    
    @Transactional
    public void delete(Integer id) {
        thongBaoRepository.deleteById(id);
    }
}


