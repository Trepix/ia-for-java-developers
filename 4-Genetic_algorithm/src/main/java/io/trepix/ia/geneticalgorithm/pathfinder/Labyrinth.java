package io.trepix.ia.geneticalgorithm.pathfinder;

import java.util.List;

public class Labyrinth {
    private Box start;
    private Box end;

    public Labyrinth(String map) {
        Box[][] boxMap = new Box[map.split("\n").length][map.split("\n")[0].length()];

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
                        index--;
                    }
                    if (boxMap[numLineas / 2][index / 3] == null) {
                        boxMap[numLineas / 2][index / 3] = new Box(index / 3, numLineas / 2);
                    }
                    start = boxMap[numLineas / 2][index / 3];
                }
                index = linea.indexOf("S");
                if (index != -1) {
                    // Salida en el pasillo
                    if (index == linea.length() - 1) {
                        index--;
                    }
                    if (boxMap[numLineas / 2][index / 3] == null) {
                        boxMap[numLineas / 2][index / 3] = new Box(index / 3, numLineas / 2);
                    }
                    end = boxMap[numLineas / 2][index / 3];
                }
                // recorremos el pasillo para crear los caminos horizontales
                for (int columna = 0; columna < linea.length() / 3; columna++) {
                    String casillaStr = linea.substring(columna * 3, columna * 3 + 3);
                    if (!casillaStr.contains("|") && !casillaStr.contains("E") && !casillaStr.contains("S")) {
                        // Paso abierto, se añade
                        if (boxMap[numLineas / 2][columna - 1] == null) {
                            boxMap[numLineas / 2][columna - 1] = new Box(columna - 1, numLineas / 2);
                        }
                        if (boxMap[numLineas / 2][columna] == null) {
                            boxMap[numLineas / 2][columna] = new Box(columna, numLineas / 2);
                        }
                        Box origin = boxMap[numLineas / 2][columna - 1];
                        Box destination = boxMap[numLineas / 2][columna];
                        origin.addAdjacent(destination);
                    }
                }
            } else {
                // Línea par : en los muros
                String[] casillas = linea.substring(1).split("\\*");
                int columna = 0;
                for (String bloc : casillas) {
                    if (bloc.equals("  ")) {
                        if (boxMap[numLineas / 2 - 1][columna] == null) {
                            boxMap[numLineas / 2 - 1][columna] = new Box(columna, numLineas / 2 - 1);
                        }
                        if (boxMap[numLineas / 2][columna] == null) {
                            boxMap[numLineas / 2][columna] = new Box(columna, numLineas / 2);
                        }
                        Box origin = boxMap[numLineas / 2 - 1][columna];
                        Box destination = boxMap[numLineas / 2][columna];
                        origin.addAdjacent(destination);
                    }
                    columna++;
                }
            }
            numLineas++;
        }
    }

    private Box moveStraight(Box origin, Displacement displacement) {
        boolean stopMoving;
        do {
            Box lastBox = origin;
            origin = origin.move(displacement);
            stopMoving = origin.isIntersection() || origin.equals(end) || lastBox.equals(origin);
        } while (!stopMoving);
        return origin;
    }

    double evaluate(List<DirectionUntilNextIntersection> directions) {
        Box box = start;
        for (var directionUntilNextIntersection : directions) {
            Direction direction = directionUntilNextIntersection.direction;
            box = moveStraight(box, direction.displacement());
            if (box.equals(end)) {
                break;
            }
        }
        return end.manhattanDistanceTo(box);
    }

    record Displacement(int vertical, int horizontal) {
    }
}
