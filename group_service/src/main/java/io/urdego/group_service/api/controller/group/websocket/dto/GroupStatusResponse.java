package io.urdego.group_service.api.controller.group.websocket.dto;

import lombok.Data;

import java.util.List;

@Data
public class GroupStatusResponse {
     List<GroupMemberStatusResponse> waitingRoomParticipants;

     private GroupStatusResponse(List<GroupMemberStatusResponse> waitingRoomParticipants) {
          this.waitingRoomParticipants = waitingRoomParticipants;
     }

     public static GroupStatusResponse of(List<GroupMemberStatusResponse> groupMemberStatusResponseList) {
          return new GroupStatusResponse(groupMemberStatusResponseList);
     }
}
