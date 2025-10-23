package com.apartment.controller;

import com.apartment.entity.BaoCaoSuCo;
import com.apartment.service.BaoCaoSuCoService;
import com.apartment.service.DoiTuongService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDateTime;

@Controller
@RequestMapping("/admin/bao-cao-su-co")
@PreAuthorize("hasAnyRole('BAN_QUAN_TRI', 'CO_QUAN_CHUC_NANG')")
@RequiredArgsConstructor
public class BaoCaoSuCoController {
    
    private final BaoCaoSuCoService baoCaoSuCoService;
    private final DoiTuongService doiTuongService;
    
    @GetMapping
    public String list(@RequestParam(required = false) String trangThai, Model model) {
        if (trangThai != null && !trangThai.isEmpty()) {
            model.addAttribute("baoCaoList", baoCaoSuCoService.findByTrangThai(trangThai));
        } else {
            model.addAttribute("baoCaoList", baoCaoSuCoService.findAll());
        }
        model.addAttribute("trangThai", trangThai);
        return "admin/bao-cao-su-co/list";
    }
    
    @GetMapping("/create")
    public String createForm(Model model, Authentication authentication) {
        BaoCaoSuCo baoCaoSuCo = new BaoCaoSuCo();
        baoCaoSuCo.setCccdNguoiNhap(authentication.getName());
        model.addAttribute("baoCaoSuCo", baoCaoSuCo);
        model.addAttribute("cuDanList", doiTuongService.findAllActiveCuDan());
        return "admin/bao-cao-su-co/form";
    }
    
    @GetMapping("/edit/{id}")
    public String editForm(@PathVariable Integer id, Model model) {
        BaoCaoSuCo baoCaoSuCo = baoCaoSuCoService.findById(id)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy báo cáo"));
        model.addAttribute("baoCaoSuCo", baoCaoSuCo);
        model.addAttribute("cuDanList", doiTuongService.findAllActiveCuDan());
        model.addAttribute("isEdit", true);
        return "admin/bao-cao-su-co/form";
    }
    
    @PostMapping("/save")
    public String save(@ModelAttribute BaoCaoSuCo baoCaoSuCo, RedirectAttributes redirectAttributes) {
        try {
            baoCaoSuCoService.save(baoCaoSuCo);
            redirectAttributes.addFlashAttribute("success", "Lưu báo cáo thành công!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Có lỗi xảy ra: " + e.getMessage());
        }
        return "redirect:/admin/bao-cao-su-co";
    }
    
    @PostMapping("/xu-ly/{id}")
    public String xuLy(@PathVariable Integer id, 
                      @RequestParam String ketQuaXuLy,
                      Authentication authentication,
                      RedirectAttributes redirectAttributes) {
        try {
            BaoCaoSuCo baoCao = baoCaoSuCoService.findById(id)
                    .orElseThrow(() -> new RuntimeException("Không tìm thấy báo cáo"));
            
            baoCao.setCccdNguoiXuLy(authentication.getName());
            baoCao.setTrangThai("da_hoan_thanh");
            baoCao.setNgayHoanThanh(LocalDateTime.now());
            baoCao.setKetQuaXuLy(ketQuaXuLy);
            
            baoCaoSuCoService.save(baoCao);
            redirectAttributes.addFlashAttribute("success", "Xử lý báo cáo thành công!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Có lỗi xảy ra: " + e.getMessage());
        }
        return "redirect:/admin/bao-cao-su-co";
    }
}


