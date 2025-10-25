package com.apartment.controller;

import com.apartment.service.*;
import com.apartment.entity.DoiTuong;
import com.apartment.entity.BaoCaoSuCo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/cu-dan")
@PreAuthorize("hasAnyRole('BAN_QUAN_TRI', 'NGUOI_DUNG_THUONG')")
public class CuDanController {
    
    @Autowired
    private ThongBaoService thongBaoService;
    
    @Autowired
    private BaoCaoSuCoService baoCaoSuCoService;
    
    @Autowired
    private DoiTuongService doiTuongService;
    
    @GetMapping("/dashboard")
    public String dashboard(Model model, Authentication authentication) {
        // Thông báo và sự cố
        model.addAttribute("thongBaoList", thongBaoService.findAllVisible());
        model.addAttribute("baoCaoSuCoList", baoCaoSuCoService.findAll());
        
        model.addAttribute("username", authentication.getName());
        model.addAttribute("role", "Cư dân");
        return "cu-dan/dashboard";
    }
    
    @GetMapping("/thong-tin-ca-nhan")
    public String thongTinCaNhan(Model model, Authentication authentication) {
        try {
            String cccd = authentication.getName();
            DoiTuong doiTuong = doiTuongService.findByCccd(cccd)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy thông tin cá nhân"));
            
            model.addAttribute("doiTuong", doiTuong);
            return "cu-dan/thong-tin-ca-nhan";
        } catch (Exception e) {
            model.addAttribute("error", "Có lỗi khi tải thông tin cá nhân: " + e.getMessage());
            return "cu-dan/thong-tin-ca-nhan";
        }
    }
    
    @GetMapping("/thong-tin-ca-nhan/edit")
    public String editThongTinCaNhan(Model model, Authentication authentication) {
        try {
            String cccd = authentication.getName();
            DoiTuong doiTuong = doiTuongService.findByCccd(cccd)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy thông tin cá nhân"));
            
            model.addAttribute("doiTuong", doiTuong);
            return "cu-dan/thong-tin-ca-nhan-edit";
        } catch (Exception e) {
            model.addAttribute("error", "Có lỗi khi tải thông tin cá nhân: " + e.getMessage());
            return "redirect:/cu-dan/thong-tin-ca-nhan";
        }
    }
    
    @PostMapping("/thong-tin-ca-nhan/save")
    public String saveThongTinCaNhan(@ModelAttribute DoiTuong doiTuong, 
                                     @RequestParam(required = false) String matKhau,
                                     RedirectAttributes redirectAttributes,
                                     Authentication authentication) {
        try {
            String cccd = authentication.getName();
            DoiTuong existingDoiTuong = doiTuongService.findByCccd(cccd)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy thông tin cá nhân"));
            
            // Cập nhật thông tin
            existingDoiTuong.setHoVaTen(doiTuong.getHoVaTen());
            existingDoiTuong.setNgaySinh(doiTuong.getNgaySinh());
            existingDoiTuong.setGioiTinh(doiTuong.getGioiTinh());
            existingDoiTuong.setSoDienThoai(doiTuong.getSoDienThoai());
            existingDoiTuong.setEmail(doiTuong.getEmail());
            existingDoiTuong.setQueQuan(doiTuong.getQueQuan());
            existingDoiTuong.setNgheNghiep(doiTuong.getNgheNghiep());
            
            // Chỉ cập nhật mật khẩu nếu có nhập
            if (matKhau != null && !matKhau.trim().isEmpty()) {
                existingDoiTuong.setMatKhau(matKhau);
            }
            
            doiTuongService.save(existingDoiTuong);
            redirectAttributes.addFlashAttribute("success", "Cập nhật thông tin thành công!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Có lỗi khi cập nhật thông tin: " + e.getMessage());
        }
        return "redirect:/cu-dan/thong-tin-ca-nhan";
    }
    
    @GetMapping("/thong-bao")
    public String thongBao(@org.springframework.web.bind.annotation.RequestParam(required = false) String search,
                           Model model,
                           Authentication authentication) {
        try {
            java.util.List<com.apartment.entity.ThongBao> thongBaoList = thongBaoService.findAll();
            
            // Tìm kiếm nếu có
            if (search != null && !search.trim().isEmpty()) {
                String searchLower = search.trim().toLowerCase();
                thongBaoList = thongBaoList.stream()
                    .filter(tb -> 
                        (tb.getTieuDe() != null && tb.getTieuDe().toLowerCase().contains(searchLower)) ||
                        (tb.getNoiDungThongBao() != null && tb.getNoiDungThongBao().toLowerCase().contains(searchLower))
                    )
                    .collect(java.util.stream.Collectors.toList());
            }
            
            model.addAttribute("thongBaoList", thongBaoList);
            model.addAttribute("search", search);
            model.addAttribute("username", authentication.getName());
            return "cu-dan/thong-bao";
        } catch (Exception e) {
            model.addAttribute("error", "Có lỗi khi tải danh sách thông báo");
            model.addAttribute("thongBaoList", new java.util.ArrayList<>());
            return "cu-dan/thong-bao";
        }
    }
    
    @GetMapping("/bao-cao-su-co")
    public String baoCaoSuCo(@org.springframework.web.bind.annotation.RequestParam(required = false) String search,
                             Model model,
                             Authentication authentication) {
        try {
            String cccd = authentication.getName();
            java.util.List<com.apartment.entity.BaoCaoSuCo> baoCaoList = baoCaoSuCoService.findByCccdNguoiBaoCao(cccd);
            
            // Tìm kiếm nếu có
            if (search != null && !search.trim().isEmpty()) {
                String searchLower = search.trim().toLowerCase();
                baoCaoList = baoCaoList.stream()
                    .filter(bc -> 
                        (bc.getTieuDe() != null && bc.getTieuDe().toLowerCase().contains(searchLower)) ||
                        (bc.getTrangThai() != null && bc.getTrangThai().toLowerCase().contains(searchLower))
                    )
                    .collect(java.util.stream.Collectors.toList());
            }
            
            model.addAttribute("baoCaoList", baoCaoList);
            model.addAttribute("search", search);
            model.addAttribute("username", authentication.getName());
            return "cu-dan/bao-cao-su-co";
        } catch (Exception e) {
            model.addAttribute("error", "Có lỗi khi tải danh sách báo cáo");
            model.addAttribute("baoCaoList", new java.util.ArrayList<>());
            return "cu-dan/bao-cao-su-co";
        }
    }
    
    @GetMapping("/bao-cao-su-co/tao-moi")
    public String taoBaoCaoSuCo(Model model, Authentication authentication) {
        try {
            BaoCaoSuCo baoCaoSuCo = new BaoCaoSuCo();
            model.addAttribute("baoCaoSuCo", baoCaoSuCo);
            model.addAttribute("username", authentication.getName());
            return "cu-dan/tao-bao-cao-su-co";
        } catch (Exception e) {
            model.addAttribute("error", "Có lỗi khi tải form tạo báo cáo");
            return "redirect:/cu-dan/bao-cao-su-co";
        }
    }
    
    @PostMapping("/bao-cao-su-co/luu")
    public String luuBaoCaoSuCo(@ModelAttribute BaoCaoSuCo baoCaoSuCo,
                                RedirectAttributes redirectAttributes,
                                Authentication authentication) {
        try {
            String cccd = authentication.getName();
            
            // Thiết lập thông tin người báo cáo
            baoCaoSuCo.setCccdNguoiBaoCao(cccd);
            baoCaoSuCo.setCccdNguoiNhap(cccd);
            baoCaoSuCo.setPhuongThucBaoCao("truc_tuyen");
            baoCaoSuCo.setTrangThai("moi_tiep_nhan");
            
            // Thiết lập ngày báo cáo
            baoCaoSuCo.setNgayBaoCao(java.time.LocalDateTime.now());
            
            baoCaoSuCoService.save(baoCaoSuCo);
            redirectAttributes.addFlashAttribute("success", "Tạo báo cáo sự cố thành công!");
            
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Có lỗi khi tạo báo cáo: " + e.getMessage());
        }
        
        return "redirect:/cu-dan/bao-cao-su-co";
    }
}

