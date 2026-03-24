package phattrienungdung2ee.webbanhang.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.util.UriComponentsBuilder;
import phattrienungdung2ee.webbanhang.model.Product;
import phattrienungdung2ee.webbanhang.service.CartSessionService;
import phattrienungdung2ee.webbanhang.service.ProductService;

import jakarta.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/cart")
public class CartController {

    @Autowired
    private CartSessionService cartSessionService;

    @Autowired
    private ProductService productService;

    @GetMapping
    public String viewCart(HttpSession session, Model model) {
        Map<Integer, Integer> lines = cartSessionService.getCart(session);
        List<CartLineView> items = new ArrayList<>();
        long grandTotal = 0;
        for (Map.Entry<Integer, Integer> e : lines.entrySet()) {
            Product p = productService.get(e.getKey());
            if (p == null) {
                continue;
            }
            int qty = e.getValue();
            long lineTotal = p.getPrice() * qty;
            grandTotal += lineTotal;
            items.add(new CartLineView(p.getId(), p.getName(), p.getPrice(), qty, lineTotal, p.getImage()));
        }
        model.addAttribute("cartItems", items);
        model.addAttribute("grandTotal", grandTotal);
        model.addAttribute("cartEmpty", items.isEmpty());
        return "cart/cart";
    }

    @PostMapping("/add")
    public String addToCart(HttpSession session,
                            @RequestParam int productId,
                            @RequestParam(defaultValue = "1") int quantity,
                            @RequestParam(required = false) String q,
                            @RequestParam(required = false) Integer categoryId,
                            @RequestParam(required = false, defaultValue = "priceAsc") String sort,
                            @RequestParam(required = false, defaultValue = "0") int page) {
        cartSessionService.addProduct(session, productId, quantity);
        UriComponentsBuilder b = UriComponentsBuilder.fromPath("/products");
        if (q != null && !q.isBlank()) {
            b.queryParam("q", q);
        }
        if (categoryId != null && categoryId > 0) {
            b.queryParam("categoryId", categoryId);
        }
        b.queryParam("sort", sort != null ? sort : "priceAsc");
        b.queryParam("page", page);
        return "redirect:" + b.encode().build().toUriString();
    }

    @PostMapping("/update")
    public String updateLine(HttpSession session,
                             @RequestParam int productId,
                             @RequestParam int quantity) {
        cartSessionService.updateQuantity(session, productId, quantity);
        return "redirect:/cart";
    }

    @PostMapping("/remove")
    public String removeLine(HttpSession session, @RequestParam int productId) {
        cartSessionService.removeLine(session, productId);
        return "redirect:/cart";
    }

    public record CartLineView(Integer productId, String name, Long unitPrice, int quantity, long lineTotal, String image) {
    }
}
