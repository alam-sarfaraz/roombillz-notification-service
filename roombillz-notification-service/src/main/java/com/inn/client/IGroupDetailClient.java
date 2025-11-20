package com.inn.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.inn.dto.GroupDetail;

@FeignClient(name = "ROOMBILLZ-SERVICE", url = "${roombillz.service.url}",path = "/groupDetail", configuration = { FeignConfig.class, FeignRetryConfig.class })
public interface IGroupDetailClient {

	@GetMapping(path = "/findByGroupName")
	ResponseEntity<GroupDetail> findByGroupName(@RequestParam(name = "groupName") String groupName);
}
