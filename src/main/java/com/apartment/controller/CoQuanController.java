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
@RequestMapping("/co-quan")
@PreAuthorize("hasAnyRole('BAN_QUAN_TRI', 'CO_QUAN_CHUC_NANG')")
public class CoQuanController {
    
    @Autowired
    private DoiTuongService doiTuongService;
    
    @Autowired
    private HoGiaDinhService hoGiaDinhService;
    
    @Autowired
    private BaoCaoSuCoService baoCaoSuCoService;
    
    @GetMapping("/dashboard")
    public String dashboard(Model model, Authentication authentication) {
        // Thống kê tổng quan cho cơ quan chức năng
        model.addAttribute("tongCuDan", doiTuongService.countCuDan());
        model.addAttribute("tongHoGiaDinh", hoGiaDinhService.countActiveHo());
        model.addAttribute("baoCaoMoi", baoCaoSuCoService.countNewReports());
        
        // Báo cáo sự cố
        model.addAttribute("baoCaoSuCoList", baoCaoSuCoService.findPendingReports());
        
        model.addAttribute("username", authentication.getName());
        model.addAttribute("role", "Cơ quan chức năng");
        return "co-quan/dashboard";
    }
}

