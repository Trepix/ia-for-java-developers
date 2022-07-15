package io.trepix.ia.algoritmogenetico;

import io.trepix.ia.algoritmogenetico.Configuration.StopCriteria;

import java.util.ArrayList;
import java.util.List;

import static java.util.stream.IntStream.range;

public class EvolutionaryProcess {
    private final Configuration configuration;
    private final IndividualFactory individualFactory;
    private final Output output;
    private List<Individual> population;
    private int ndGeneration = 0;

    public EvolutionaryProcess(Configuration configuration, IndividualFactory individualFactory, Output output) {
        this.output = output;
        this.configuration = configuration;
        this.individualFactory = individualFactory;
        this.population = range(0, configuration.initialPopulation())
                .mapToObj(__ -> individualFactory.create())
                .toList();
    }

    public void run() {
        StopCriteria stopCriteria = configuration.stopCriteria();
        Individual bestIndividual;
        do {
            bestIndividual = bestIndividual();
            List<Individual> newPopulation = reproduceWithElitism(bestIndividual);
            survive(newPopulation);
            output.showIndividual(bestIndividual, ndGeneration++);
        } while (!stopCriteria.isReached(ndGeneration, bestIndividual.fitness()));
    }

    private Individual bestIndividual() {
        Individual bestIndividual = this.population.get(0);
        for (Individual individual : this.population) {
            if (individual.isBetterThan(bestIndividual)) {
                bestIndividual = individual;
            }
        }
        return bestIndividual;
    }

    private List<Individual> reproduceWithElitism(Individual bestIndividual) {
        List<Individual> newPopulation = new ArrayList<>();
        newPopulation.add(bestIndividual);
        for (int i = 0; i < configuration.initialPopulation() - 1; i++) {
            if (configuration.haveToApplyCrossover()) {
                Individual firstFather = selectByTournament();
                Individual secondFather = selectByTournament();
                newPopulation.add(individualFactory.reproduceFrom(firstFather, secondFather));
            } else {
                Individual father = selectByTournament();
                newPopulation.add(individualFactory.reproduceFrom(father));
            }
        }
        return newPopulation;
    }

    private void survive(List<Individual> newPopulation) {
        population = newPopulation;
    }

    private Individual selectByTournament() {
        Individual firstIndividual = randomIndividual();
        Individual secondIndividual = randomIndividual();
        if (firstIndividual.isBetterThan(secondIndividual)) {
            return firstIndividual;
        }
        return secondIndividual;
    }

    private Individual randomIndividual() {
        int index = configuration.random().nextInt(configuration.initialPopulation());
        return population.get(index);
    }
}
