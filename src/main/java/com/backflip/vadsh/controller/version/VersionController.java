package com.backflip.vadsh.controller.version;


import com.backflip.vadsh.controller.dto.ResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class VersionController {

    @Value("${app.version}")
    private String appVersion;

    @GetMapping("/version")
    public ResponseDto getAppVersion() {
        return VersionResponse.builder().version(appVersion).build();
    }

}
