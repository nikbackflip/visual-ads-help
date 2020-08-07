package com.backflip.vadsh.template.graph;

import com.backflip.vadsh.template.TemplateArgs;

import java.util.Map;

import static java.lang.String.format;
import static java.util.Map.of;
import static java.util.Collections.unmodifiableMap;

public class GraphArgs implements TemplateArgs {

    private GraphArgs(String edgesList, String n) {
        this.edgesList = edgesList;
        this.n = n;
    }

    private final String edgesList;
    private final String n;

    @Override
    public Map<String, String> getArgs() {
        return unmodifiableMap(of(
                "nodesCount", n,
                "edgesList", edgesList
        ));
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {

        private final StringBuilder edges = new StringBuilder();
        private String n = "0";

        public Builder withEdge(int from, int to, double weight) {
            edges.append(format("        add(new Edge(%s, %s, %s));\n", from, to, weight));
            return this;
        }

        public Builder ofSize(Integer n) {
            this.n = n.toString();
            return this;
        }

        public GraphArgs build() {
            return new GraphArgs(edges.toString(), n);
        }

    }
}
