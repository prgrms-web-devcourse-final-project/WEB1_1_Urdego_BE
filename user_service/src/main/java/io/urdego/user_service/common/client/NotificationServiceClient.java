package io.urdego.user_service.common.client;

import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(name ="NOTIFICATION-SERVICE")
public interface NotificationServiceClient {

}
