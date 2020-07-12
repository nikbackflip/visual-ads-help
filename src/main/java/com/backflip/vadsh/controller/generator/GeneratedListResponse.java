package com.backflip.vadsh.controller.generator;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class GeneratedListResponse {
    List<List<Integer>> generated;
}
