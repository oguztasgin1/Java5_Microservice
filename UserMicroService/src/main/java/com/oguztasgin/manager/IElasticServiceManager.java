package com.oguztasgin.manager;

import com.oguztasgin.dto.request.UserProfileSaveRequestDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import static com.oguztasgin.constants.RestEndPoints.SAVE;

@FeignClient(
        name = "user-profile-service-feign",
        //url = "${bu-benim-tanimim.userprofile-url}",
        url = "http://localhost:9999/user",
        decode404 = true
)
public interface IElasticServiceManager {

    @PostMapping(SAVE)
    ResponseEntity<Void> save(@RequestBody UserProfileSaveRequestDto dto);
}
