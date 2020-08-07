package com.backflip.vadsh.controller.analyzer;

import com.backflip.vadsh.controller.dto.ResponseDto;
import lombok.*;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AnalyzerResponse extends ResponseDto {

    private boolean checked;

}
