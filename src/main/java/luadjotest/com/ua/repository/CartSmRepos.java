package luadjotest.com.ua.repository;

import luadjotest.com.ua.model.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface CartSmRepos extends JpaRepository<CartSmall, Long> {
    @Query("SELECT c FROM CartSmall c WHERE c.product = :product AND c.cart = :cart")
    CartSmall findByProductAndCart(@Param("product") Product product, @Param("cart") Cart cart);
}
