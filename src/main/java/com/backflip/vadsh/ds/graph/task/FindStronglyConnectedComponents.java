package com.backflip.vadsh.ds.graph.task;

import com.backflip.vadsh.ds.graph.Config;
import com.backflip.vadsh.ds.graph.Graph;

import java.util.*;

import static com.backflip.vadsh.ds.graph.task.TaskResult.success;
import static java.lang.Math.min;

public class FindStronglyConnectedComponents implements Task {

    @Override
    public TaskResult execute(Graph graph, Config config, Map<String, Integer> params) {

        List<List<Integer>> g = graph.adjacencyList();
        int n = graph.n();

        int[] visited = new int[n];
        for (int j = 0; j < n; j++) visited[j] = -1;

        int[] llv = new int[n];
        for (int j = 0; j < n; j++) llv[j] = -1;

        Stack<Integer> stack = new Stack<>();

        List<Set<Integer>> components = new LinkedList<>();
        Counter c = new Counter();

        int start = getNotVisited(visited);
        while (start != -1) {
            dfs(g, start, visited, llv, stack, components, c);
            start = getNotVisited(visited);
        }

        return success(components);
    }

    private void dfs(List<List<Integer>> g, int current, int[] visited, int[] llv, Stack<Integer> stack, List<Set<Integer>> components, Counter c) {
        if (visited[current] != -1) return;

        visited[current] = c.inc();
        int currRef = visited[current];

        llv[currRef] = currRef;
        stack.add(currRef);

        for (Integer child : g.get(current)) {
            dfs(g, child, visited, llv, stack, components, c);
            if (stack.contains(visited[child])) llv[currRef] = min(llv[visited[child]], llv[currRef]);
        }
        if (llv[currRef] == currRef) {
            Set<Integer> component = new HashSet<>();
            while (stack.peek() != currRef) {
                component.add(getByRef(visited, stack.pop()));
            }
            component.add(getByRef(visited, stack.pop()));
            components.add(component);
        }
    }

    private int getByRef(int[] refs, Integer ref) {
        for (int i = 0; i < refs.length; i++) {
            if (refs[i] == ref) return i;
        }
        return -1;
    }

    private int getNotVisited(int[] visited) {
        for (int i = 0; i < visited.length; i++) {
            if (visited[i] == -1) {
                return i;
            }
        }
        return -1;
    }

    private static class Counter {
        int c = 0;

        int inc() {
            return c++;
        }
    }
}




