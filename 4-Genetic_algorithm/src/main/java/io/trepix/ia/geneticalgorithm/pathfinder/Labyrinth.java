package io.trepix.ia.geneticalgorithm.pathfinder;

import io.trepix.ia.geneticalgorithm.Gene;

import java.util.LinkedList;
import java.util.List;

public class Labyrinth {

    private final List<Movement> possibleMovements;
    private Box start;
    private Box end;
    public enum Direction {Up, Down, Left, Right};

    record Movement(Box origin, Box destination) {}
    public Labyrinth(String map) {
        possibleMovements = new LinkedList<>();

        // Nos separramos y después se trata cada línea
        String[] lineas = map.split("\n");
        int numLineas = 0;
        for (String linea : lineas) {
            if (numLineas % 2 != 0) {
                // Número impar, línea de pasillo
                int index = linea.indexOf("E");
                if (index != -1) {
                    // Tenemos una entrada en ese pasillo
                    if (index == linea.length() - 1) {
                        index --;
                    }
                    start = new Box(numLineas/2, index /3);
                }
                index = linea.indexOf("S");
                if (index != -1) {
                    // Salida en el pasillo
                    if (index == linea.length() - 1) {
                        index--;
                    }
                    end = new Box(numLineas/2, index/3);
                }
                // recorremos el pasillo para crear los caminos horizontales
                for (int columna = 0; columna < linea.length() / 3; columna++) {
                    String casillaStr = linea.substring(columna*3, columna*3 + 3);
                    if (!casillaStr.contains("|") && !casillaStr.contains("E") && !casillaStr.contains("S")) {
                        // Paso abierto, se añade
                        possibleMovements.add(new Movement(new Box(numLineas/2, columna-1), new Box(numLineas/2, columna)));
                    }
                }
            }
            else {
                // Línea par : en los muros
                String [] casillas = linea.substring(1).split("\\*");
                int columna = 0;
                for (String bloc : casillas) {
                    if (bloc.equals("  ")) {
                        // Paso abierto, se añade
                        possibleMovements.add(new Movement(new Box(numLineas/2 - 1, columna), new Box(numLineas/2, columna)));
                    }
                    columna++;
                }
            }
            numLineas++;
        }
    }

    // Indica si un movimiento entre dos casillas es posible
    private boolean canMoveBetween(Box firstBox, Box secondBox) {
        for (Movement movement : possibleMovements) {
            if (movement.origin.equals(firstBox) && movement.destination.equals(secondBox) || movement.origin.equals(secondBox) && movement.destination.equals(firstBox)) {
                return true;
            }
        }
        return false;
    }
    private boolean isIntersection(Box box) {
        int numCaminos = 0;
        for (Movement movement : possibleMovements) {
            if (movement.origin.equals(box) || movement.destination.equals(box)) {
                numCaminos++;
            }
        }
        return numCaminos > 2;
    }
    
    // Mira si el movimiento es posible
    private void move(Box inicio, int deplI, int deplJ) {
        boolean finMovimiento = false;
        while(canMoveBetween(inicio, new Box(inicio.getI() + deplI, inicio.getJ() + deplJ)) && !finMovimiento) {
            inicio.setI(inicio.getI() + deplI);
            inicio.setJ(inicio.getJ() + deplJ);
            finMovimiento = isIntersection(inicio) || inicio.equals(end);
        }
    }
    
    // Mueve un individuo en el laberinto para evaluarlo
    double Evaluar(List<Gene> genoma) {
        Box boxActual = new Box(start.getI(), start.getJ());
        for(Gene g : genoma) {
            switch (((DirectionUntilNextIntersection)g).dirección) {
                case Down:
                    move(boxActual, 1, 0);
                    break;
                case Up:
                    move(boxActual, -1, 0);
                    break;
                case Right:
                    move(boxActual, 0, 1);
                    break;
                case Left:
                    move(boxActual, 0, -1);
                    break;
            }
            if (boxActual.equals(end)) {
                break;
            }
        }
        // Cálculo del fitness : distancia de Manhattan
        int distancia = Math.abs(end.getI() - boxActual.getI()) + Math.abs(end.getJ() - boxActual.getJ());
        return distancia;
    }
}
