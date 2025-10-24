package com.apartment.controller;

import com.apartment.entity.TaiSanChungCu;
import com.apartment.service.TaiSanChungCuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Controller
@RequestMapping("/admin/tai-san")
@PreAuthorize("hasRole('BAN_QUAN_TRI')")
public class TaiSanChungCuController {
    
    private static final Logger logger = LoggerFactory.getLogger(TaiSanChungCuController.class);
    
    @Autowired
    private TaiSanChungCuService taiSanService;
    
    @GetMapping
    public String list(@RequestParam(required = false) String loai, Model model) {
        if (loai != null && !loai.isEmpty()) {
            model.addAttribute("taiSanList", taiSanService.findByLoaiTaiSan(loai));
        } else {
            model.addAttribute("taiSanList", taiSanService.findAll());
        }
        model.addAttribute("loai", loai);
        return "admin/tai-san/list";
    }
    
    @GetMapping("/create")
    public String createForm(Model model) {
        model.addAttribute("taiSan", new TaiSanChungCu());
        model.addAttribute("isEdit", false);
        return "admin/tai-san/form";
    }
    
    @GetMapping("/edit/{maTaiSan}")
    public String editForm(@PathVariable Integer maTaiSan, Model model) {
        TaiSanChungCu taiSan = taiSanService.findById(maTaiSan)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy tài sản"));
        model.addAttribute("taiSan", taiSan);
        model.addAttribute("isEdit", true);
        return "admin/tai-san/form";
    }
    
    @PostMapping("/save")
    public String save(@ModelAttribute TaiSanChungCu taiSan, RedirectAttributes redirectAttributes) {
        try {
            logger.info("Saving tai san: {}", taiSan);
            
            // Đảm bảo maHo là null nếu rỗng
            if (taiSan.getMaHo() != null && taiSan.getMaHo().trim().isEmpty()) {
                taiSan.setMaHo(null);
            }
            
            // Đảm bảo trangThai có giá trị mặc định
            if (taiSan.getTrangThai() == null || taiSan.getTrangThai().trim().isEmpty()) {
                taiSan.setTrangThai("hoat_dong");
            }
            
            TaiSanChungCu saved = taiSanService.save(taiSan);
            logger.info("Tai san saved successfully with ID: {}", saved.getMaTaiSan());
            
            redirectAttributes.addFlashAttribute("success", 
                taiSan.getMaTaiSan() == null ? "Thêm tài sản mới thành công!" : "Cập nhật tài sản thành công!");
        } catch (Exception e) {
            logger.error("Error saving tai san", e);
            redirectAttributes.addFlashAttribute("error", "Có lỗi xảy ra: " + e.getMessage());
        }
        return "redirect:/admin/tai-san";
    }
    
    @GetMapping("/delete/{maTaiSan}")
    public String delete(@PathVariable Integer maTaiSan, RedirectAttributes redirectAttributes) {
        try {
            taiSanService.delete(maTaiSan);
            redirectAttributes.addFlashAttribute("success", "Xóa thành công!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Có lỗi xảy ra: " + e.getMessage());
        }
        return "redirect:/admin/tai-san";
    }
}

