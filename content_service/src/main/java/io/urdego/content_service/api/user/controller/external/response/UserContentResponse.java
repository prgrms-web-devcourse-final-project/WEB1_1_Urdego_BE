package io.urdego.content_service.api.user.controller.external.response;

import io.urdego.content_service.domain.entity.user.constant.ContentInfo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class UserContentResponse {

    private Long userId;

    private Long contentId;

    private String url;

    private String contentName;

    private Double latitude;

    private Double longitude;

    private String hint;

    private ContentInfo contentInfo;
}
