package io.urdego.group_service.application.domain;

import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class Group {
    private Long groupId;
    private Long userId;
    private String name;
    private Integer maximum;
    private String status;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private String description;

    private Group(Long groupId, Long userId, String name, String description, Integer maximum, String status, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.groupId = groupId;
        this.userId = userId;
        this.name = name;
        this.description = description;
        this.maximum = maximum;
        this.status = status;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }


}
