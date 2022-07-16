package io.trepix.ia.geneticalgorithm.pathfinder;

import io.trepix.ia.geneticalgorithm.Gene;

import java.util.LinkedList;
import java.util.List;

public class Labyrinth {

    private final List<Movement> possibleMovements;
    private Box start;
    private Box end;
    ;

    record Movement(Box origin, Box destination) {}
    record Displacement(int vertical, int horizontal) {}
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
    private boolean canMoveBetween(Box firstBox, Box secondBox) {
        for (Movement movement : possibleMovements) {
            if (movement.origin.equals(firstBox) && movement.destination.equals(secondBox) || movement.origin.equals(secondBox) && movement.destination.equals(firstBox)) {
                return true;
            }
        }
        return false;
    }
    private boolean isIntersection(Box box) {
        int numberPossibleMovements = 0;
        for (Movement movement : possibleMovements) {
            if (movement.origin.equals(box) || movement.destination.equals(box)) {
                numberPossibleMovements++;
            }
        }
        return numberPossibleMovements > 2;
    }
    private void move(Box origin, Displacement displacement) {
        boolean finMovimiento = false;
        while(canMoveBetween(origin, new Box(origin.getI() + displacement.vertical, origin.getJ() + displacement.horizontal)) && !finMovimiento) {
            origin.setI(origin.getI() + displacement.vertical);
            origin.setJ(origin.getJ() + displacement.horizontal);
            finMovimiento = isIntersection(origin) || origin.equals(end);
        }
    }
    double evaluate(List<DirectionUntilNextIntersection> directions) {
        Box actualBox = new Box(start.getI(), start.getJ());
        for(var directionUntilNextIntersection : directions) {
            Direction direction = directionUntilNextIntersection.dirección;
            move(actualBox, direction.displacement());
            if (actualBox.equals(end)) {
                break;
            }
        }
        return end.manhattanDistanceTo(actualBox);
    }
}
