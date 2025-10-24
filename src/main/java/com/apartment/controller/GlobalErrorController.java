package com.apartment.controller;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class GlobalErrorController implements ErrorController {
    
    private static final Logger logger = LoggerFactory.getLogger(GlobalErrorController.class);
    
    @RequestMapping("/error")
    public String handleError(HttpServletRequest request, Model model) {
        Object status = request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);
        Exception exception = (Exception) request.getAttribute(RequestDispatcher.ERROR_EXCEPTION);
        
        if (exception != null) {
            logger.error("Error occurred: ", exception);
        }
        
        if (status != null) {
            int statusCode = Integer.parseInt(status.toString());
            logger.error("Error status code: {}", statusCode);
            
            model.addAttribute("statusCode", statusCode);
            model.addAttribute("errorMessage", getErrorMessage(statusCode));
            
            // Redirect back to a safe page
            if (statusCode == 404) {
                return "redirect:/admin";
            } else if (statusCode == 403) {
                return "redirect:/login";
            }
        }
        
        // For 500 and other errors, redirect back to admin dashboard
        return "redirect:/admin";
    }
    
    private String getErrorMessage(int statusCode) {
        switch (statusCode) {
            case 404:
                return "Không tìm thấy trang";
            case 403:
                return "Không có quyền truy cập";
            case 500:
                return "Lỗi hệ thống";
            default:
                return "Đã xảy ra lỗi";
        }
    }
}

