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
import org.springframework.dao.DataIntegrityViolationException;
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
    public String createForm(Model model, @RequestParam(required = false) String error) {
        model.addAttribute("hoGiaDinh", new HoGiaDinh());
        model.addAttribute("isEdit", false);
        // Load danh sách căn hộ để chọn
        model.addAttribute("canHoList", taiSanChungCuService.findAllCanHo());
        // Khởi tạo empty list cho thành viên
        model.addAttribute("thanhVienList", java.util.Collections.emptyList());
        if (error != null) {
            model.addAttribute("error", error);
        }
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
    public String save(@ModelAttribute HoGiaDinh hoGiaDinh, 
                      @RequestParam(required = false) String cccdChuHo,
                      @RequestParam(required = false) String isEdit,
                      RedirectAttributes redirectAttributes) {
        try {
            // Kiểm tra xem căn hộ đã được gán cho hộ gia đình khác chưa
            if (hoGiaDinh.getMaCanHo() != null) {
                HoGiaDinh existingHo = hoGiaDinhService.findByMaCanHo(hoGiaDinh.getMaCanHo()).orElse(null);
                
                // Kiểm tra đây có phải là hộ mới (maHo chưa tồn tại) hay edit
                boolean isNew = hoGiaDinhService.findByMaHo(hoGiaDinh.getMaHo()).isEmpty();
                
                // Nếu căn hộ đã được gán cho hộ khác
                if (existingHo != null && !existingHo.getMaHo().equals(hoGiaDinh.getMaHo())) {
                    String errorMsg = "⚠️ Căn hộ này đã được gán cho hộ gia đình " + existingHo.getMaHo() + " (Tên: " + existingHo.getTenHo() + ")! Vui lòng chọn căn hộ khác.";
                    redirectAttributes.addFlashAttribute("error", errorMsg);
                    System.out.println("=== DEBUG: " + errorMsg + " ===");
                    if (isNew) {
                        return "redirect:/admin/ho-gia-dinh";
                    } else {
                        return "redirect:/admin/ho-gia-dinh/edit/" + hoGiaDinh.getMaHo();
                    }
                }
            }
            
            // Lưu thông tin hộ gia đình
            hoGiaDinhService.save(hoGiaDinh);
            
            // Nếu có CCCD chủ hộ, tự động thêm vào bảng thành viên hộ
            if (cccdChuHo != null && !cccdChuHo.trim().isEmpty()) {
                // Kiểm tra xem chủ hộ đã tồn tại chưa
                List<ThanhVienHo> existingMembers = thanhVienHoRepository.findActiveByCccd(cccdChuHo);
                boolean alreadyExists = existingMembers.stream()
                    .anyMatch(m -> m.getMaHo().equals(hoGiaDinh.getMaHo()) && m.getLaChuHo());
                
                if (!alreadyExists) {
                    // Thêm chủ hộ vào bảng thành viên hộ
                    ThanhVienHo chuHo = new ThanhVienHo();
                    chuHo.setCccd(cccdChuHo);
                    chuHo.setMaHo(hoGiaDinh.getMaHo());
                    chuHo.setLaChuHo(true);
                    chuHo.setQuanHeVoiChuHo("Chủ hộ");
                    chuHo.setNgayBatDau(LocalDate.now());
                    
                    thanhVienHoRepository.save(chuHo);
                }
            }
            
            redirectAttributes.addFlashAttribute("success", "Lưu thành công!");
        } catch (org.springframework.dao.DataIntegrityViolationException e) {
            redirectAttributes.addFlashAttribute("error", 
                "Căn hộ này đã được gán cho hộ gia đình khác! Vui lòng chọn căn hộ khác.");
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


