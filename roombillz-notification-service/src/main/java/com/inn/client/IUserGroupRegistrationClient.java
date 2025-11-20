package com.inn.client;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "ROOMBILLZ-SERVICE", url = "${roombillz.service.url}", path = "/userGroupRegistration", configuration = { FeignConfig.class, FeignRetryConfig.class })
public interface IUserGroupRegistrationClient {

	@GetMapping(path = "/getUserListByGroupName", produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<List<String>> getUserListByGroupName(@RequestParam(name = "groupName") String groupName);

	@GetMapping(path = "/getEmailListByGroupName", produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<List<String>> getEmailListByGroupName(@RequestParam(name = "groupName") String groupName);
}
