package com.apartment.controller;

import com.apartment.entity.HoaDon;
import com.apartment.service.HoaDonService;
import com.apartment.service.HoGiaDinhService;
import com.apartment.service.DichVuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDateTime;

@Controller
@RequestMapping("/admin/hoa-don")
@PreAuthorize("hasAnyRole('BAN_QUAN_TRI', 'KE_TOAN')")
public class HoaDonController {
    
    @Autowired
    private HoaDonService hoaDonService;
    
    @Autowired
    private HoGiaDinhService hoGiaDinhService;
    
    @Autowired
    private DichVuService dichVuService;
    
    @GetMapping
    public String list(@RequestParam(required = false) String trangThai, Model model) {
        if (trangThai != null && !trangThai.isEmpty()) {
            model.addAttribute("hoaDonList", hoaDonService.findByTrangThai(trangThai));
        } else {
            model.addAttribute("hoaDonList", hoaDonService.findAll());
        }
        model.addAttribute("trangThai", trangThai);
        model.addAttribute("tongDaThu", hoaDonService.sumPaidAmount());
        model.addAttribute("tongChuaThu", hoaDonService.sumUnpaidAmount());
        return "admin/hoa-don/list";
    }
    
    @GetMapping("/create")
    public String createForm(Model model) {
        model.addAttribute("hoaDon", new HoaDon());
        model.addAttribute("hoGiaDinhList", hoGiaDinhService.findAll());
        model.addAttribute("dichVuList", dichVuService.findAllActive());
        model.addAttribute("isEdit", false);
        return "admin/hoa-don/form";
    }
    
    @GetMapping("/edit/{id}")
    public String editForm(@PathVariable Integer id, Model model) {
        HoaDon hoaDon = hoaDonService.findById(id)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy hóa đơn"));
        model.addAttribute("hoaDon", hoaDon);
        model.addAttribute("hoGiaDinhList", hoGiaDinhService.findAll());
        model.addAttribute("dichVuList", dichVuService.findAllActive());
        model.addAttribute("isEdit", true);
        return "admin/hoa-don/form";
    }
    
    @PostMapping("/save")
    public String save(@ModelAttribute HoaDon hoaDon, RedirectAttributes redirectAttributes) {
        try {
            hoaDonService.save(hoaDon);
            redirectAttributes.addFlashAttribute("success", "Lưu hóa đơn thành công!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Có lỗi xảy ra: " + e.getMessage());
        }
        return "redirect:/admin/hoa-don";
    }
    
    @PostMapping("/thanh-toan/{id}")
    public String thanhToan(@PathVariable Integer id, 
                           @RequestParam String phuongThucThanhToan,
                           RedirectAttributes redirectAttributes) {
        try {
            HoaDon hoaDon = hoaDonService.findById(id)
                    .orElseThrow(() -> new RuntimeException("Không tìm thấy hóa đơn"));
            
            hoaDon.setTrangThai("da_thanh_toan");
            hoaDon.setNgayThanhToan(LocalDateTime.now());
            hoaDon.setPhuongThucThanhToan(phuongThucThanhToan);
            
            hoaDonService.save(hoaDon);
            redirectAttributes.addFlashAttribute("success", "Thanh toán thành công!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Có lỗi xảy ra: " + e.getMessage());
        }
        return "redirect:/admin/hoa-don";
    }
}


