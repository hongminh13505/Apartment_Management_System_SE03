package com.apartment.controller;

import com.apartment.entity.DoiTuong;
import com.apartment.repository.DoiTuongRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class AuthController {
    
    @Autowired
    private DoiTuongRepository doiTuongRepository;
    
    @GetMapping("/login")
    public String login(@RequestParam(required = false) String error,
                       @RequestParam(required = false) String logout,
                       Model model) {
        if (error != null) {
            model.addAttribute("error", "CCCD hoặc mật khẩu không đúng hoặc vai trò không khớp!");
        }
        if (logout != null) {
            model.addAttribute("message", "Đã đăng xuất thành công!");
        }
        return "login";
    }
    
    @GetMapping("/")
    public String home() {
        return "redirect:/dashboard";
    }
    
    @GetMapping("/dashboard")
    public String dashboard() {
        return "redirect:/admin/dashboard";
    }
    
    @GetMapping("/forgot-password")
    public String forgotPassword() {
        return "forgot-password";
    }
    
    @PostMapping("/forgot-password")
    public String processForgotPassword(@RequestParam("cccd") String cccd,
                                       @RequestParam("email") String email,
                                       RedirectAttributes redirectAttributes) {
        try {
            DoiTuong doiTuong = doiTuongRepository.findById(cccd).orElse(null);
            
            if (doiTuong == null) {
                redirectAttributes.addFlashAttribute("error", "Không tìm thấy tài khoản với CCCD này!");
                return "redirect:/forgot-password";
            }
            
            if (!email.equals(doiTuong.getEmail())) {
                redirectAttributes.addFlashAttribute("error", "Email không khớp với tài khoản!");
                return "redirect:/forgot-password";
            }
            
            // TODO: Send password reset email
            // For now, just redirect with success message
            redirectAttributes.addFlashAttribute("message", 
                "Đã gửi email hướng dẫn đặt lại mật khẩu! Vui lòng liên hệ quản trị viên.");
            return "redirect:/login";
            
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Có lỗi xảy ra: " + e.getMessage());
            return "redirect:/forgot-password";
        }
    }
}


