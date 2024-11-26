package io.urdego.user_service.domain;

import io.urdego.user_service.domain.entity.User;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByNickname(String nickname);

    boolean existsByNickname(String nickname);

    Optional<User> findByEmail(String email);

    @Query("select u from User u where u.nickname like %:string%")
    List<User> findByString(String string);
}
