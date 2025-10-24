package com.apartment.controller;

import com.apartment.entity.DoiTuong;
import com.apartment.entity.HoGiaDinh;
import com.apartment.entity.ThanhVienHo;
import com.apartment.repository.DoiTuongRepository;
import com.apartment.repository.ThanhVienHoRepository;
import com.apartment.service.HoGiaDinhService;
import com.apartment.service.DoiTuongService;
import com.apartment.service.TaiSanChungCuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDate;
import java.util.List;

@Controller
@RequestMapping("/admin/ho-gia-dinh")
@PreAuthorize("hasRole('BAN_QUAN_TRI')")
public class HoGiaDinhController {
    
    @Autowired
    private HoGiaDinhService hoGiaDinhService;
    
    @Autowired
    private DoiTuongService doiTuongService;
    
    @Autowired
    private TaiSanChungCuService taiSanChungCuService;
    
    @Autowired
    private DoiTuongRepository doiTuongRepository;
    
    @Autowired
    private ThanhVienHoRepository thanhVienHoRepository;
    
    @GetMapping
    public String list(@RequestParam(required = false) String search, Model model) {
        List<HoGiaDinh> hoGiaDinhList;
        if (search != null && !search.isEmpty()) {
            hoGiaDinhList = hoGiaDinhService.searchByName(search);
        } else {
            hoGiaDinhList = hoGiaDinhService.findAll();
        }
        model.addAttribute("hoGiaDinhList", hoGiaDinhList);
        model.addAttribute("search", search);
        
        // Load tất cả căn hộ để map với hộ gia đình
        model.addAttribute("allCanHo", taiSanChungCuService.findAll());
        
        return "admin/ho-gia-dinh/list";
    }
    
    @GetMapping("/create")
    public String createForm(Model model) {
        model.addAttribute("hoGiaDinh", new HoGiaDinh());
        model.addAttribute("isEdit", false);
        // Load danh sách căn hộ để chọn
        model.addAttribute("canHoList", taiSanChungCuService.findAllCanHo());
        // Khởi tạo empty list cho thành viên
        model.addAttribute("thanhVienList", java.util.Collections.emptyList());
        return "admin/ho-gia-dinh/form";
    }
    
    @GetMapping("/edit/{maHo}")
    public String editForm(@PathVariable String maHo, Model model) {
        HoGiaDinh hoGiaDinh = hoGiaDinhService.findByMaHo(maHo)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy hộ gia đình"));
        model.addAttribute("hoGiaDinh", hoGiaDinh);
        model.addAttribute("isEdit", true);
        // Load danh sách căn hộ để chọn
        model.addAttribute("canHoList", taiSanChungCuService.findAllCanHo());
        return "admin/ho-gia-dinh/form";
    }
    
    @GetMapping("/detail/{maHo}")
    public String detail(@PathVariable String maHo, Model model) {
        HoGiaDinh hoGiaDinh = hoGiaDinhService.findByMaHo(maHo)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy hộ gia đình"));
        model.addAttribute("hoGiaDinh", hoGiaDinh);
        
        // Load thông tin căn hộ
        if (hoGiaDinh.getMaCanHo() != null) {
            taiSanChungCuService.findById(hoGiaDinh.getMaCanHo())
                    .ifPresent(canHo -> model.addAttribute("canHo", canHo));
        }
        
        // Load danh sách thành viên
        List<ThanhVienHo> thanhVienList = thanhVienHoRepository.findActiveByMaHo(maHo);
        model.addAttribute("thanhVienList", thanhVienList);
        
        return "admin/ho-gia-dinh/detail";
    }
    
    // API endpoint để search cư dân
    @GetMapping("/api/search-cudan")
    @ResponseBody
    public ResponseEntity<List<DoiTuong>> searchCuDan(@RequestParam String keyword) {
        List<DoiTuong> cuDanList = doiTuongRepository.searchCuDanByKeyword(keyword);
        return ResponseEntity.ok(cuDanList);
    }
    
    // API endpoint để thêm thành viên
    @PostMapping("/api/add-member")
    @ResponseBody
    public ResponseEntity<String> addMember(
            @RequestParam String maHo,
            @RequestParam String cccd,
            @RequestParam String quanHe,
            @RequestParam(defaultValue = "false") boolean laChuHo) {
        try {
            ThanhVienHo thanhVien = new ThanhVienHo();
            thanhVien.setMaHo(maHo);
            thanhVien.setCccd(cccd);
            thanhVien.setQuanHeVoiChuHo(quanHe);
            thanhVien.setLaChuHo(laChuHo);
            thanhVien.setNgayBatDau(LocalDate.now());
            
            thanhVienHoRepository.save(thanhVien);
            return ResponseEntity.ok("Thêm thành viên thành công");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Lỗi: " + e.getMessage());
        }
    }
    
    // API endpoint để xóa thành viên
    @PostMapping("/api/remove-member")
    @ResponseBody
    public ResponseEntity<String> removeMember(@RequestParam String maHo, @RequestParam String cccd, @RequestParam String ngayBatDau) {
        try {
            ThanhVienHo.ThanhVienHoId id = new ThanhVienHo.ThanhVienHoId();
            id.setCccd(cccd);
            id.setNgayBatDau(LocalDate.parse(ngayBatDau));
            thanhVienHoRepository.deleteById(id);
            return ResponseEntity.ok("Xóa thành viên thành công");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Lỗi: " + e.getMessage());
        }
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


