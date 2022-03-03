package luadjotest.com.ua.repository;

import luadjotest.com.ua.model.Cart;
import luadjotest.com.ua.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartRepos extends JpaRepository<Cart, Long> {
}
