package com.backflip.vadsh.controller.generator;

import com.backflip.vadsh.controller.dto.ResponseDto;
import com.backflip.vadsh.ds.graph.generator.GeneratorOption;
import lombok.*;

import java.util.Set;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OptionsResponse extends ResponseDto {

    private Set<GeneratorOption> availableOptions;

}
