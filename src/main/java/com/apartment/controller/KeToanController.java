package com.apartment.controller;

import com.apartment.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import com.apartment.entity.HoaDon;
import com.apartment.entity.ChiSoDienNuoc;
import java.math.BigDecimal;
import java.time.LocalDate;

@Controller
@RequestMapping("/ke-toan")
@PreAuthorize("hasAnyRole('BAN_QUAN_TRI', 'KE_TOAN')")
public class KeToanController {
    
    @Autowired
    private HoaDonService hoaDonService;
    
    @Autowired
    private HoGiaDinhService hoGiaDinhService;
    
    @Autowired
    private ChiSoDienNuocService chiSoService;
    
    @GetMapping("/dashboard")
    public String dashboard(Model model, Authentication authentication) {
        // Thống kê tài chính
        model.addAttribute("tongThuNhap", hoaDonService.sumPaidAmount());
        model.addAttribute("congNoConLai", hoaDonService.sumUnpaidAmount());
        model.addAttribute("tongHoGiaDinh", hoGiaDinhService.countActiveHo());
        
        model.addAttribute("username", authentication.getName());
        model.addAttribute("role", "Kế toán");
        return "ke-toan/dashboard";
    }
    
    @GetMapping("/chi-so")
    public String chiSoPage(@RequestParam(required = false) String maHo,
                            @RequestParam(required = false) String ky,
                            Model model) {
        model.addAttribute("hoList", hoGiaDinhService.findAll());
        model.addAttribute("maHo", maHo);
        model.addAttribute("ky", ky);
        model.addAttribute("records", chiSoService.search(maHo, ky));
        return "ke-toan/chi-so/list";
    }
    
    @PostMapping("/chi-so/save")
    public String saveChiSo(@RequestParam String maHo,
                            @RequestParam String ky,
                            @RequestParam Integer dienCu,
                            @RequestParam Integer dienMoi,
                            @RequestParam Integer nuocCu,
                            @RequestParam Integer nuocMoi,
                            @RequestParam(required = false, defaultValue = "0") java.math.BigDecimal tienDichVu,
                            org.springframework.web.servlet.mvc.support.RedirectAttributes ra) {
        try {
            com.apartment.entity.ChiSoDienNuoc cs = new com.apartment.entity.ChiSoDienNuoc();
            cs.setMaHo(maHo);
            cs.setKyThanhToan(ky);
            cs.setDienCu(dienCu);
            cs.setDienMoi(dienMoi);
            cs.setNuocCu(nuocCu);
            cs.setNuocMoi(nuocMoi);
            cs.setTienDichVu(tienDichVu);
            chiSoService.save(cs);
            ra.addFlashAttribute("success", "Lưu chỉ số thành công!");
        } catch (Exception e) {
            ra.addFlashAttribute("error", "Có lỗi khi lưu: " + e.getMessage());
        }
        return "redirect:/ke-toan/chi-so?maHo=" + maHo + "&ky=" + ky;
    }
    
    @GetMapping("/hoa-don")
    public String hoaDonList(@RequestParam(required = false) String trangThai, Model model) {
        model.addAttribute("trangThai", trangThai);
        if (trangThai != null && !trangThai.isEmpty()) {
            model.addAttribute("hoaDonList", hoaDonService.findByTrangThai(trangThai));
        } else {
            model.addAttribute("hoaDonList", hoaDonService.findAll());
        }
        return "ke-toan/hoa-don/list";
    }
    
    @GetMapping("/hoa-don/create")
    public String createForm(Model model) {
        model.addAttribute("hoaDon", new HoaDon());
        model.addAttribute("hoGiaDinhList", hoGiaDinhService.findAll());
        model.addAttribute("isEdit", false);
        return "ke-toan/hoa-don/form";
    }
    
    @GetMapping("/hoa-don/edit/{id}")
    public String editForm(@PathVariable Integer id, Model model) {
        HoaDon hoaDon = hoaDonService.findById(id)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy hóa đơn"));
        model.addAttribute("hoaDon", hoaDon);
        model.addAttribute("hoGiaDinhList", hoGiaDinhService.findAll());
        model.addAttribute("isEdit", true);
        return "ke-toan/hoa-don/form";
    }
    
    @PostMapping("/hoa-don/save")
    public String save(@ModelAttribute HoaDon hoaDon, RedirectAttributes ra) {
        try {
            hoaDonService.save(hoaDon);
            ra.addFlashAttribute("success", "Lưu hóa đơn thành công!");
        } catch (Exception e) {
            ra.addFlashAttribute("error", "Có lỗi: " + e.getMessage());
        }
        return "redirect:/ke-toan/hoa-don";
    }
    
    @PostMapping("/hoa-don/thanh-toan/{id}")
    public String thanhToanHoaDon(@PathVariable Integer id,
                                  @RequestParam String phuongThuc,
                                  RedirectAttributes ra) {
        try {
            HoaDon hd = hoaDonService.findById(id).orElseThrow();
            hd.setTrangThai("da_thanh_toan");
            hd.setPhuongThucThanhToan(phuongThuc);
            hd.setNgayThanhToan(java.time.LocalDateTime.now());
            hoaDonService.save(hd);
            ra.addFlashAttribute("success", "Thanh toán thành công!");
        } catch (Exception e) {
            ra.addFlashAttribute("error", "Có lỗi: " + e.getMessage());
        }
        return "redirect:/ke-toan/hoa-don";
    }
}

