package luadjotest.com.ua.repository;

import luadjotest.com.ua.model.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.*;


public interface ProductRepos extends JpaRepository<Product, Long> {
    Page<Product> findAll(Pageable pageable);

    @Query("SELECT p FROM Product p WHERE p.category.name = :category")
    Page<Product> findAllByCategory(@Param("category") String category, Pageable pageable);

    @Query("SELECT p FROM Product p WHERE p.name LIKE :filter OR p.description LIKE :filter")
    Page<Product> findAllByFilter(@Param("filter") String filter, Pageable pageable);

}




