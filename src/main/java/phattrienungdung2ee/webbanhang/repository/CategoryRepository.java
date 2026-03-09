package phattrienungdung2ee.webbanhang.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import phattrienungdung2ee.webbanhang.model.Category;

public interface CategoryRepository extends JpaRepository<Category, Integer> {
}
