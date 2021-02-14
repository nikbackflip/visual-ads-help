package com.backflip.vadsh.controller.generator;

import com.backflip.vadsh.controller.dto.ResponseDto;
import com.backflip.vadsh.ds.graph.Config;
import lombok.*;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class GraphResponse extends ResponseDto {

    private double[][] graph;
    private Config config;

}
