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
    private Double latitude;

    @Column(name = "longitude", precision = 10, scale = 7) // DECIMAL
    private Double longitude;

    @Column(name = "hint", length = 255)
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
