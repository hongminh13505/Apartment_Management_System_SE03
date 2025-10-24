package com.apartment.service;

import com.apartment.entity.ChiSoDienNuoc;
import com.apartment.repository.ChiSoDienNuocRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class ChiSoDienNuocService {

    @Autowired
    private ChiSoDienNuocRepository repository;

    public List<ChiSoDienNuoc> search(String maHo, String ky) {
        return repository.search(maHo, ky);
    }

    public Optional<ChiSoDienNuoc> findOne(String maHo, String ky) {
        return repository.findByMaHoAndKyThanhToan(maHo, ky);
    }

    @Transactional
    public ChiSoDienNuoc save(ChiSoDienNuoc cs) {
        return repository.save(cs);
    }
}


