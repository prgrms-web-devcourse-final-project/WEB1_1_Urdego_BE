package io.urdego.user_service.domain;

import io.urdego.user_service.domain.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByNickname(String nickname);

    boolean existsByNickname(String nickname);

    Optional<User> findByEmail(String email);

    @Query("select u from User u where u.nickname like %:string%")
    List<User> findByString(String string);

    // Feign userId 검증
    @Query("SELECT u.id FROM User u WHERE u.id IN :userIds")
    List<Long> findByUserIds(@Param("userIds") List<Long> userIds);
}
