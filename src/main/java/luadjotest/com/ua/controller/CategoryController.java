package luadjotest.com.ua.controller;

import luadjotest.com.ua.model.Category;
import luadjotest.com.ua.service.CategoryService;
import luadjotest.com.ua.service.PageService;
import luadjotest.com.ua.service.ProductService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/categories")
public class CategoryController {
    private final CategoryService categoryService;
    private final ProductService productService;
    private final PageService pageService;

    public CategoryController(CategoryService categoryService, ProductService productService, PageService pageService) {
        this.categoryService = categoryService;
        this.productService = productService;
        this.pageService = pageService;
    }

    @GetMapping()
    @PreAuthorize("hasAuthority('ADMIN')")
    public String categoriesList(Model model) {
        return pageService.pageWithPagination(model, 1, Category.class, "id", "desc");
    }

    @GetMapping("/page/{pageNo}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public String pages(Model model, @PathVariable(name = "pageNo") Integer pageNo,
                        @RequestParam("sortField") String sortField,
                        @RequestParam("sortDir") String sortDirection) {
        return pageService.pageWithPagination(model, pageNo, Category.class, sortField, sortDirection);
    }

    @PostMapping()
    @PreAuthorize("hasAuthority('ADMIN')")
    public String addCategory(@RequestParam("name") String name) {
        categoryService.addCategory(name);
        return "redirect:/categories";
    }

    @GetMapping("/{name}")
    public String productCategory(Model model,
                                  @PathVariable("name") String category) {
        return pageService.pageWithPagination(model, 1, category, "id", "desc");
    }

    @GetMapping("/{name}/page/{pageNo}")
    public String pages(Model model, @PathVariable("name") String category,
                        @PathVariable(name = "pageNo") Integer pageNo,
                        @RequestParam("sortField") String sortField,
                        @RequestParam("sortDir") String sortDirection) {
        return pageService.pageWithPagination(model, pageNo, category, sortField, sortDirection);
    }
}
