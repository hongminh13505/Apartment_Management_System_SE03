package com.apartment.security;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {
    
    @Autowired
    private CustomUserDetailsService userDetailsService;
    
    @Autowired
    private RoleValidationFilter roleValidationFilter;
    
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
    
    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService);
        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;
    }
    
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }
    
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            .authenticationProvider(authenticationProvider())
            .addFilterBefore(roleValidationFilter, UsernamePasswordAuthenticationFilter.class)
            .csrf(csrf -> csrf
                // Use Ant-style matcher to allow multiple ** segments safely
                .ignoringRequestMatchers(
                    new AntPathRequestMatcher("/admin/**/api/**"),
                    new AntPathRequestMatcher("/ke-toan/**/save"),
                    new AntPathRequestMatcher("/ke-toan/**/thanh-toan/**"),
                    new AntPathRequestMatcher("/cu-dan/**/save")
                )
            )
            .authorizeHttpRequests(auth -> auth
                .requestMatchers("/css/**", "/js/**", "/images/**", "/webjars/**").permitAll()
                .requestMatchers("/login", "/forgot-password").permitAll()
                // Cho phép kế toán truy cập trang hóa đơn trong khu vực admin
                .requestMatchers("/admin/hoa-don/**").hasAnyRole("BAN_QUAN_TRI", "KE_TOAN")
                // Cho phép kế toán xem danh sách hộ gia đình (cần để tạo hóa đơn)
                .requestMatchers("/admin/ho-gia-dinh", "/admin/ho-gia-dinh/detail/**").hasAnyRole("BAN_QUAN_TRI", "KE_TOAN")
                // Các trang admin khác chỉ cho BQT
                .requestMatchers("/admin/**").hasRole("BAN_QUAN_TRI")
                // Cho phép kế toán truy cập khu vực riêng
                .requestMatchers("/ke-toan/**").hasAnyRole("BAN_QUAN_TRI", "KE_TOAN")
                // Cho phép cư dân truy cập khu vực riêng
                .requestMatchers("/cu-dan/**").hasAnyRole("BAN_QUAN_TRI", "NGUOI_DUNG_THUONG")
                // Cho phép cơ quan chức năng truy cập khu vực riêng
                .requestMatchers("/co-quan/**").hasAnyRole("BAN_QUAN_TRI", "CO_QUAN_CHUC_NANG")
                .anyRequest().authenticated()
            )
            .formLogin(form -> form
                .loginPage("/login")
                .loginProcessingUrl("/login")
                .usernameParameter("cccd")
                .passwordParameter("password")
                .successHandler(customAuthenticationSuccessHandler())
                .failureUrl("/login?error=true")
                .permitAll()
            )
            .logout(logout -> logout
                .logoutUrl("/logout")
                .logoutSuccessUrl("/login?logout=true")
                .invalidateHttpSession(true)
                .deleteCookies("JSESSIONID")
                .permitAll()
            );
        
        return http.build();
    }
    
    @Bean
    public AuthenticationSuccessHandler customAuthenticationSuccessHandler() {
        return (request, response, authentication) -> {
            // Get selected role from session
            String selectedRole = (String) ((HttpServletRequest) request).getSession().getAttribute("selectedRole");
            
            // Get actual user role
            String actualRole = authentication.getAuthorities().stream()
                    .map(a -> a.getAuthority().replace("ROLE_", ""))
                    .findFirst()
                    .orElse("");
            
            // Validate role if one was selected
            if (selectedRole != null && !selectedRole.isEmpty() && !selectedRole.equals(actualRole)) {
                // Role mismatch - logout and redirect to login with error
                ((HttpServletRequest) request).getSession().invalidate();
                response.sendRedirect("/login?error=true");
                return;
            }
            
            // Clear selected role from session
            ((HttpServletRequest) request).getSession().removeAttribute("selectedRole");
            
            String redirectUrl = "/dashboard";
            
            // Redirect dựa trên vai trò
            if (authentication.getAuthorities().stream()
                    .anyMatch(a -> a.getAuthority().equals("ROLE_BAN_QUAN_TRI"))) {
                redirectUrl = "/admin/dashboard";
            } else if (authentication.getAuthorities().stream()
                    .anyMatch(a -> a.getAuthority().equals("ROLE_KE_TOAN"))) {
                redirectUrl = "/ke-toan/dashboard";
            } else if (authentication.getAuthorities().stream()
                    .anyMatch(a -> a.getAuthority().equals("ROLE_NGUOI_DUNG_THUONG"))) {
                redirectUrl = "/cu-dan/dashboard";
            } else if (authentication.getAuthorities().stream()
                    .anyMatch(a -> a.getAuthority().equals("ROLE_CO_QUAN_CHUC_NANG"))) {
                redirectUrl = "/co-quan/dashboard";
            }
            
            response.sendRedirect(redirectUrl);
        };
    }
}


