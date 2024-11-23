package domain.entity.user.repository;

import domain.entity.user.UserContent;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UserContentRepository extends JpaRepository<UserContent, Long> {}
