package io.urdego.group_service.common.client;

import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(name = "NOTIFICATION-SERVICE")
public interface NotificationServiceClient {


}
