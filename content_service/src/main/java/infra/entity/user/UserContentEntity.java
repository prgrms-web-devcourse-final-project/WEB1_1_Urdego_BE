package infra.entity.user;

import infra.entity.BaseEntity;
import infra.entity.user.constant.ContentInfo;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "user_content")
public class UserContentEntity extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "CONTENT_ID")
    private Long id;

    @Column(name = "USER_ID", nullable = false)
    private Long userId;

    @Column(name = "URL", columnDefinition = "JSON", nullable = false) // JSON
    private String url;

    @Embedded
    @Column(name = "INFO", length = 255)
    private ContentInfo contentInfo;

    @Column(name = "CONTENT_NAME", nullable = false)
    private String contentName;

    @Column(name = "LATITUDE", precision = 10, scale = 7) // DECIMAL
    private Double latitude;

    @Column(name = "LONGITUDE", precision = 10, scale = 7) // DECIMAL
    private Double longitude;

    @Column(name = "HINT", length = 255)
    private String hint;


    @Builder
    public UserContentEntity(Long userId, String url, ContentInfo contentInfo, String contentName, Double latitude, Double longitude, String hint) {
        this.userId = userId;
        this.url = url;
        this.contentInfo = contentInfo;
        this.contentName = contentName;
        this.latitude = latitude;
        this.longitude = longitude;
        this.hint = hint;
    }

}
