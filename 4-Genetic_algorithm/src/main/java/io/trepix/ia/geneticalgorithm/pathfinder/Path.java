package io.trepix.ia.geneticalgorithm.pathfinder;

import io.trepix.ia.geneticalgorithm.Configuration;
import io.trepix.ia.geneticalgorithm.Gene;
import io.trepix.ia.geneticalgorithm.Individual;

import java.util.ArrayList;
import java.util.List;

public class Path extends Individual {

    private final Configuration configuration;
    private final Labyrinth labyrinth;
    public Path(Configuration configuration, Labyrinth labyrinth) {
        this.configuration = configuration;
        this.labyrinth = labyrinth;
        this.genome = new ArrayList<>();
        for (int i = 0; i < configuration.genesNumber(); i++) {
            genome.add(new DirectionUntilNextIntersection(configuration));
        }
    }

    public Path(Path father) {
        this.configuration = father.configuration;
        this.labyrinth = father.labyrinth;
        this.genome = new ArrayList<>();
        for (Gene gene : father.genome) {
            genome.add(((DirectionUntilNextIntersection) gene).copy());
        }
        mutate();
    }

    public Path(Path father1, Path father2) {
        this.configuration = father1.configuration;
        this.labyrinth = father1.labyrinth;
        this.genome = new ArrayList<>();

        int cutOffIndex = configuration.random().nextInt(father1.genome.size());
        genome = new ArrayList<>();
        for (Gene gene : father1.genome.subList(0, cutOffIndex)) {
            genome.add(((DirectionUntilNextIntersection) gene).copy());
        }
        if (cutOffIndex < father2.genome.size()) {
            for (Gene gene : father2.genome.subList(cutOffIndex, father2.genome.size())) {
                genome.add(((DirectionUntilNextIntersection) gene).copy());
            }
        }
        mutate();
    }

    @Override
    public void mutate() {
        if (configuration.haveToDeleteGene()) {
            int index = configuration.random().nextInt(genome.size());
            genome.remove(index);
        }

        if (configuration.haveToAggregateGene()) {
            genome.add(new DirectionUntilNextIntersection(configuration));
        }

        for (Gene gene : genome) {
            if (configuration.haveToMutate()) {
                gene.mutate();
            }
        }
    }

    @Override
    protected double evaluate() {
        List<DirectionUntilNextIntersection> directions = genome.stream()
                .map(x -> (DirectionUntilNextIntersection) x)
                .toList();
        return labyrinth.evaluate(directions);
    }
}
