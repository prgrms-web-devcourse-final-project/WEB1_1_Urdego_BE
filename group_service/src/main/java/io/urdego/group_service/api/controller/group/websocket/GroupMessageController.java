package io.urdego.group_service.api.controller.group.websocket;

import io.urdego.group_service.api.controller.group.websocket.dto.MessageRequest;
import io.urdego.group_service.api.controller.group.websocket.dto.MessageResponse;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

@Controller
public class GroupMessageController {

    @MessageMapping("/group/{groupId}")
    @SendTo("/group-service/subscribe/group/{groupId}")
    public MessageResponse sendMessage(MessageRequest request, @DestinationVariable Long groupId) {

        return new MessageResponse("hello world!");
    }

}
