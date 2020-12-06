package com.backflip.vadsh.ds.graph.task;

import com.backflip.vadsh.ds.graph.Config;
import com.backflip.vadsh.ds.graph.Edge;
import com.backflip.vadsh.ds.graph.Graph;

import java.util.*;

import static com.backflip.vadsh.ds.graph.task.TaskResponse.emptyResponse;

public class FindShortestPath implements Task {

    @Override
    public TaskResponse execute(Graph graphInput, Config config, Map<String, Integer> params) {
        Map<Integer, List<Edge>> graph = graphInput.adjacencyListAsMap();
        int n = graphInput.n();

        int from = params.get("from");
        int to = params.get("to");

        if (n == 0) {
            return emptyResponse();
        }

        Float[] dist = new Float[n];
        for (int i = 0; i < n; i++) {
            dist[i] = Float.POSITIVE_INFINITY;
        }
        int[] prev = new int[n];
        for (int i = 0; i < n; i++) {
            prev[i] = -1;
        }

        class QueueEntry implements Comparable<QueueEntry> {

            public QueueEntry(Integer index, Float weight) {
                this.index = index;
                this.weight = weight;
            }

            final Integer index;
            final Float weight;

            @Override
            public boolean equals(Object o) {
                if (this == o) return true;
                if (o == null || getClass() != o.getClass()) return false;

                QueueEntry that = (QueueEntry) o;

                if (index != null ? !index.equals(that.index) : that.index != null) return false;
                return weight != null ? weight.equals(that.weight) : that.weight == null;
            }

            @Override
            public int hashCode() {
                int result = index != null ? index.hashCode() : 0;
                result = 31 * result + (weight != null ? weight.hashCode() : 0);
                return result;
            }

            @Override
            public int compareTo(QueueEntry o) {
                return this.weight.compareTo(o.weight) * -1;
            }
        }

        Queue<QueueEntry> pq = new PriorityQueue<>();

        pq.add(new QueueEntry(from, 0f));
        dist[from] = 0f;

        while(!pq.isEmpty()) {
            QueueEntry e = pq.poll();
            List<Edge> outEdges = graph.get(e.index);

            for (Edge edge: outEdges) {
                if (dist[e.index] + edge.weight() < dist[edge.to()]) {
                    dist[edge.to()] = dist[e.index] + (float)edge.weight();
                    pq.add(new QueueEntry(edge.to(), (float)edge.getWeight()));
                    prev[edge.to()] = e.index;
                }
            }
        }

        List<Edge> path = new ArrayList<>();
        int i = to;
        while (i != -1) {
            path.add(new Edge(prev[i], i, 1.0));
            i = prev[i];
        }
        path.remove(path.size()-1);
        Collections.reverse(path);
        return TaskResponse.builder().nodes(Collections.emptyList()).edges(path).build();
    }
}