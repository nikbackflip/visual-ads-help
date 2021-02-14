package com.backflip.vadsh.ds.graph.generator;

import org.junit.jupiter.api.Test;
import java.util.Set;

import static com.backflip.vadsh.ds.graph.generator.GeneratorOption.*;
import static org.hamcrest.Matchers.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class GeneratorOptionTest {

    @Test
    public void getRemainingOptionsForDirected() {
        //given

        //when
        Set<GeneratorOption> remainingOptions = DIRECTED.compatibleOptions();

        //then
        assertThat(remainingOptions, containsInAnyOrder(WEIGHTED, NOT_WEIGHTED, COMPLETE, SPARSE, DENSE, DAG, CYCLIC, ACYCLIC, RANDOM));
        assertThat(remainingOptions, not(containsInAnyOrder(DIRECTED, NOT_DIRECTED)));
    }

    @Test
    public void getRemainingOptionsForNotDirected() {
        //given

        //when
        Set<GeneratorOption> remainingOptions = NOT_DIRECTED.compatibleOptions();

        //then
        assertThat(remainingOptions, containsInAnyOrder(WEIGHTED, NOT_WEIGHTED, COMPLETE, SPARSE, DENSE, CYCLIC, ACYCLIC, RANDOM));
        assertThat(remainingOptions, not(containsInAnyOrder(DIRECTED, NOT_DIRECTED, DAG)));
    }

    @Test
    public void getRemainingOptionsForWeighted() {
        //given

        //when
        Set<GeneratorOption> remainingOptions = WEIGHTED.compatibleOptions();

        //then
        assertThat(remainingOptions, containsInAnyOrder(DIRECTED, NOT_DIRECTED, COMPLETE, SPARSE, DENSE, DAG, CYCLIC, ACYCLIC, RANDOM));
        assertThat(remainingOptions, not(containsInAnyOrder(WEIGHTED, NOT_WEIGHTED)));
    }

    @Test
    public void getRemainingOptionsNotWeighted() {
        //given

        //when
        Set<GeneratorOption> remainingOptions = NOT_WEIGHTED.compatibleOptions();

        //then
        assertThat(remainingOptions, containsInAnyOrder(DIRECTED, NOT_DIRECTED, COMPLETE, SPARSE, DENSE, DAG, CYCLIC, ACYCLIC, RANDOM));
        assertThat(remainingOptions, not(containsInAnyOrder(WEIGHTED, NOT_WEIGHTED)));
    }

    @Test
    public void getRemainingOptionsForComplete() {
        //given

        //when
        Set<GeneratorOption> remainingOptions = COMPLETE.compatibleOptions();

        //then
        assertThat(remainingOptions, containsInAnyOrder(DIRECTED, NOT_DIRECTED, WEIGHTED, NOT_WEIGHTED, CYCLIC));
        assertThat(remainingOptions, not(containsInAnyOrder(COMPLETE, SPARSE, DENSE, DAG, ACYCLIC, RANDOM)));
    }

    @Test
    public void getRemainingOptionsForSparse() {
        //given

        //when
        Set<GeneratorOption> remainingOptions = SPARSE.compatibleOptions();

        //then
        assertThat(remainingOptions, containsInAnyOrder(DIRECTED, NOT_DIRECTED, WEIGHTED, NOT_WEIGHTED, DAG, CYCLIC, ACYCLIC, RANDOM));
        assertThat(remainingOptions, not(containsInAnyOrder(COMPLETE, SPARSE, DENSE)));
    }

    @Test
    public void getRemainingOptionsForDense() {
        //given

        //when
        Set<GeneratorOption> remainingOptions = DENSE.compatibleOptions();

        //then
        assertThat(remainingOptions, containsInAnyOrder(DIRECTED, NOT_DIRECTED, WEIGHTED, NOT_WEIGHTED, DAG, CYCLIC, ACYCLIC, RANDOM));
        assertThat(remainingOptions, not(containsInAnyOrder(COMPLETE, SPARSE, DENSE)));
    }

    @Test
    public void getRemainingOptionsForDag() {
        //given

        //when
        Set<GeneratorOption> remainingOptions = DAG.compatibleOptions();

        //then
        assertThat(remainingOptions, containsInAnyOrder(DIRECTED, WEIGHTED, NOT_WEIGHTED, SPARSE, DENSE, ACYCLIC));
        assertThat(remainingOptions, not(containsInAnyOrder(NOT_DIRECTED, COMPLETE, DAG, CYCLIC, RANDOM)));
    }

    @Test
    public void getRemainingOptionsForCyclic() {
        //given

        //when
        Set<GeneratorOption> remainingOptions = CYCLIC.compatibleOptions();

        //then
        assertThat(remainingOptions, containsInAnyOrder(DIRECTED, NOT_DIRECTED, WEIGHTED, NOT_WEIGHTED, COMPLETE, SPARSE, DENSE));
        assertThat(remainingOptions, not(containsInAnyOrder(DAG, CYCLIC, ACYCLIC, RANDOM)));
    }

    @Test
    public void getRemainingOptionsForAcyclic() {
        //given

        //when
        Set<GeneratorOption> remainingOptions = ACYCLIC.compatibleOptions();

        //then
        assertThat(remainingOptions, containsInAnyOrder(DIRECTED, NOT_DIRECTED, WEIGHTED, NOT_WEIGHTED, SPARSE, DENSE, DAG));
        assertThat(remainingOptions, not(containsInAnyOrder(COMPLETE, CYCLIC, ACYCLIC, RANDOM)));
    }

    @Test
    public void getRemainingOptionsForRandom() {
        //given

        //when
        Set<GeneratorOption> remainingOptions = RANDOM.compatibleOptions();

        //then
        assertThat(remainingOptions, containsInAnyOrder(DIRECTED, NOT_DIRECTED, WEIGHTED, NOT_WEIGHTED, SPARSE, DENSE));
        assertThat(remainingOptions, not(containsInAnyOrder(DAG, COMPLETE, CYCLIC, ACYCLIC, RANDOM)));
    }

    @Test
    public void verifyOptionsCompatible() {
        //given
        Set<GeneratorOption> options = Set.of(DIRECTED, WEIGHTED, DAG, DENSE);

        //when
        boolean result = compatible(options);

        //then
        assertTrue(result);
    }

    @Test
    public void verifyOptionsIncompatible() {
        //given
        Set<GeneratorOption> options = Set.of(COMPLETE, WEIGHTED, ACYCLIC);

        //when
        boolean result = compatible(options);

        //then
        assertFalse(result);
    }

    @Test
    public void verifyOptionsPairCompatible() {
        //given

        //when
        boolean result = NOT_WEIGHTED.compatibleWith(COMPLETE);

        //then
        assertTrue(result);
    }

    @Test
    public void verifyOptionsPairIncompatible() {
        //given

        //when
        boolean result = NOT_WEIGHTED.compatibleWith(WEIGHTED);

        //then
        assertFalse(result);
    }

    @Test
    public void verifyOptionCompatibleWithSet() {
        //given

        //when
        boolean result = COMPLETE.compatibleWith(Set.of(WEIGHTED, NOT_DIRECTED, CYCLIC));

        //then
        assertTrue(result);
    }

    @Test
    public void verifyOptionIncompatibleWithSet() {
        //given

        //when
        boolean result = DAG.compatibleWith(Set.of(SPARSE, CYCLIC));

        //then
        assertFalse(result);
    }

    @Test
    public void getRemainingOptions() {
        //given
        Set<GeneratorOption> currentOptions = Set.of(WEIGHTED, NOT_DIRECTED);

        //when
        Set<GeneratorOption> remainingOptions = compatibleOptionsFor(currentOptions);

        //then
        assertThat(remainingOptions, containsInAnyOrder(COMPLETE, SPARSE, DENSE, CYCLIC, ACYCLIC, RANDOM));
        assertThat(remainingOptions, not(containsInAnyOrder(WEIGHTED, NOT_WEIGHTED, DIRECTED, NOT_DIRECTED, DAG)));
    }

}
