package com.backflip.vadsh.templates.graph;

import com.backflip.vadsh.service.FileStorage;
import com.backflip.vadsh.templates.Template;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class GraphTemplate extends Template<GraphArgs> {

    private final FileStorage fileStorage;

    @Override
    public String getSource() {
        return "/sources/Graph.java.template";
    }

    @Override
    public String getFinalName() {
        return "Graph.java";
    }

    @Override
    public FileStorage getStorage() {
        return fileStorage;
    }
}