package io.urdego.content_service.domain.entity.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import io.urdego.content_service.domain.entity.user.UserContent;

public interface UserContentRepository extends JpaRepository<UserContent, Long> {}
