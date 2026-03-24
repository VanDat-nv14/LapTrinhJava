package phattrienungdung2ee.webbanhang.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import phattrienungdung2ee.webbanhang.model.ShopOrder;

@Repository
public interface ShopOrderRepository extends JpaRepository<ShopOrder, Integer> {
}
