package com.backflip.vadsh.controller.version;

import lombok.*;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class VersionResponse {
    private String version;
}
