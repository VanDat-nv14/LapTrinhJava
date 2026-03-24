package phattrienungdung2ee.webbanhang.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import phattrienungdung2ee.webbanhang.dto.RegisterForm;
import phattrienungdung2ee.webbanhang.service.AccountService;

@Controller
public class RegisterController {

    @Autowired
    private AccountService accountService;

    @GetMapping("/register")
    public String showRegister(Model model) {
        model.addAttribute("registerForm", new RegisterForm());
        return "register";
    }

    @PostMapping("/register")
    public String register(@Valid @ModelAttribute("registerForm") RegisterForm form,
                           BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "register";
        }
        try {
            accountService.register(form.getLoginName().trim(), form.getPassword());
        } catch (IllegalArgumentException e) {
            bindingResult.reject("register.error", e.getMessage());
            return "register";
        }
        return "redirect:/login?registered";
    }
}
