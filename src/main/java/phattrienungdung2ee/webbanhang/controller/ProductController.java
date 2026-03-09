package phattrienungdung2ee.webbanhang.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import phattrienungdung2ee.webbanhang.model.Category;
import phattrienungdung2ee.webbanhang.model.Product;
import phattrienungdung2ee.webbanhang.service.CategoryService;
import phattrienungdung2ee.webbanhang.service.ProductService;

@Controller
@RequestMapping("/products")
public class ProductController {

    @Autowired
    private ProductService productService;

    @Autowired
    private CategoryService categoryService;

    @GetMapping
    public String index(Model model) {
        model.addAttribute("listproduct", productService.getAll());
        return "product/products";
    }

    @GetMapping("/create")
    public String create(Model model) {
        model.addAttribute("product", new Product());
        model.addAttribute("categories", categoryService.getAll());
        return "product/create";
    }

    @PostMapping("/create")
    public String create(@Valid @ModelAttribute("product") Product newProduct,
                         BindingResult result,
                         @RequestParam(value = "category.id", required = false) Integer categoryId,
                         @RequestParam(value = "imageProduct", required = false) MultipartFile imageProduct,
                         Model model) {
        if (categoryId == null || categoryId <= 0) {
            result.rejectValue("category", "category.required", "Vui lòng chọn danh mục");
        }
        if (result.hasErrors()) {
            model.addAttribute("categories", categoryService.getAll());
            return "product/create";
        }
        try {
            if (imageProduct != null && !imageProduct.isEmpty()) {
                productService.updateImage(newProduct, imageProduct);
            }
            Category selectedCategory = categoryService.get(categoryId);
            newProduct.setCategory(selectedCategory);
            productService.add(newProduct);
            return "redirect:/products";
        } catch (Exception e) {
            result.reject("error.general", "Lỗi: " + e.getMessage());
            model.addAttribute("categories", categoryService.getAll());
            return "product/create";
        }
    }

    @GetMapping("/edit/{id}")
    public String edit(@PathVariable int id, Model model) {
        Product find = productService.get(id);
        if (find == null) {
            return "redirect:/products";
        }
        model.addAttribute("product", find);
        model.addAttribute("categories", categoryService.getAll());
        return "product/edit";
    }

    @PostMapping("/edit")
    public String edit(@Valid @ModelAttribute("product") Product editProduct,
                       BindingResult result,
                       @RequestParam(value = "imageProduct", required = false) MultipartFile imageProduct,
                       Model model) {
        if (result.hasErrors()) {
            model.addAttribute("categories", categoryService.getAll());
            return "product/edit";
        }
        try {
            if (editProduct.getCategory() != null && editProduct.getCategory().getId() != null && editProduct.getCategory().getId() > 0) {
                Category cat = categoryService.get(editProduct.getCategory().getId());
                editProduct.setCategory(cat);
            }
            if (imageProduct != null && !imageProduct.isEmpty()) {
                productService.updateImage(editProduct, imageProduct);
            }
            productService.update(editProduct);
            return "redirect:/products";
        } catch (Exception e) {
            result.reject("error.general", "Lỗi: " + e.getMessage());
            model.addAttribute("categories", categoryService.getAll());
            return "product/edit";
        }
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable int id) {
        productService.delete(id);
        return "redirect:/products";
    }
}
