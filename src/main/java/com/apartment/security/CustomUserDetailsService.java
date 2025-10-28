package com.apartment.security;

import com.apartment.entity.DoiTuong;
import com.apartment.repository.DoiTuongRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class CustomUserDetailsService implements UserDetailsService {
    
    @Autowired
    private DoiTuongRepository doiTuongRepository;
    
    @Override
    @Transactional
    public UserDetails loadUserByUsername(String cccd) throws UsernameNotFoundException {
        DoiTuong doiTuong = doiTuongRepository.findByCccd(cccd)
                .orElseThrow(() -> new UsernameNotFoundException("Không tìm thấy người dùng với CCCD: " + cccd));
        
        if (!"hoat_dong".equals(doiTuong.getTrangThaiTaiKhoan())) {
            throw new UsernameNotFoundException("Tài khoản không hoạt động");
        }
        
        // Cập nhật thời gian đăng nhập cuối
        doiTuong.setLanDangNhapCuoi(LocalDateTime.now());
        doiTuongRepository.save(doiTuong);
        
        List<GrantedAuthority> authorities = new ArrayList<>();
        String role = "ROLE_" + doiTuong.getVaiTro().toUpperCase();
        authorities.add(new SimpleGrantedAuthority(role));
        
        System.out.println("User: " + doiTuong.getCccd() + ", Role: " + role + ", Last login updated");
        
        return User.builder()
                .username(doiTuong.getCccd())
                .password(doiTuong.getMatKhau())
                .authorities(authorities)
                .accountExpired(false)
                .accountLocked(!"hoat_dong".equals(doiTuong.getTrangThaiTaiKhoan()))
                .credentialsExpired(false)
                .disabled(!"hoat_dong".equals(doiTuong.getTrangThaiTaiKhoan()))
                .build();
    }
}


