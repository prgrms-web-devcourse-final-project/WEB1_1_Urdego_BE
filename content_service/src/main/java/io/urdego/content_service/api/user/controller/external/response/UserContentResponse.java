package io.urdego.content_service.api.user.controller.external.response;

import io.urdego.content_service.domain.entity.user.UserContent;
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

    private String address;

    private Double latitude;

    private Double longitude;

    private String hint;

    private ContentInfo contentInfo;


    public static UserContentResponse of(UserContent userContent) {
        return UserContentResponse.builder()
                .userId(userContent.getUserId())
                .contentId(userContent.getId())
                .contentName(userContent.getContentName())
                .address(userContent.getAddress())
                .latitude(userContent.getLatitude())
                .longitude(userContent.getLongitude())
                .hint(userContent.getHint())
                .contentInfo(userContent.getContentInfo())
                .build();
    }
}
