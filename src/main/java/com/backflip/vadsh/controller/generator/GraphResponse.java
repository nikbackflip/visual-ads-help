package com.backflip.vadsh.controller.generator;

import com.backflip.vadsh.controller.dto.ResponseDto;
import com.backflip.vadsh.ds.graph.Config;
import com.backflip.vadsh.ds.graph.layout.Coordinates;
import io.micrometer.core.lang.Nullable;
import lombok.*;

import java.util.Map;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class GraphResponse extends ResponseDto {

    private double[][] graph;
    private Config config;

    @Nullable
    private Map<Integer, Coordinates> coordinates;

}
