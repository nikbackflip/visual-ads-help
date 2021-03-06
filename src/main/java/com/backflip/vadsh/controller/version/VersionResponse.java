package com.backflip.vadsh.controller.version;

import com.backflip.vadsh.controller.dto.ResponseDto;
import lombok.*;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class VersionResponse extends ResponseDto {
    private String version;
}
