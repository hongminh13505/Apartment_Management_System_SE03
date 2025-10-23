package com.apartment.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class AuthController {
    
    @GetMapping("/login")
    public String login(@RequestParam(required = false) String error,
                       @RequestParam(required = false) String logout,
                       Model model) {
        if (error != null) {
            model.addAttribute("error", "CCCD hoặc mật khẩu không đúng!");
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
}


