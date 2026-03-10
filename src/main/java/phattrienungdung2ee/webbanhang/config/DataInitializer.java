package phattrienungdung2ee.webbanhang.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;
import phattrienungdung2ee.webbanhang.model.Category;
import phattrienungdung2ee.webbanhang.model.Role;
import phattrienungdung2ee.webbanhang.model.Account;
import phattrienungdung2ee.webbanhang.repository.CategoryRepository;
import phattrienungdung2ee.webbanhang.repository.RoleRepository;
import phattrienungdung2ee.webbanhang.repository.AccountRepository;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.HashSet;
import java.util.Set;

@Component
public class DataInitializer implements ApplicationRunner {

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

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

        // Khởi tạo Role
        if (roleRepository.count() == 0) {
            Role roleAdmin = new Role();
            roleAdmin.setName("ROLE_ADMIN");
            roleRepository.save(roleAdmin);

            Role roleUser = new Role();
            roleUser.setName("ROLE_USER");
            roleRepository.save(roleUser);

            System.out.println("✓ Dữ liệu role đã được thêm vào database!");
        }

        // Khởi tạo Account
        if (accountRepository.count() == 0) {
            Role adminRole = roleRepository.findByName("ROLE_ADMIN");
            Role userRole = roleRepository.findByName("ROLE_USER");

            Account admin = new Account();
            admin.setLogin_name("admin");
            admin.setPassword(passwordEncoder.encode("admin123"));
            Set<Role> adminRoles = new HashSet<>();
            adminRoles.add(adminRole);
            admin.setRoles(adminRoles);
            accountRepository.save(admin);

            Account user1 = new Account();
            user1.setLogin_name("user1");
            user1.setPassword(passwordEncoder.encode("123456"));
            Set<Role> userRoles = new HashSet<>();
            userRoles.add(userRole);
            user1.setRoles(userRoles);
            accountRepository.save(user1);

            System.out.println("✓ Dữ liệu account đã được thêm vào database!");
        }
    }
}
