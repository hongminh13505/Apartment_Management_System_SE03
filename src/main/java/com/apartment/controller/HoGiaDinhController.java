package com.apartment.controller;

import com.apartment.entity.HoGiaDinh;
import com.apartment.service.HoGiaDinhService;
import com.apartment.service.DoiTuongService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/admin/ho-gia-dinh")
@PreAuthorize("hasRole('BAN_QUAN_TRI')")
public class HoGiaDinhController {
    
    @Autowired
    private HoGiaDinhService hoGiaDinhService;
    
    @Autowired
    private DoiTuongService doiTuongService;
    
    @GetMapping
    public String list(@RequestParam(required = false) String search, Model model) {
        if (search != null && !search.isEmpty()) {
            model.addAttribute("hoGiaDinhList", hoGiaDinhService.searchByName(search));
        } else {
            model.addAttribute("hoGiaDinhList", hoGiaDinhService.findAll());
        }
        model.addAttribute("search", search);
        return "admin/ho-gia-dinh/list";
    }
    
    @GetMapping("/create")
    public String createForm(Model model) {
        model.addAttribute("hoGiaDinh", new HoGiaDinh());
        model.addAttribute("isEdit", false);
        return "admin/ho-gia-dinh/form";
    }
    
    @GetMapping("/edit/{maHo}")
    public String editForm(@PathVariable String maHo, Model model) {
        HoGiaDinh hoGiaDinh = hoGiaDinhService.findByMaHo(maHo)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy hộ gia đình"));
        model.addAttribute("hoGiaDinh", hoGiaDinh);
        model.addAttribute("isEdit", true);
        return "admin/ho-gia-dinh/form";
    }
    
    @PostMapping("/save")
    public String save(@ModelAttribute HoGiaDinh hoGiaDinh, RedirectAttributes redirectAttributes) {
        try {
            hoGiaDinhService.save(hoGiaDinh);
            redirectAttributes.addFlashAttribute("success", "Lưu thành công!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Có lỗi xảy ra: " + e.getMessage());
        }
        return "redirect:/admin/ho-gia-dinh";
    }
    
    @GetMapping("/delete/{maHo}")
    public String delete(@PathVariable String maHo, RedirectAttributes redirectAttributes) {
        try {
            hoGiaDinhService.delete(maHo);
            redirectAttributes.addFlashAttribute("success", "Xóa thành công!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Có lỗi xảy ra: " + e.getMessage());
        }
        return "redirect:/admin/ho-gia-dinh";
    }
}


