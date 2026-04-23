package com.mimce.workshop.controller;

import com.mimce.workshop.service.UserRegistrationService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class RegisterController {

    private final UserRegistrationService userRegistrationService;

    public RegisterController(UserRegistrationService userRegistrationService) {
        this.userRegistrationService = userRegistrationService;
    }

    @GetMapping("/register")
    public String registerPage() {
        return "register";
    }

    @PostMapping("/register")
    public String register(
        @RequestParam String username,
        @RequestParam String password,
        RedirectAttributes redirectAttributes
    ) {
        try {
            userRegistrationService.register(username, password);
            redirectAttributes.addFlashAttribute("successMessage", "Hesap olusturuldu. Simdi giris yapabilirsiniz.");
            return "redirect:/login";
        } catch (IllegalStateException ex) {
            redirectAttributes.addFlashAttribute("errorMessage", "Bu kullanici adi zaten kullaniliyor.");
            return "redirect:/register";
        } catch (IllegalArgumentException ex) {
            redirectAttributes.addFlashAttribute("errorMessage", "Kullanici adi ve sifre zorunludur.");
            return "redirect:/register";
        }
    }
}
