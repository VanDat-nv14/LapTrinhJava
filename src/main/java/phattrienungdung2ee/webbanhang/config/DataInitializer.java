package phattrienungdung2ee.webbanhang.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;
import phattrienungdung2ee.webbanhang.model.Category;
import phattrienungdung2ee.webbanhang.repository.CategoryRepository;

@Component
public class DataInitializer implements ApplicationRunner {

    @Autowired
    private CategoryRepository categoryRepository;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        // Chỉ thêm dữ liệu nếu database trống
        if (categoryRepository.count() == 0) {
            Category cat1 = new Category();
            cat1.setName("Điện thoại");
            categoryRepository.save(cat1);

            Category cat2 = new Category();
            cat2.setName("Laptop");
            categoryRepository.save(cat2);

            Category cat3 = new Category();
            cat3.setName("Máy tính bảng");
            categoryRepository.save(cat3);

            Category cat4 = new Category();
            cat4.setName("Phụ kiện");
            categoryRepository.save(cat4);

            Category cat5 = new Category();
            cat5.setName("Đồng hồ thông minh");
            categoryRepository.save(cat5);

            System.out.println("✓ Dữ liệu category đã được thêm vào database!");
        }
    }
}
