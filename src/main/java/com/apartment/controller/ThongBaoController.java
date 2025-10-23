package com.apartment.controller;

import com.apartment.entity.ThongBao;
import com.apartment.service.ThongBaoService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/admin/thong-bao")
@PreAuthorize("hasRole('BAN_QUAN_TRI')")
@RequiredArgsConstructor
public class ThongBaoController {
    
    private final ThongBaoService thongBaoService;
    
    @GetMapping
    public String list(Model model) {
        model.addAttribute("thongBaoList", thongBaoService.findAll());
        return "admin/thong-bao/list";
    }
    
    @GetMapping("/create")
    public String createForm(Model model, Authentication authentication) {
        ThongBao thongBao = new ThongBao();
        thongBao.setCccdBanQuanTri(authentication.getName());
        model.addAttribute("thongBao", thongBao);
        return "admin/thong-bao/form";
    }
    
    @GetMapping("/edit/{id}")
    public String editForm(@PathVariable Integer id, Model model) {
        ThongBao thongBao = thongBaoService.findById(id)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy thông báo"));
        model.addAttribute("thongBao", thongBao);
        model.addAttribute("isEdit", true);
        return "admin/thong-bao/form";
    }
    
    @PostMapping("/save")
    public String save(@ModelAttribute ThongBao thongBao, RedirectAttributes redirectAttributes) {
        try {
            thongBaoService.save(thongBao);
            redirectAttributes.addFlashAttribute("success", "Lưu thông báo thành công!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Có lỗi xảy ra: " + e.getMessage());
        }
        return "redirect:/admin/thong-bao";
    }
    
    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Integer id, RedirectAttributes redirectAttributes) {
        try {
            thongBaoService.delete(id);
            redirectAttributes.addFlashAttribute("success", "Xóa thành công!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Có lỗi xảy ra: " + e.getMessage());
        }
        return "redirect:/admin/thong-bao";
    }
}


