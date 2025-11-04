package com.inn.client;

import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(name = "ROOMBILLZ-SERVICE", url = "${roombillz.service.url}",
             configuration = {FeignConfig.class,FeignRetryConfig.class})
public interface RoomBillzFeignClient {

}
