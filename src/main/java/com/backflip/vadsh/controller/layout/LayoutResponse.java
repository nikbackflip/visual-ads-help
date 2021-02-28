package com.backflip.vadsh.controller.layout;


import com.backflip.vadsh.controller.dto.ResponseDto;
import com.backflip.vadsh.ds.graph.layout.Coordinates;
import lombok.*;

import java.util.Map;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LayoutResponse extends ResponseDto {

    private Map<Integer, Coordinates> coordinates;

}
