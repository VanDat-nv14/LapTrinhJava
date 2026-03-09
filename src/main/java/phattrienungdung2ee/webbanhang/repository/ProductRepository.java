package phattrienungdung2ee.webbanhang.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import phattrienungdung2ee.webbanhang.model.Product;

public interface ProductRepository extends JpaRepository<Product, Integer> {
}
