package urdego.api.user.controller.request;

import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ContentUploadRequest {

    private Long userId;

    private String contentName;

    private Double latitude;

    private Double longitude;

    @Size(max = 20, message = "힌트 20자 이내")
    private String hint;
}