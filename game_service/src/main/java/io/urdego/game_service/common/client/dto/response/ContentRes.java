package io.urdego.game_service.common.client.dto.response;

public record ContentRes(
        Long userId,
        Long contentId,
        String url,
        String contentName,
        String address,
        Double latitude,
        Double longitude,
        String hint
) {

}
