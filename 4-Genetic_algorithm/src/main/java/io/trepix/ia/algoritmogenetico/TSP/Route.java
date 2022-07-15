package io.trepix.ia.algoritmogenetico.TSP;

import io.trepix.ia.algoritmogenetico.Configuration;
import io.trepix.ia.algoritmogenetico.Gene;
import io.trepix.ia.algoritmogenetico.Individual;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static java.util.Collections.swap;

public class Route extends Individual {
    private final Configuration configuration;

    public Route(Configuration configuration, List<City> cities) {
        this.configuration = configuration;
        genome = new ArrayList<>(cities);
        Collections.shuffle(genome, configuration.random());
    }

    public Route(Route father) {
        this.configuration = father.configuration;
        genome = new ArrayList<>(father.genome);
        mutate();
    }

    public Route(Route father1, Route father2) {
        this.configuration = father1.configuration;

        int cutOffIndex = configuration.random().nextInt(father1.genome.size());
        genome = new ArrayList<>(father1.genome.subList(0, cutOffIndex));
        List<Gene> remainingCities = father2.genome.stream().filter(x -> !genome.contains(x)).toList();
        genome.addAll(remainingCities);
        mutate();
    }

    @Override
    public void mutate() {
        if (configuration.haveToMutate()) {
            int index1 = configuration.random().nextInt(genome.size());
            int index2 = configuration.random().nextInt(genome.size());
            swap(genome, index1, index2);
        }
    }

    @Override
    protected double evaluate() {
        double kmTotal = 0;
        for (int i = 1; i < genome.size(); i++) {
            City current = (City) genome.get(i);
            City before = (City) genome.get(i - 1);
            kmTotal += current.distanceTo(before);
        }
        City firstCity = (City) genome.get(0);
        City lastCity = (City) genome.get(genome.size() - 1);
        kmTotal += lastCity.distanceTo(firstCity);
        return kmTotal;
    }

}
