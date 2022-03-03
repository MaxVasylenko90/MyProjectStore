package luadjotest.com.ua.repository;

import luadjotest.com.ua.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepos extends JpaRepository<Order, Long> {
}
