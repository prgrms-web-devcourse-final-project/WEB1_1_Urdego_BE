package application.domain.user;

import infra.entity.user.constant.ContentInfo;
import lombok.Getter;

@Getter
public class UserContent {

    private Long contentId;

    private Long userId;

    private String url;

    private ContentInfo contentInfo;

    private String contentName;

    private Double latitude;

    private Double longitude;

    private String hint;

    public UserContent(Long contentId, Long userId, String url, ContentInfo contentInfo, String contentName, Double latitude, Double longitude, String hint) {
        this.contentId = contentId;
        this.userId = userId;
        this.url = url;
        this.contentInfo = contentInfo;
        this.contentName = contentName;
        this.latitude = latitude;
        this.longitude = longitude;
        this.hint = hint;
    }
}
