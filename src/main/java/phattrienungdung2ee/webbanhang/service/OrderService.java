package phattrienungdung2ee.webbanhang.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import phattrienungdung2ee.webbanhang.model.Account;
import phattrienungdung2ee.webbanhang.model.OrderDetail;
import phattrienungdung2ee.webbanhang.model.Product;
import phattrienungdung2ee.webbanhang.model.ShopOrder;
import phattrienungdung2ee.webbanhang.repository.ProductRepository;
import phattrienungdung2ee.webbanhang.repository.ShopOrderRepository;

import java.time.LocalDateTime;
import java.util.Map;

@Service
public class OrderService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ShopOrderRepository shopOrderRepository;

    @Transactional
    public ShopOrder placeOrder(Account account, Map<Integer, Integer> cartLines) {
        if (cartLines == null || cartLines.isEmpty()) {
            throw new IllegalArgumentException("Giỏ hàng trống");
        }
        ShopOrder order = new ShopOrder();
        order.setCreatedAt(LocalDateTime.now());
        order.setAccount(account);
        long total = 0;
        for (Map.Entry<Integer, Integer> entry : cartLines.entrySet()) {
            int productId = entry.getKey();
            int qty = entry.getValue();
            if (qty <= 0) {
                continue;
            }
            Product product = productRepository.findById(productId)
                    .orElseThrow(() -> new IllegalArgumentException("Sản phẩm không tồn tại: " + productId));
            OrderDetail detail = new OrderDetail();
            detail.setOrder(order);
            detail.setProduct(product);
            detail.setQuantity(qty);
            detail.setUnitPrice(product.getPrice());
            order.getDetails().add(detail);
            total += (long) product.getPrice() * qty;
        }
        if (order.getDetails().isEmpty()) {
            throw new IllegalArgumentException("Không có dòng hợp lệ trong giỏ hàng");
        }
        order.setTotalAmount(total);
        return shopOrderRepository.save(order);
    }
}
