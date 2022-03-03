package luadjotest.com.ua.service;

import luadjotest.com.ua.model.Category;
import luadjotest.com.ua.model.Product;
import luadjotest.com.ua.model.User;
import luadjotest.com.ua.repository.CategoryRepos;
import luadjotest.com.ua.repository.ProductRepos;
import luadjotest.com.ua.repository.UserRepos;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import java.util.List;

@Service
public class PageService<T> {
    private final ProductRepos productRepos;
    private final UserRepos userRepos;
    private final ProductService productService;
    private final CategoryRepos categoryRepos;

    public PageService(ProductRepos productRepos, UserRepos userRepos, ProductService productService, CategoryRepos categoryRepos) {
        this.productRepos = productRepos;
        this.userRepos = userRepos;
        this.productService = productService;
        this.categoryRepos = categoryRepos;
    }

    public String mainPage(Model model, int pageNo, T obj, String sortField, String sortDir) {
        Page<T> page = findPaginated(pageNo, obj, sortField, sortDir);
        List<T> list = page.getContent();
        model.addAttribute("url", "/");
        model.addAttribute("productList", list);
        addAttributes(model, pageNo, sortField, sortDir, page);
        return "shop";
    }

    public String pageWithPagination(Model model, Integer pageNo, T obj, String sortField, String sortDir) {
        Page<T> page = findPaginated(pageNo, obj, sortField, sortDir);
        List<T> list = page.getContent();
        if (obj.equals(Product.class)) {
            model.addAttribute("url", "/products/");
            model.addAttribute("categories", productService.getAllCategories());
            model.addAttribute("productList", list);
            addAttributes(model, pageNo, sortField, sortDir, page);
            return "productList";
        }
        else if(obj.equals(User.class)){
            model.addAttribute("url", "/user/");
            model.addAttribute("userList", list);
            addAttributes(model, pageNo, sortField, sortDir, page);
            return "userList";
        }
        else if(obj.equals(Category.class)){
            model.addAttribute("url", "/categories/");
            model.addAttribute("categoryList", list);
            addAttributes(model, pageNo, sortField, sortDir, page);
            return "categories";
        }
        return null;
    }

    public Page<T> findPaginated(int pageNo, T object, String sortField, String sortDir) {
        Pageable pageable = getPageable(pageNo, sortField, sortDir);
        if (object.equals(Product.class))
            return (Page<T>) productRepos.findAll(pageable);
        else if (object.equals(User.class))
            return (Page<T>) userRepos.findAll(pageable);
        else if (object.equals(Category.class))
            return (Page<T>) categoryRepos.findAll(pageable);
        return null;
    }

    public String pageWithPagination(Model model, Integer pageNo, String category, String sortField, String sortDir) {
        Page<Product> page = findPaginated(pageNo, category, sortField, sortDir);
        List<Product> list = page.getContent();
        model.addAttribute("url", "/categories/" + category + "/");
        model.addAttribute("categoryName" , category);
        model.addAttribute("productList", list);

        addAttributes(model, pageNo, sortField, sortDir, page);
        return "shop";
    }

    public Page<Product> findPaginated(int pageNo, String category, String sortField, String sortDir) {
        Pageable pageable = getPageable(pageNo, sortField, sortDir);
        return productRepos.findAllByCategory(category, pageable);
    }

    public String pageWithPagination(Model model, String filter, Integer pageNo, String sortField, String sortDir, Boolean isAdmin) {
        Page<Product> page = findPaginated(filter, pageNo, sortField, sortDir);
        List<Product> list = page.getContent();
        model.addAttribute("productList", list);
        addAttributes(model, pageNo, sortField, sortDir, page);
        if(isAdmin) {
            String url = "/products/search/filter=" + filter + "/";
            model.addAttribute("url", url);
            model.addAttribute("categories", productService.getAllCategories());
            return "productList";
        }
        else {
            String url = "/search/filter=" + filter + "/";
            model.addAttribute("url", url);
            return "shop";
        }
    }

    public Page<Product> findPaginated(String filter, int pageNo, String sortField, String sortDir) {
        Pageable pageable = getPageable(pageNo, sortField, sortDir);
        String newFilter = "%" + filter + "%";
        return productRepos.findAllByFilter(newFilter, pageable);
    }

    private Pageable getPageable(int pageNo, String sortField, String sortDir) {
        Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortField).ascending() : Sort.by(sortField).descending();
        return PageRequest.of(pageNo - 1, 12, sort);
    }

    private void addAttributes(Model model, Integer pageNo, String sortField, String sortDir, Page<?> page) {
        model.addAttribute("currentPage", pageNo);
        model.addAttribute("totalPages", page.getTotalPages());
        model.addAttribute("sortField", sortField);
        model.addAttribute("sortDir", sortDir);
        model.addAttribute("reverseSortDir", sortDir.equals("asc") ? "desc" : "asc");
    }
}
