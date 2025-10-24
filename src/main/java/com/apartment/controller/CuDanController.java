package com.apartment.controller;

import com.apartment.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/cu-dan")
@PreAuthorize("hasAnyRole('BAN_QUAN_TRI', 'NGUOI_DUNG_THUONG')")
public class CuDanController {
    
    @Autowired
    private ThongBaoService thongBaoService;
    
    @Autowired
    private BaoCaoSuCoService baoCaoSuCoService;
    
    @GetMapping("/dashboard")
    public String dashboard(Model model, Authentication authentication) {
        // Thông báo và sự cố
        model.addAttribute("thongBaoList", thongBaoService.findAllVisible());
        
        model.addAttribute("username", authentication.getName());
        model.addAttribute("role", "Cư dân");
        return "cu-dan/dashboard";
    }
}

