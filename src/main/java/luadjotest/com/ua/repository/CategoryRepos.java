package luadjotest.com.ua.repository;

import luadjotest.com.ua.model.Category;
import luadjotest.com.ua.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface CategoryRepos extends JpaRepository<Category, Long> {
    @Query("SELECT c FROM Category c WHERE :product MEMBER c.product")
    Category findByProduct(@Param("product") Product product);
}
