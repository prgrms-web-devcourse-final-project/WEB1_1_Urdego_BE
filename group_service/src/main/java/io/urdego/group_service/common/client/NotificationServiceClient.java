package io.urdego.group_service.common.client;

import io.urdego.group_service.common.client.request.NotificationRequestInfo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "NOTIFICATION-SERVICE")
public interface NotificationServiceClient {

    @PostMapping("/api/notification-service/notifications/send")
    String sendNotification(@RequestBody NotificationRequestInfo requestInfo);

}
