package io.trepix.ia.algoritmogenetico;

import java.util.Random;

// Conjunto de  argumentos del sistema
public class Argumentos {
    // Argumentos de la población y los individuos
    public static int numIndividuos = 20;
    public static int numGenes = 10;
    
    // Criterios de parada
    public static int numMaxGeneraciones = 50;
    public static double minFitness = 0.0;
    
    // Tasa de los operadores
    public static double tasaMutacion = 0.1;
    public static double tasaAgregaGen = 0.2;
    public static double tasaEliminaGen = 0.1;
    public static double tasaCrossover = 0.6;
    
    // Generador aleatoria
    public static Random random = new Random();
}
