package io.trepix.ia;

import io.trepix.ia.geneticalgorithm.Configuration;
import io.trepix.ia.geneticalgorithm.EvolutionaryProcess;
import io.trepix.ia.geneticalgorithm.Output;
import io.trepix.ia.geneticalgorithm.Individual;
import io.trepix.ia.geneticalgorithm.TSP.CitiesBuilder;
import io.trepix.ia.geneticalgorithm.TSP.City;
import io.trepix.ia.geneticalgorithm.TSP.RouteFactory;
import io.trepix.ia.geneticalgorithm.laberinto.PathFactory;

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

        // Resolución del laberinto
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

        EvolutionaryProcess process2 = new EvolutionaryProcess(configuration, new PathFactory(configuration), this);
        process2.run();
    }

    private List<City> cities() {
        return new CitiesBuilder()
                .with(city("Paris")
                        .distanceTo("Lyon", 462)
                        .distanceTo("Marsella", 772)
                        .distanceTo("Nantes", 379)
                        .distanceTo("Burdeos", 546)
                        .distanceTo("Toulouse", 678)
                        .distanceTo("Lille", 215))
                .with(city("Lyon")
                        .distanceTo("Marsella", 326)
                        .distanceTo("Nantes", 598)
                        .distanceTo("Burdeos", 842)
                        .distanceTo("Toulouse", 506)
                        .distanceTo("Lille", 664))
                .with(city("Marsella")
                        .distanceTo("Nantes", 909)
                        .distanceTo("Burdeos", 555)
                        .distanceTo("Toulouse", 407)
                        .distanceTo("Lille", 1005))
                .with(city("Nantes")
                        .distanceTo("Burdeos", 338)
                        .distanceTo("Toulouse", 540)
                        .distanceTo("Lille", 584))
                .with(city("Burdeos")
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
