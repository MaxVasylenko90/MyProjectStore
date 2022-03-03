package luadjotest.com.ua.repository;

import luadjotest.com.ua.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;


public interface UserRepos extends JpaRepository<User, Long> {
    User findByUsername(String login);
    User findByActivationCode(String code);

}
