package io.urdego.content_service.domain.entity.user.repository;

import io.urdego.content_service.domain.entity.user.UserContent;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UserContentRepository
        extends JpaRepository<UserContent, Long>, UserContentRepositoryCustom {}
