package io.urdego.content_service.api.user.controller.external.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Builder
@Getter
@AllArgsConstructor
public class UserContentListAndCursorIdxResponse {

    private Long userId;

    private List<UserContentResponse> userContents; // 컨텐츠 정보

    private Long cursorIdx;

    public void setNextCursorIdx() {
        cursorIdx =
                userContents == null || userContents.isEmpty()
                        ? 0L
                        : userContents.get(userContents.size() - 1).getUserId();
    }
}
