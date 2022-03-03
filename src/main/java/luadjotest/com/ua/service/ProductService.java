package luadjotest.com.ua.service;

import luadjotest.com.ua.model.Category;
import luadjotest.com.ua.model.Image;
import luadjotest.com.ua.model.Product;
import luadjotest.com.ua.repository.CategoryRepos;
import luadjotest.com.ua.repository.ImageRepos;
import luadjotest.com.ua.repository.ProductRepos;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.criteria.CriteriaBuilder;
import java.io.File;
import java.io.IOException;
import java.util.*;

@Service
public class ProductService {
    @Value("${upload.path}")
    private String uploadPath;
    private final ProductRepos productRepos;
    private final ImageRepos imageRepos;
    private final CategoryRepos categoryRepos;

    public ProductService(ProductRepos productRepos, ImageRepos imageRepos, CategoryRepos categoryRepos) {
        this.productRepos = productRepos;
        this.imageRepos = imageRepos;
        this.categoryRepos = categoryRepos;
    }

    public Page<Product> getStandardPageOfProducts(Integer pageNo) {
        int pageSize = 12;
        Pageable pageable = PageRequest.of(pageNo - 1, pageSize);
        return productRepos.findAll(pageable);
//        return productRepos.findAll(pageable);
    }

    public Page<Product> getSortedPageOfProducts(String sort, Pageable pageable) {
        Pageable sortedByPrice;
        if (sort.equals("priceDesc"))
            sortedByPrice = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), Sort.by("price").descending());
        else sortedByPrice = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), Sort.by("price").ascending());
        return productRepos.findAll(sortedByPrice);
    }

    public void editProduct(String name, String description, Integer quantity, Integer price,
                            String category, MultipartFile mainImage, MultipartFile[] images,
                            Product product){
        product.setName(name);
        product.setDescription(description);
        product.setQuantity(quantity);
        product.setPrice(price);
        for(Category category1: categoryRepos.findAll()) {
            if (category1.getName().equals(category))
                product.setCategory(category1);
        }
        if(!mainImage.getOriginalFilename().isEmpty())
            setMainImage(mainImage, product);
        if(!images[0].getOriginalFilename().isEmpty()) {
            for (MultipartFile image : images)
                createImage(image, product, false);
        }
        productRepos.save(product);
    }

    public void addNewProduct(String name, String description, Integer quantity, Integer price,
                               String category, MultipartFile mainImage, MultipartFile[] images) {
        Product product = new Product(name, description, quantity, price);
        for(Category category1: categoryRepos.findAll()) {
            if (category1.getName().equals(category))
                product.setCategory(category1);
        }
        productRepos.save(product);
        if(images != null ) {
            File uploadDir = new File(uploadPath);
            if (!uploadDir.exists())
                uploadDir.mkdir();
            if(!mainImage.getOriginalFilename().isEmpty())
                createImage(mainImage, product, true);
            if(!images[0].getOriginalFilename().isEmpty()) {
                for (MultipartFile image : images)
                    createImage(image, product, false);
            }
        }
    }

    private void createImage(MultipartFile file, Product product, boolean isMainImage) {
        try {
            if(!(file == null) & !(product == null)) {
                String imageFilename = randomNameAndTransferImage(file);
                Image image;
                if (isMainImage)
                    image = new Image(imageFilename, product, true);
                else
                    image = new Image(imageFilename, product, false);
                imageRepos.save(image);
                product.getImages().add(image);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String randomNameAndTransferImage(MultipartFile file) throws IOException {
        String uuidFile = UUID.randomUUID().toString();
        String resultFilename = uuidFile + "." + file.getOriginalFilename();
        file.transferTo(new File(uploadPath + "/" + resultFilename));
        return resultFilename;
    }



    public void deleteById(Product product) {
        for(Image image : product.getImages()) {
            File tmp = new File(uploadPath + "/" + image.getFileName());
            tmp.delete();
            imageRepos.deleteById(image.getId());
        }
        productRepos.deleteById(product.getId());
    }

    public List<Category> getAllCategories() {
       List<Category> categories = categoryRepos.findAll();
       return categories;
    }

//    public String find(Model model, String filter, Integer pageNo) {
//
//        Pageable pageable = PageRequest.of(pageNo - 1, 4);
//        Page page = productRepos.find(filter, pageable);
//        List<Product> list = page.getContent();
//        model.addAttribute("currentPage", pageNo);
//        model.addAttribute("totalPages", page.getTotalPages());
//        model.addAttribute("url", "/?filter=" + filter);
//        model.addAttribute("productList", list);
//        return "shop";
//    }

    public void setMainImage(MultipartFile newMainImage, Product product) {
        List<Image> mainImage = imageRepos.findMainOrNonMainImage(true,product);
        if(mainImage.size() == 0)
            createImage(newMainImage, product, true);
        else {
            File tmp = new File(uploadPath + "/" + mainImage.get(0).getFileName());
            tmp.delete();
            product.removeImage(mainImage.get(0));
            createImage(newMainImage, product, true);
        }
        //        Image img = product.findMainImage();
    }


    public void deleteImage(Image image, Product product) {
        File tmp = new File(uploadPath + "/" + image.getFileName());
        tmp.delete();
        product.removeImage(image);
        imageRepos.deleteById(image.getId());

    }


}
