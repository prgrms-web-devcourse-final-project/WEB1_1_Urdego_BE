package io.urdego.content_service.domain.entity.user;

import io.urdego.content_service.domain.entity.BaseEntity;
import io.urdego.content_service.domain.entity.user.constant.ContentInfo;

import jakarta.persistence.*;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "user_content")
public class UserContent extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "content_id")
    private Long id;

    @Column(name = "user_id", nullable = false)
    private Long userId;

    @Column(name = "url", columnDefinition = "JSON", nullable = false) // JSON
    private String url;

    @Embedded
    @Column(name = "content_info", length = 255)
    private ContentInfo contentInfo;

    @Column(name = "content_name", nullable = false)
    private String contentName;

    @Column(name = "latitude", precision = 10, scale = 7) // DECIMAL
    private BigDecimal latitude;

    @Column(name = "longitude", precision = 10, scale = 7) // DECIMAL
    private BigDecimal longitude;

    @Column(name = "hint", length = 255)
    private String hint;

    @Builder
    public UserContent(
            Long userId,
            String url,
            ContentInfo contentInfo,
            String contentName,
            BigDecimal latitude,
            BigDecimal longitude,
            String hint) {
        this.userId = userId;
        this.url = url;
        this.contentInfo = contentInfo;
        this.contentName = contentName;
        this.latitude = latitude;
        this.longitude = longitude;
        this.hint = hint;
    }
}
