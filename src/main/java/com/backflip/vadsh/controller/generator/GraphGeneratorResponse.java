package com.backflip.vadsh.controller.generator;

import com.backflip.vadsh.controller.dto.ResponseDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class GraphGeneratorResponse extends ResponseDto {
    String generated;
}
