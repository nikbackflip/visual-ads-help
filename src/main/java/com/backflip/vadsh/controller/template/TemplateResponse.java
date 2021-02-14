package com.backflip.vadsh.controller.template;

import com.backflip.vadsh.controller.dto.ResponseDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class TemplateResponse extends ResponseDto {
    String generated;
}
