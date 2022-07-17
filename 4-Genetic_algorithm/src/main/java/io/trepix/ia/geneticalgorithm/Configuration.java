package io.trepix.ia.geneticalgorithm;

import io.trepix.ia.geneticalgorithm.Configuration.Rates.RatesBuilder;
import io.trepix.ia.geneticalgorithm.Configuration.StopCriteria.StopCriteriaBuilder;

import java.util.Random;
public class Configuration {

    private final Population population;
    private  final StopCriteria stopCriteria;
    private final Rates rates;
    private final Random random;

    public Configuration(Population population, StopCriteria stopCriteria, Rates rates, Random random) {
        this.population = population;
        this.stopCriteria = stopCriteria;
        this.rates = rates;
        this.random = random;
    }

    public static ConfigurationBuilder withRandomSeed() {
        ConfigurationBuilder builder = new ConfigurationBuilder();
        builder.seed = new Random().nextLong();
        return builder;
    }

    public int initialPopulation() {
        return this.population.individuals;
    }

    public int genesNumber() {
        return this.population.genesNumber;
    }

    public boolean haveToMutate() {
        return this.random().nextDouble() < this.rates.mutation;
    }

    public Random random() {
        return random;
    }

    public StopCriteria stopCriteria() {
        return this.stopCriteria;
    }

    boolean haveToApplyCrossover() {
        return random().nextDouble() < this.rates.crossover;
    }

    public boolean haveToDeleteGene() {
        return random().nextDouble() < this.rates.geneDeletion;
    }

    public boolean haveToAggregateGene() {
        return random().nextDouble() < this.rates.geneAggregation;
    }

    public static class ConfigurationBuilder {
        private long seed = 1L;
        private final int initialPopulation = 20;
        private final int genesNumber = 10;

        private RatesBuilder rates;
        private StopCriteriaBuilder stopCriteria;

        public ConfigurationBuilder stoppingAt(StopCriteriaBuilder stopCriteriaBuilder) {
            this.stopCriteria = stopCriteriaBuilder;
            return this;
        }

        public ConfigurationBuilder withRates(RatesBuilder rates) {
            this.rates = rates;
            return this;
        }

        public Configuration build() {
            Population population = new Population(this.initialPopulation, this.genesNumber);
            Random random = new Random(this.seed);
            return new Configuration(population, this.stopCriteria.build(), rates.build(), random);
        }
    }

    record Population(int individuals, int genesNumber) {}
    public record StopCriteria(int maxGenerations, double desiredFitness) {

        public boolean isReached(int ndGeneration, double fitness) {
            return ndGeneration >= this.maxGenerations || fitness <= this.desiredFitness;
        }
        public static class StopCriteriaBuilder {
            private int maxGenerations = 50;
            private double desiredFitness;

            public static StopCriteriaBuilder fitness(double desiredFitness) {
                StopCriteriaBuilder builder = new StopCriteriaBuilder();
                builder.desiredFitness = desiredFitness;
                return builder;
            }

            public StopCriteriaBuilder orGenerations(int maxGenerations) {
                this.maxGenerations = maxGenerations;
                return this;
            }

            public StopCriteria build() {
                return new StopCriteria(maxGenerations, desiredFitness);
            }
        }
    }
    public record Rates(double mutation, double geneAggregation, double geneDeletion, double crossover) {

        public static class RatesBuilder {
            private double mutation = 0.1;
            private double geneAggregation = 0.2;
            private double geneDeletion = 0.1;
            private double crossover = 0.6;

            public static RatesBuilder mutation(double mutation) {
                RatesBuilder builder = new RatesBuilder();
                builder.mutation = mutation;
                return builder;
            }

            public RatesBuilder geneAggregation(double geneAggregation) {
                this.geneAggregation = geneAggregation;
                return this;
            }

            public RatesBuilder geneDeletion(double geneDeletion) {
                this.geneDeletion = geneDeletion;
                return this;
            }

            public RatesBuilder crossover(double crossover) {
                this.crossover = crossover;
                return this;
            }

            public Rates build() {
                return new Rates(mutation, geneAggregation, geneDeletion, crossover);
            }
        }
    }
}
