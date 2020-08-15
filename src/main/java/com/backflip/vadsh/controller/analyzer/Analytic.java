package com.backflip.vadsh.controller.analyzer;

public enum Analytic {

    DIRECTIONAL("directional"),
    WEIGHTED("weighted"),
    COMPLETE("complete"),
    TREE("tree"),
    DAG("DAG");

    Analytic(String label) {
        this.label = label;
    }

    private String label;

    public String label() {
        return label;
    }
}
