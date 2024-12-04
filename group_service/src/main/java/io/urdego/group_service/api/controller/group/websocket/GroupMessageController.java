package io.urdego.group_service.api.controller.group.websocket;

import io.urdego.group_service.api.controller.group.api.dto.response.GroupInfoRes;
import io.urdego.group_service.api.controller.group.websocket.request.MessageRequest;
import io.urdego.group_service.api.controller.group.websocket.response.GameStartResponse;
import io.urdego.group_service.api.controller.group.websocket.response.GroupStatusResponse;
import io.urdego.group_service.api.controller.group.websocket.response.GroupMemberStatusResponse;
import io.urdego.group_service.api.controller.group.websocket.response.MessageResponse;
import io.urdego.group_service.api.controller.groupMember.dto.response.GroupMemberListRes;
import io.urdego.group_service.api.controller.groupMember.dto.response.GroupMemberRes;
import io.urdego.group_service.api.service.group.GroupService;
import io.urdego.group_service.api.service.groupMember.GroupMemberService;
import io.urdego.group_service.common.client.UserServiceClient;
import io.urdego.group_service.common.client.response.ResponseUserInfo;
import io.urdego.group_service.domain.entity.groupMember.GroupMember;
import io.urdego.group_service.domain.entity.groupMember.GroupMemberRole;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

import java.util.List;


@Controller
@RequiredArgsConstructor
@Slf4j
public class GroupMessageController {

    private final GroupMemberService groupMemberService;
    private final MessageDataReader messageDataReader;
    private final GroupService groupService;
    private final UserServiceClient userServiceClient;

    @MessageMapping("/group/{groupId}")
    @SendTo("/group-service/subscribe/group/{groupId}")
    public MessageResponse<?> sendMessage(MessageRequest request, @DestinationVariable Long groupId) {
        return switch (request.eventType()) {
            case PARTICIPANT -> {
                String nickname = messageDataReader.readString(request.data().get("nickname"));
                GroupMemberRole role = GroupMemberRole.valueOf(messageDataReader.readString(request.data().get("role")));
                groupMemberService.addMember(groupId, nickname, role);

                List<GroupMemberStatusResponse> groupMemberStatusResponseList = groupMemberService.getStatus(groupId);
                yield new MessageResponse<>(GroupStatusResponse.of(groupMemberStatusResponseList));
            }
            case READY -> {
                String nickname = messageDataReader.readString(request.data().get("nickname"));
                groupMemberService.ready(groupId, nickname);

                List<GroupMemberStatusResponse> groupMemberStatusResponseList = groupMemberService.getStatus(groupId);
                yield new MessageResponse<>(GroupStatusResponse.of(groupMemberStatusResponseList));
            }
            case START -> {
                Long gameId = messageDataReader.readLong(request.data().get("gameId"));

                Integer rounds = groupService.getGroupInfo(groupId).totalRounds();
                List<String> nicknameList =
                        groupMemberService.getMemberList(groupId).
                                members().stream().parallel().
                                map(GroupMemberRes::userId).
                                map(userServiceClient::getUserById).
                                map(ResponseUserInfo::nickname).toList();
                yield new MessageResponse<>(GameStartResponse.of(gameId, nicknameList, rounds));
            }
        };
    }
}
