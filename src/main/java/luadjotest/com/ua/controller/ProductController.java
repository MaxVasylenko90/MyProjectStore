package luadjotest.com.ua.controller;

import luadjotest.com.ua.model.Image;
import luadjotest.com.ua.model.Product;
import luadjotest.com.ua.service.CategoryService;
import luadjotest.com.ua.service.PageService;
import luadjotest.com.ua.service.ProductService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("products")
public class ProductController {
    private final ProductService productService;
    private final CategoryService categoryService;
    private final PageService pageService;

    public ProductController(ProductService productService, CategoryService categoryService, PageService pageService) {
        this.productService = productService;
        this.categoryService = categoryService;
        this.pageService = pageService;
    }

    @GetMapping()
    @PreAuthorize("hasAuthority('ADMIN')")
    public String listOfProducts(Model model) {
        return pageService.pageWithPagination(model, 1, Product.class, "id", "desc");
    }

    @GetMapping("/page/{pageNo}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public String pages(Model model, @PathVariable(name = "pageNo") Integer pageNo,
                        @RequestParam("sortField") String sortField,
                        @RequestParam("sortDir") String sortDirection) {
        return pageService.pageWithPagination(model, pageNo, Product.class, sortField, sortDirection);
    }

    @GetMapping("/search")
    @PreAuthorize("hasAuthority('ADMIN')")
    public String search(Model model, @RequestParam(name = "filter") String filter) {
        return pageService.pageWithPagination(model, filter, 1, "id", "desc", true);
    }

    @GetMapping("/search/filter={filter}/page/{pageNo}")
    public String pages(Model model, @PathVariable(name = "filter") String filter, @PathVariable(name = "pageNo") Integer pageNo,
                        @RequestParam("sortField") String sortField,
                        @RequestParam("sortDir") String sortDirection) {
        return pageService.pageWithPagination(model, filter, pageNo, sortField, sortDirection, true);
    }

    @PostMapping("/**")
    @PreAuthorize("hasAuthority('ADMIN')")
    public String addNewProduct(
            @RequestParam String name,
            @RequestParam String description,
            @RequestParam Integer quantity,
            @RequestParam Integer price,
            @RequestParam("images") MultipartFile[] files,
            @RequestParam("mainImage") MultipartFile mainImage,
            @RequestParam String category) {
        productService.addNewProduct(name, description, quantity, price, category, mainImage, files);
        return "redirect:/products";
    }

    @GetMapping("/edit/{product}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public String productEdit(Model model, @PathVariable Product product) {
        model.addAttribute("product", product);
        model.addAttribute("categories", productService.getAllCategories());
        return "productEdit";
    }

    @PostMapping("/edit")
    @PreAuthorize("hasAuthority('ADMIN')")
    public String productEdit(@RequestParam String name,
                              @RequestParam String description,
                              @RequestParam Integer quantity,
                              @RequestParam Integer price,
                              @RequestParam String category,
                              @RequestParam(value = "mainImage") MultipartFile mainImage,
                              @RequestParam(value = "images") MultipartFile[] files,
                              @RequestParam("productId") Product product) {
        productService.editProduct(name, description, quantity, price, category, mainImage, files, product);
        return "redirect:/products";
    }

    @PostMapping("/delete")
    @PreAuthorize("hasAuthority('ADMIN')")
    public String deleteProduct(@RequestParam("productId") Product product) {
        categoryService.deleteProduct(product);
        productService.deleteById(product);
        return "redirect:/products";
    }

    @PostMapping("/image/delete")
    @PreAuthorize("hasAuthority('ADMIN')")
    public String deleteImage(@RequestParam("imageId") Image image, @RequestParam("productId") Product product, RedirectAttributes redirectAttributes) {
        productService.deleteImage(image, product);
        redirectAttributes.addAttribute(product);
        return "redirect:/products/edit/{product}";
    }

    @GetMapping("{id}")
    public String productPage(Model model, @PathVariable("id") Product product) {
        model.addAttribute("product", product);
        model.addAttribute("images", product.getNonMainImages());
        model.addAttribute("start", product.getMainImage());
        model.addAttribute("count", product.countImages());
        return "productPage";
    }
}
