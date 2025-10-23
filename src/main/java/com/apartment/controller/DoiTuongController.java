package com.apartment.controller;

import com.apartment.entity.DoiTuong;
import com.apartment.service.DoiTuongService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import jakarta.validation.Valid;

@Controller
@RequestMapping("/admin/doi-tuong")
@PreAuthorize("hasRole('BAN_QUAN_TRI')")
@RequiredArgsConstructor
public class DoiTuongController {
    
    private final DoiTuongService doiTuongService;
    
    @GetMapping
    public String list(@RequestParam(required = false) String search, Model model) {
        if (search != null && !search.isEmpty()) {
            model.addAttribute("doiTuongList", doiTuongService.searchByName(search));
        } else {
            model.addAttribute("doiTuongList", doiTuongService.findAll());
        }
        model.addAttribute("search", search);
        return "admin/doi-tuong/list";
    }
    
    @GetMapping("/create")
    public String createForm(Model model) {
        model.addAttribute("doiTuong", new DoiTuong());
        return "admin/doi-tuong/form";
    }
    
    @GetMapping("/edit/{cccd}")
    public String editForm(@PathVariable String cccd, Model model) {
        DoiTuong doiTuong = doiTuongService.findByCccd(cccd)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy đối tượng"));
        model.addAttribute("doiTuong", doiTuong);
        model.addAttribute("isEdit", true);
        return "admin/doi-tuong/form";
    }
    
    @PostMapping("/save")
    public String save(@ModelAttribute DoiTuong doiTuong, RedirectAttributes redirectAttributes) {
        try {
            doiTuongService.save(doiTuong);
            redirectAttributes.addFlashAttribute("success", "Lưu thành công!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Có lỗi xảy ra: " + e.getMessage());
        }
        return "redirect:/admin/doi-tuong";
    }
    
    @GetMapping("/delete/{cccd}")
    public String delete(@PathVariable String cccd, RedirectAttributes redirectAttributes) {
        try {
            doiTuongService.delete(cccd);
            redirectAttributes.addFlashAttribute("success", "Xóa thành công!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Có lỗi xảy ra: " + e.getMessage());
        }
        return "redirect:/admin/doi-tuong";
    }
}


