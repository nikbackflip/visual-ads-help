package com.backflip.vadsh.templates.graph;

import com.backflip.vadsh.templates.TemplateArgs;

import java.util.Map;

import static java.lang.String.format;
import static java.util.Map.of;
import static java.util.Collections.unmodifiableMap;

public class GraphArgs implements TemplateArgs {

    private GraphArgs(String nodeIdToNameMap, String edgesList) {
        this.nodeIdToNameMap = nodeIdToNameMap;
        this.edgesList = edgesList;
    }

    private String nodeIdToNameMap;
    private String edgesList;

    @Override
    public Map<String, String> getArgs() {
        return unmodifiableMap(of(
                "nodeIdToNameMap", nodeIdToNameMap,
                "edgesList", edgesList
        ));
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {

        private StringBuilder edges = new StringBuilder();
        private StringBuilder nodes = new StringBuilder();

        public Builder withEdge(int from, int to, double weight) {
            edges.append(format("        add(new Edge(%s, %s, %s));\n", from, to, weight));
            return this;
        }

        public Builder withNode(int id, String name) {
            nodes.append(format("        put(%s, \"%s\");\n", id, name));
            return this;
        }

        public GraphArgs build() {
            return new GraphArgs(nodes.toString(), edges.toString());
        }

    }
}
