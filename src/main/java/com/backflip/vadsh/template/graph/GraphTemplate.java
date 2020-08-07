package com.backflip.vadsh.template.graph;

import com.backflip.vadsh.template.Template;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class GraphTemplate extends Template<GraphArgs> {

    @Override
    public String getSource() {
        return "sources/Graph.java.template";
    }

    @Override
    public String getFinalName() {
        return "Graph.java";
    }

}
