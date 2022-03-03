package luadjotest.com.ua.repository;

import luadjotest.com.ua.model.TempUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TempUserRepos extends JpaRepository<TempUser, Long> {
}
