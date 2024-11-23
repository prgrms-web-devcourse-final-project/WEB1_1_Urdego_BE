package infra.repository.user;

import application.domain.user.UserContent;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserContentJPARepository extends JpaRepository<UserContent, Long> {
}
