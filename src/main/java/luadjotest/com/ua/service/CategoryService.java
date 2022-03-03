package luadjotest.com.ua.service;

import luadjotest.com.ua.controller.CategoryController;
import luadjotest.com.ua.model.Category;
import luadjotest.com.ua.model.Product;
import luadjotest.com.ua.repository.CategoryRepos;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryService {
    private final CategoryRepos categoryRepos;

    public CategoryService(CategoryRepos categoryRepos) {
        this.categoryRepos = categoryRepos;
    }

    public Page<Category> findAll(Pageable pageable) {
        return categoryRepos.findAll(pageable);
    }

    public void addCategory(String name) {
        Category category = new Category(name);
        categoryRepos.save(category);
    }

    public void deleteProduct(Product product) {
        Category category = categoryRepos.findByProduct(product);
        category.getProduct().remove(product);
        categoryRepos.save(category);
    }
}

