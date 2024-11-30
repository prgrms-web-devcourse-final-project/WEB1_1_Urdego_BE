package io.urdego.group_service.api.controller.group.websocket;

import io.urdego.group_service.api.controller.group.websocket.dto.MessageRequest;
import io.urdego.group_service.api.controller.group.websocket.dto.MessageResponse;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

@Controller
public class GroupMessageController {

    @MessageMapping("/group")
    @SendTo("/group-service/subscribe/group")
    public MessageResponse sendMessage(MessageRequest request) {
        return new MessageResponse("hello world!");
    }
}
