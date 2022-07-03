package io.trepix.ia.aplicaciones;

import io.trepix.ia.algoritmogenetico.Configuration;
import io.trepix.ia.algoritmogenetico.IHM;
import io.trepix.ia.algoritmogenetico.Individual;
import io.trepix.ia.algoritmogenetico.PVC.RouteFactory;
import io.trepix.ia.algoritmogenetico.ProcesoEvolutivo;
import io.trepix.ia.algoritmogenetico.laberinto.PathFactory;

import static io.trepix.ia.algoritmogenetico.Configuration.Rates.RatesBuilder.mutation;
import static io.trepix.ia.algoritmogenetico.Configuration.StopCriteria.StopCriteriaBuilder.fitness;
import static io.trepix.ia.algoritmogenetico.Configuration.withRandomSeed;

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
        ProcesoEvolutivo process = new ProcesoEvolutivo(this, configuration, new RouteFactory(configuration));
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

        ProcesoEvolutivo process2 = new ProcesoEvolutivo(this, configuration, new PathFactory(configuration));
        process2.run();
    }

    @Override
    public void showBestIndividual(Individual ind, int generation) {
        System.out.println(generation + " -> " + ind.toString());
    }
}
