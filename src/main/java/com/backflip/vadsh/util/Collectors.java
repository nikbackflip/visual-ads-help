package com.backflip.vadsh.util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collector;

public class Collectors {

    private static final Collector<?, ?, ?> SHUFFLER = java.util.stream.Collectors.collectingAndThen(
            java.util.stream.Collectors.toCollection(ArrayList::new),
            list -> {
                Collections.shuffle(list);
                return list;
            }
    );

    @SuppressWarnings("unchecked")
    public static <T> Collector<T, ?, List<T>> toShuffledList() {
        return (Collector<T, ?, List<T>>) SHUFFLER;
    }

}
