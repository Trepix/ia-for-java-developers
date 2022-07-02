package io.trepix.ia.algoritmogenetico;

import java.util.Random;

// Conjunto de  argumentos del sistema
public class Configuration {
    // Argumentos de la población y los individuos
    public int numIndividuos = 20;
    public int numGenes = 10;

    // Criterios de parada
    public int numMaxGeneraciones = 50;
    public double minFitness = 0.0;

    // Tasa de los operadores
    public double tasaMutacion = 0.1;
    public double tasaAgregaGen = 0.2;
    public double tasaEliminaGen = 0.1;
    public double tasaCrossover = 0.6;
    public Random random = new Random();
}
