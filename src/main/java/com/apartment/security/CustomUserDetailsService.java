package com.apartment.security;

import com.apartment.entity.DoiTuong;
import com.apartment.repository.DoiTuongRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {
    
    private final DoiTuongRepository doiTuongRepository;
    
    @Override
    public UserDetails loadUserByUsername(String cccd) throws UsernameNotFoundException {
        DoiTuong doiTuong = doiTuongRepository.findByCccd(cccd)
                .orElseThrow(() -> new UsernameNotFoundException("Không tìm thấy người dùng với CCCD: " + cccd));
        
        if (!"hoat_dong".equals(doiTuong.getTrangThaiTaiKhoan())) {
            throw new UsernameNotFoundException("Tài khoản không hoạt động");
        }
        
        List<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority("ROLE_" + doiTuong.getVaiTro().toUpperCase()));
        
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


