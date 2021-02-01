package com.backflip.vadsh.controller.analytics;

import com.backflip.vadsh.controller.dto.ResponseDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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
