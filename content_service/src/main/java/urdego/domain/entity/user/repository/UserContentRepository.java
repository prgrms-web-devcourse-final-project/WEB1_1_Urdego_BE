package urdego.domain.entity.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import urdego.domain.entity.user.UserContent;

public interface UserContentRepository extends JpaRepository<UserContent, Long> {}
