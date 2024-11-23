package domain.define.user.repository;

import domain.define.user.UserContent;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserContentRepository extends JpaRepository<UserContent, Long> ,UserContentRepositoryCustom{
}
