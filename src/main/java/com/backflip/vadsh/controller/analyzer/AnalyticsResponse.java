package com.backflip.vadsh.controller.analyzer;

import com.backflip.vadsh.controller.dto.ResponseDto;
import lombok.*;

import java.util.LinkedList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AnalyticsResponse extends ResponseDto {

    private List<AnalyticResult> analytics;

    public static Builder builder() {
        return new Builder();
    }

    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    private static class AnalyticResult {
        private String name;
        private Boolean result;
    }

    public static class Builder {

        private final List<AnalyticResult> analytics = new LinkedList<>();

        public Builder withAnalytic(String name, Boolean result) {
            analytics.add(new AnalyticResult(name, result));
            return this;
        }

        public AnalyticsResponse build() {
            return new AnalyticsResponse(analytics);
        }
    }

}
