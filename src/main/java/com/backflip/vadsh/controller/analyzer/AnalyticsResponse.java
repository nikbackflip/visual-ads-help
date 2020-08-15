package com.backflip.vadsh.controller.analyzer;

import com.backflip.vadsh.controller.dto.ResponseDto;
import lombok.*;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AnalyticsResponse extends ResponseDto {

    private List<AnalyticResult> analytics;

    public static AnalyticsResponseBuilder builder() {
        return new AnalyticsResponseBuilder();
    }

    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    private static class AnalyticResult {
        private String name;
        private Boolean result;
    }

    public static class AnalyticsResponseBuilder {

        private final List<AnalyticResult> analytics = new LinkedList<>();

        public AnalyticsResponseBuilder withAnalytic(String name, Boolean result) {
            analytics.add(new AnalyticResult(name, result));
            return this;
        }

        public AnalyticsResponse build() {
            return new AnalyticsResponse(analytics);
        }
    }

}
