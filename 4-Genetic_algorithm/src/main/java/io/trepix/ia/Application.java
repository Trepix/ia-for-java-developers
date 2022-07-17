package io.trepix.ia;

import io.trepix.ia.geneticalgorithm.Configuration;
import io.trepix.ia.geneticalgorithm.EvolutionaryProcess;
import io.trepix.ia.geneticalgorithm.Output;
import io.trepix.ia.geneticalgorithm.Individual;
import io.trepix.ia.geneticalgorithm.TSP.CitiesBuilder;
import io.trepix.ia.geneticalgorithm.TSP.City;
import io.trepix.ia.geneticalgorithm.TSP.RouteFactory;
import io.trepix.ia.geneticalgorithm.pathfinder.Labyrinth;
import io.trepix.ia.geneticalgorithm.pathfinder.PathFactory;

import java.util.List;

import static io.trepix.ia.geneticalgorithm.Configuration.Rates.RatesBuilder.mutation;
import static io.trepix.ia.geneticalgorithm.Configuration.StopCriteria.StopCriteriaBuilder.fitness;
import static io.trepix.ia.geneticalgorithm.Configuration.withRandomSeed;
import static io.trepix.ia.geneticalgorithm.TSP.CitiesBuilder.CityBuilder.city;

public class Application implements Output {

    public static void main(String[] args) {
        Application app = new Application();
        app.run();
    }

    public void run() {
        Configuration configuration = withRandomSeed()
                .withRates(mutation(0.3)
                        .crossover(0.0)
                        .geneAggregation(0.0)
                        .geneDeletion(0.0)
                )
                .stoppingAt(fitness(2579))
                .build();
        final var routeFactory = new RouteFactory(configuration, cities());
        EvolutionaryProcess process = new EvolutionaryProcess(configuration, routeFactory, this);
        process.run();

        configuration = withRandomSeed()
                .withRates(mutation(0.1)
                        .crossover(0.6)
                        .geneAggregation(0.8)
                        .geneDeletion(0.1)
                )
                .stoppingAt(fitness(0)
                        .orGenerations(300)
                )
                .build();

        final var pathFactory = new PathFactory(configuration, new Labyrinth(map()));
        EvolutionaryProcess process2 = new EvolutionaryProcess(configuration, pathFactory, this);
        process2.run();
    }

    private String map() {
        return """
                *--*--*--*--*--*--*
                E        |  |     |
                *--*--*  *  *  *--*
                |     |     |     |
                *  *  *  *  *  *  *
                |  |  |  |     |  |
                *--*  *  *--*--*  *
                |     |  |     |  |
                *  *--*--*  *  *  *
                |  |        |  |  |
                *  *  *  *--*  *  *
                |     |     |     S
                *--*--*--*--*--*--*""";
    }

    private String otherMap() {
        return """
            *--*--*--*--*
            E           |
            *  *  *--*--*
            |  |  |     |
            *  *--*  *  *
            |        |  |
            *  *--*--*  *
            |        |  S
            *--*--*--*--*""";
    }
    private List<City> cities() {
        return new CitiesBuilder()
                .with(city("Paris")
                        .distanceTo("Lyon", 462)
                        .distanceTo("Marseille", 772)
                        .distanceTo("Nantes", 379)
                        .distanceTo("Bordeaux", 546)
                        .distanceTo("Toulouse", 678)
                        .distanceTo("Lille", 215))
                .with(city("Lyon")
                        .distanceTo("Marseille", 326)
                        .distanceTo("Nantes", 598)
                        .distanceTo("Bordeaux", 842)
                        .distanceTo("Toulouse", 506)
                        .distanceTo("Lille", 664))
                .with(city("Marseille")
                        .distanceTo("Nantes", 909)
                        .distanceTo("Bordeaux", 555)
                        .distanceTo("Toulouse", 407)
                        .distanceTo("Lille", 1005))
                .with(city("Nantes")
                        .distanceTo("Bordeaux", 338)
                        .distanceTo("Toulouse", 540)
                        .distanceTo("Lille", 584))
                .with(city("Bordeaux")
                        .distanceTo("Toulouse", 250)
                        .distanceTo("Lille", 792))
                .with(city("Toulouse")
                        .distanceTo("Lille", 926))
                .build();
    }

    @Override
    public void showIndividual(Individual individual, int generation) {
        System.out.println(generation + " -> " + individual.toString());
    }
}
