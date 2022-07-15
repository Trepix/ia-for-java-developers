package io.trepix.ia.aplicaciones;

import io.trepix.ia.algoritmogenetico.Configuration;
import io.trepix.ia.algoritmogenetico.EvolutionaryProcess;
import io.trepix.ia.algoritmogenetico.IHM;
import io.trepix.ia.algoritmogenetico.Individual;
import io.trepix.ia.algoritmogenetico.PVC.CitiesBuilder;
import io.trepix.ia.algoritmogenetico.PVC.City;
import io.trepix.ia.algoritmogenetico.PVC.RouteFactory;
import io.trepix.ia.algoritmogenetico.laberinto.PathFactory;

import java.util.List;

import static io.trepix.ia.algoritmogenetico.Configuration.Rates.RatesBuilder.mutation;
import static io.trepix.ia.algoritmogenetico.Configuration.StopCriteria.StopCriteriaBuilder.fitness;
import static io.trepix.ia.algoritmogenetico.Configuration.withRandomSeed;
import static io.trepix.ia.algoritmogenetico.PVC.CitiesBuilder.CityBuilder.city;

public class Application implements IHM {
    public static void main(String[] args) {
        Application app = new Application();
        app.run();
    }

    public void run() {
        // Resolución del viajante de comercio
        Configuration configuration = withRandomSeed()
                .withRates(mutation(0.3)
                        .crossover(0.0)
                        .geneAggregation(0.0)
                        .geneDeletion(0.0)
                )
                .stoppingAt(fitness(2579))
                .build();
        EvolutionaryProcess process = new EvolutionaryProcess(this, configuration, new RouteFactory(configuration, cities()));
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

        EvolutionaryProcess process2 = new EvolutionaryProcess(this, configuration, new PathFactory(configuration));
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
    public void showBestIndividual(Individual ind, int generation) {
        System.out.println(generation + " -> " + ind.toString());
    }
}
