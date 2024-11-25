package urdego.domain.entity.user.constant;


import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Embeddable
@Getter
@NoArgsConstructor
public class ContentInfo {

    @Column(name = "content_type")
    private String contentType; // 파일 콘텐츠 타입 (ex: image/jpg)

    @Column(name = "meta_latitude")
    private Double metaLatitude;   // 컨텐츠 위치정보

    @Column(name = "meta_longitude")
    private Double metaLongitude;

    @Column(name = "size")
    private long size;    // 사진 크기

    @Builder
    public ContentInfo(String contentType, Double metaLatitude, Double metaLongitude, long size) {
        this.contentType = contentType;
        this.metaLatitude = metaLatitude;
        this.metaLongitude = metaLongitude;
        this.size = size;
    }
}
