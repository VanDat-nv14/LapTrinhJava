package phattrienungdung2ee.webbanhang.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import phattrienungdung2ee.webbanhang.model.Account;
import phattrienungdung2ee.webbanhang.model.ShopOrder;
import phattrienungdung2ee.webbanhang.service.AccountService;
import phattrienungdung2ee.webbanhang.service.CartSessionService;
import phattrienungdung2ee.webbanhang.service.OrderService;

import jakarta.servlet.http.HttpSession;
import java.util.Map;

@Controller
public class OrderController {

    @Autowired
    private OrderService orderService;

    @Autowired
    private AccountService accountService;

    @Autowired
    private CartSessionService cartSessionService;

    @PostMapping("/checkout")
    public String checkout(HttpSession session,
                           Authentication authentication,
                           RedirectAttributes redirectAttributes) {
        Account account = accountService.findByLoginName(authentication.getName());
        if (account == null) {
            return "redirect:/login";
        }
        Map<Integer, Integer> lines = cartSessionService.getCart(session);
        try {
            ShopOrder saved = orderService.placeOrder(account, lines);
            cartSessionService.clear(session);
            redirectAttributes.addFlashAttribute("orderId", saved.getId());
            redirectAttributes.addFlashAttribute("orderTotal", saved.getTotalAmount());
        } catch (IllegalArgumentException e) {
            redirectAttributes.addFlashAttribute("checkoutError", e.getMessage());
            return "redirect:/cart";
        }
        return "redirect:/order/success";
    }

    @GetMapping("/order/success")
    public String orderSuccess(@ModelAttribute("orderId") Integer orderId) {
        if (orderId == null) {
            return "redirect:/products";
        }
        return "order/success";
    }
}
