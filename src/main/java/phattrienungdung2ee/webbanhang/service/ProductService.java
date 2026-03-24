package phattrienungdung2ee.webbanhang.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import phattrienungdung2ee.webbanhang.model.Product;
import phattrienungdung2ee.webbanhang.repository.ProductRepository;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.UUID;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Value("${app.upload-dir:upload-dir}")
    private String uploadDir;

    public static final int PAGE_SIZE = 5;

    public List<Product> getAll() {
        return productRepository.findAll();
    }

    public Page<Product> search(String q, Integer categoryId, String sort, int page) {
        Sort sortObj = "priceDesc".equalsIgnoreCase(sort != null ? sort : "")
                ? Sort.by("price").descending()
                : Sort.by("price").ascending();
        int pageIndex = Math.max(0, page);
        PageRequest pageable = PageRequest.of(pageIndex, PAGE_SIZE, sortObj);
        boolean hasQ = q != null && !q.trim().isEmpty();
        boolean hasCat = categoryId != null && categoryId > 0;
        if (hasQ && hasCat) {
            return productRepository.findByNameContainingIgnoreCaseAndCategory_Id(q.trim(), categoryId, pageable);
        }
        if (hasQ) {
            return productRepository.findByNameContainingIgnoreCase(q.trim(), pageable);
        }
        if (hasCat) {
            return productRepository.findByCategory_Id(categoryId, pageable);
        }
        return productRepository.findAll(pageable);
    }

    public Product get(int id) {
        return productRepository.findById(id).orElse(null);
    }

    public void add(Product newProduct) {
        productRepository.save(newProduct);
    }

    public void update(Product editProduct) {
        if (editProduct.getId() == null) return;
        Product find = get(editProduct.getId());
        if (find != null) {
            find.setPrice(editProduct.getPrice());
            find.setName(editProduct.getName());
            find.setCategory(editProduct.getCategory());
            if (editProduct.getImage() != null && !editProduct.getImage().isEmpty()) {
                find.setImage(editProduct.getImage());
            }
            productRepository.save(find);
        }
    }

    public void delete(int id) {
        productRepository.deleteById(id);
    }

    public void updateImage(Product product, MultipartFile imageProduct) {
        if (imageProduct == null || imageProduct.isEmpty()) return;
        String contentType = imageProduct.getContentType();
        if (contentType == null || !contentType.startsWith("image/")) {
            throw new IllegalArgumentException("Tệp tải lên không phải là hình ảnh!");
        }
        try {
            Path dirImages = Path.of(uploadDir).toAbsolutePath().normalize();
            if (!Files.exists(dirImages)) {
                Files.createDirectories(dirImages);
                System.out.println("✓ Tạo thư mục upload: " + dirImages);
            }
            String originalName = imageProduct.getOriginalFilename();
            String ext = originalName != null && originalName.contains(".")
                    ? originalName.substring(originalName.lastIndexOf('.')) : "";
            String newFileName = UUID.randomUUID() + ext;
            Path pathFileUpload = dirImages.resolve(newFileName);
            Files.copy(imageProduct.getInputStream(), pathFileUpload, StandardCopyOption.REPLACE_EXISTING);
            product.setImage(newFileName);
            System.out.println("✓ Lưu ảnh thành công: " + newFileName);
        } catch (IOException e) {
            System.err.println("❌ Lỗi lưu ảnh: " + e.getMessage());
            throw new RuntimeException("Không thể lưu ảnh: " + e.getMessage(), e);
        }
    }
}
