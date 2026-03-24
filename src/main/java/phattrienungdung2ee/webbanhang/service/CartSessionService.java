package phattrienungdung2ee.webbanhang.service;

import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Service;

import java.util.LinkedHashMap;
import java.util.Map;

@Service
public class CartSessionService {

    public static final String SESSION_CART = "CART";

    @SuppressWarnings("unchecked")
    public Map<Integer, Integer> getCart(HttpSession session) {
        Object attr = session.getAttribute(SESSION_CART);
        if (attr instanceof Map<?, ?> map) {
            Map<Integer, Integer> typed = new LinkedHashMap<>();
            for (Map.Entry<?, ?> e : map.entrySet()) {
                if (e.getKey() instanceof Number && e.getValue() instanceof Number) {
                    typed.put(((Number) e.getKey()).intValue(), ((Number) e.getValue()).intValue());
                }
            }
            session.setAttribute(SESSION_CART, typed);
            return typed;
        }
        LinkedHashMap<Integer, Integer> empty = new LinkedHashMap<>();
        session.setAttribute(SESSION_CART, empty);
        return empty;
    }

    public void addProduct(HttpSession session, int productId, int quantity) {
        int q = Math.max(1, quantity);
        Map<Integer, Integer> cart = getCart(session);
        cart.merge(productId, q, Integer::sum);
    }

    public void updateQuantity(HttpSession session, int productId, int quantity) {
        Map<Integer, Integer> cart = getCart(session);
        if (quantity <= 0) {
            cart.remove(productId);
        } else {
            cart.put(productId, quantity);
        }
    }

    public void removeLine(HttpSession session, int productId) {
        getCart(session).remove(productId);
    }

    public void clear(HttpSession session) {
        session.removeAttribute(SESSION_CART);
    }
}
