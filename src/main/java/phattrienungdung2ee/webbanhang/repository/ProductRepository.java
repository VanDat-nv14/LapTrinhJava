package phattrienungdung2ee.webbanhang.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import phattrienungdung2ee.webbanhang.model.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product, Integer> {

    Page<Product> findByNameContainingIgnoreCase(String name, Pageable pageable);

    Page<Product> findByCategory_Id(Integer categoryId, Pageable pageable);

    Page<Product> findByNameContainingIgnoreCaseAndCategory_Id(String name, Integer categoryId, Pageable pageable);
}
