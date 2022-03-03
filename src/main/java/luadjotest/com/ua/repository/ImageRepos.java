package luadjotest.com.ua.repository;

import luadjotest.com.ua.model.Image;
import luadjotest.com.ua.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

public interface ImageRepos extends JpaRepository<Image, Long> {
    @Query("SELECT i FROM Image i WHERE i.isMainImage = :isMainImage AND i.product = :product")
    public List<Image> findMainOrNonMainImage(@RequestParam ("isMainImage") Boolean isMainImage,
                                              @RequestParam ("product") Product product);
}
