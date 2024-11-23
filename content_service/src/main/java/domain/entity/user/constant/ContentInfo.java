package domain.entity.user.constant;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Embeddable
@Getter
@NoArgsConstructor
public class ContentInfo {

    @Column(name = "place_name")
    private String placeName;

    @Column(name = "height")
    private int height; // 높이

    @Column(name = "width")
    private int width; // 너비

    @Column(name = "size")
    private long size; // 사진 크기

    @Builder
    public ContentInfo(String placeName, int height, int width, long size) {
        this.placeName = placeName;
        this.height = height;
        this.width = width;
        this.size = size;
    }
}
