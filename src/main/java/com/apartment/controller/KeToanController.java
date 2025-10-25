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
    
    @GetMapping("/quan-ly-hoa-don")
    public String quanLyHoaDon(@RequestParam(required = false) String search,
                               Model model,
                               Authentication authentication) {
        try {
            System.out.println("=== Truy cập /ke-toan/quan-ly-hoa-don ===");
            
            // Lấy danh sách hóa đơn
            java.util.List<HoaDon> hoaDonList = hoaDonService.findAll();
            
            // Lọc theo từ khóa tìm kiếm nếu có
            if (search != null && !search.trim().isEmpty()) {
                String searchLower = search.trim().toLowerCase();
                hoaDonList = hoaDonList.stream()
                    .filter(hd -> 
                        (hd.getMaHo() != null && hd.getMaHo().toLowerCase().contains(searchLower)) ||
                        (hd.getLoaiHoaDon() != null && hd.getLoaiHoaDon().toLowerCase().contains(searchLower)) ||
                        (hd.getMaHoaDon() != null && hd.getMaHoaDon().toString().contains(searchLower))
                    )
                    .collect(java.util.stream.Collectors.toList());
            }
            
            model.addAttribute("hoaDonList", hoaDonList);
            model.addAttribute("search", search);
            model.addAttribute("username", authentication.getName());
            
            return "ke-toan/quan-ly-hoa-don";
        } catch (Exception e) {
            System.err.println("Lỗi: " + e.getMessage());
            e.printStackTrace();
            model.addAttribute("hoaDonList", new java.util.ArrayList<>());
            model.addAttribute("error", "Lỗi khi tải danh sách hóa đơn");
            return "ke-toan/quan-ly-hoa-don";
        }
    }
    
    @GetMapping("/quan-ly-hoa-don/tao-moi")
    public String taoHoaDonMoi(Model model) {
        try {
            HoaDon hoaDon = new HoaDon();
            hoaDon.setTrangThai("chua_thanh_toan");
            model.addAttribute("hoaDon", hoaDon);
            model.addAttribute("hoGiaDinhList", hoGiaDinhService.findAll());
            model.addAttribute("isEdit", false);
            return "ke-toan/tao-hoa-don";
        } catch (Exception e) {
            System.err.println("Lỗi: " + e.getMessage());
            return "redirect:/ke-toan/quan-ly-hoa-don";
        }
    }
    
    @GetMapping("/quan-ly-hoa-don/sua/{id}")
    public String suaHoaDon(@PathVariable Integer id, Model model) {
        try {
            HoaDon hoaDon = hoaDonService.findById(id)
                    .orElseThrow(() -> new RuntimeException("Không tìm thấy hóa đơn"));
            model.addAttribute("hoaDon", hoaDon);
            model.addAttribute("hoGiaDinhList", hoGiaDinhService.findAll());
            model.addAttribute("isEdit", true);
            return "ke-toan/tao-hoa-don";
        } catch (Exception e) {
            System.err.println("Lỗi: " + e.getMessage());
            return "redirect:/ke-toan/quan-ly-hoa-don";
        }
    }
    
    @PostMapping("/quan-ly-hoa-don/luu")
    public String luuHoaDon(@RequestParam String maHo,
                           @RequestParam String loaiHoaDon,
                           @RequestParam BigDecimal soTien,
                           @RequestParam LocalDate hanThanhToan,
                           @RequestParam(required = false) String ghiChu,
                           @RequestParam(required = false) String moTa,
                           @RequestParam(required = false) String kyThanhToan,
                           @RequestParam(required = false) Integer dienCu,
                           @RequestParam(required = false) Integer dienMoi,
                           @RequestParam(required = false) Integer nuocCu,
                           @RequestParam(required = false) Integer nuocMoi,
                           RedirectAttributes ra) {
        try {
            System.out.println("=== DEBUG: Bắt đầu lưu hóa đơn ===");
            System.out.println("maHo: " + maHo);
            System.out.println("loaiHoaDon: " + loaiHoaDon);
            System.out.println("soTien: " + soTien);
            System.out.println("hanThanhToan: " + hanThanhToan);
            System.out.println("ghiChu: " + ghiChu);
            System.out.println("moTa: " + moTa);
            System.out.println("kyThanhToan: " + kyThanhToan);
            
            // Tạo hóa đơn
            HoaDon hoaDon = new HoaDon();
            hoaDon.setMaHo(maHo);
            hoaDon.setSoTien(soTien);
            hoaDon.setHanThanhToan(hanThanhToan);
            hoaDon.setTrangThai("chua_thanh_toan");
            
            // Xử lý ghi chú dựa trên loại hóa đơn
            String finalGhiChu = ghiChu;
            if ("dien_nuoc".equals(loaiHoaDon)) {
                // Điện nước được lưu như dịch vụ nhưng có ghi chú đặc biệt
                hoaDon.setLoaiHoaDon("dich_vu");
                finalGhiChu = "Hóa đơn điện nước - Kỳ: " + kyThanhToan;
                if (ghiChu != null && !ghiChu.trim().isEmpty()) {
                    finalGhiChu += " - " + ghiChu;
                }
            } else {
                // Các loại hóa đơn khác
                hoaDon.setLoaiHoaDon(loaiHoaDon);
                if (moTa != null && !moTa.trim().isEmpty()) {
                    finalGhiChu = moTa;
                    if (ghiChu != null && !ghiChu.trim().isEmpty()) {
                        finalGhiChu += " - " + ghiChu;
                    }
                }
            }
            hoaDon.setGhiChu(finalGhiChu);
            
            // Lưu hóa đơn
            System.out.println("=== DEBUG: Lưu hóa đơn ===");
            System.out.println("Hóa đơn: " + hoaDon.toString());
            hoaDonService.save(hoaDon);
            System.out.println("=== DEBUG: Đã lưu hóa đơn thành công ===");
            
            // Lưu chỉ số điện nước nếu là hóa đơn điện nước
            if ("dien_nuoc".equals(loaiHoaDon) && kyThanhToan != null && 
                dienCu != null && dienMoi != null && nuocCu != null && nuocMoi != null) {
                
                System.out.println("=== DEBUG: Lưu chỉ số điện nước ===");
                ChiSoDienNuoc chiSo = new ChiSoDienNuoc();
                chiSo.setMaHo(maHo);
                chiSo.setKyThanhToan(kyThanhToan);
                chiSo.setDienCu(dienCu);
                chiSo.setDienMoi(dienMoi);
                chiSo.setNuocCu(nuocCu);
                chiSo.setNuocMoi(nuocMoi);
                chiSo.setTienDichVu(soTien);
                
                System.out.println("Chỉ số: " + chiSo.toString());
                chiSoService.save(chiSo);
                System.out.println("=== DEBUG: Đã lưu chỉ số điện nước thành công ===");
            }
            
            String loaiHoaDonName = getLoaiHoaDonName(loaiHoaDon);
            ra.addFlashAttribute("success", "Tạo hóa đơn " + loaiHoaDonName + " thành công! Tổng tiền: " + 
                               soTien.toPlainString() + " VNĐ");
        } catch (Exception e) {
            System.err.println("=== DEBUG: Lỗi khi lưu hóa đơn ===");
            System.err.println("Lỗi: " + e.getMessage());
            e.printStackTrace();
            ra.addFlashAttribute("error", "Có lỗi khi tạo hóa đơn: " + e.getMessage());
        }
        return "redirect:/ke-toan/quan-ly-hoa-don";
    }
    
    private String getLoaiHoaDonName(String loaiHoaDon) {
        switch (loaiHoaDon) {
            case "dien_nuoc": return "điện nước";
            case "dich_vu": return "dịch vụ";
            case "sua_chua": return "sửa chữa";
            case "phat": return "phạt";
            case "khac": return "khác";
            default: return loaiHoaDon;
        }
    }
    
    @PostMapping("/quan-ly-hoa-don/xoa/{id}")
    public String xoaHoaDon(@PathVariable Integer id, RedirectAttributes ra) {
        try {
            hoaDonService.delete(id);
            ra.addFlashAttribute("success", "Xóa hóa đơn thành công!");
        } catch (Exception e) {
            ra.addFlashAttribute("error", "Có lỗi: " + e.getMessage());
        }
        return "redirect:/ke-toan/quan-ly-hoa-don";
    }
    
    @GetMapping("/quan-ly-hoa-don/tao-hoa-don")
    public String taoHoaDon(@RequestParam String loai, Model model, Authentication authentication) {
        try {
            model.addAttribute("hoGiaDinhList", hoGiaDinhService.findAll());
            model.addAttribute("username", authentication.getName());
            model.addAttribute("loaiHoaDon", loai);
            return "ke-toan/tao-hoa-don";
        } catch (Exception e) {
            System.err.println("Lỗi: " + e.getMessage());
            return "redirect:/ke-toan/quan-ly-hoa-don";
        }
    }
    
    
}

