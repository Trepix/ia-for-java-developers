package io.trepix.ia.geneticalgorithm.pathfinder;

import java.util.List;

public class Labyrinth {
    private final Box start;
    private final Box end;

    public Labyrinth(String map) {
        Box[] boxes = new MapParser().parse(map);
        start = boxes[0];
        end = boxes[1];
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

    public static class MapParser {

        private Box[][] boxMap;

        public Box[] parse(String map) {
            int rows = map.split("\n").length;
            int columns = map.split("\n")[0].length();

            boxMap = new Box[rows][columns];
            Box start = null;
            Box end = null;

            String[] lines = map.split("\n");
            int linesCounter = 0;
            for (String line : lines) {
                if (linesCounter % 2 != 0) {
                    // Número impar, línea de pasillo
                    int index = line.indexOf("E");
                    if (index != -1) {
                        // Tenemos una entrada en ese pasillo
                        if (index == line.length() - 1) {
                            index--;
                        }
                        int x = index / 3;
                        int y = linesCounter / 2;
                        start = getOrCreate(x, y);
                    }
                    index = line.indexOf("S");
                    if (index != -1) {
                        // Salida en el pasillo
                        if (index == line.length() - 1) {
                            index--;
                        }
                        int x = index / 3;
                        int y = linesCounter / 2;
                        end = getOrCreate(x, y);
                    }
                    // recorremos el pasillo para crear los caminos horizontales
                    for (int x = 0; x < line.length() / 3; x++) {
                        String casillaStr = line.substring(x * 3, x * 3 + 3);
                        if (!casillaStr.contains("|") && !casillaStr.contains("E") && !casillaStr.contains("S")) {
                            // Paso abierto, se añade
                            int y = linesCounter / 2;
                            Box origin = getOrCreate(x - 1, y);
                            Box destination = getOrCreate(x, y);
                            origin.addAdjacent(destination);
                        }
                    }
                } else {
                    // Línea par : en los muros
                    String[] casillas = line.substring(1).split("\\*");
                    int x = 0;
                    for (String bloc : casillas) {
                        if (bloc.equals("  ")) {
                            int y = linesCounter / 2;
                            Box origin = getOrCreate(x, y - 1);
                            Box destination = getOrCreate(x, y);
                            origin.addAdjacent(destination);
                        }
                        x++;
                    }
                }
                linesCounter++;
            }
            return new Box[]{start, end};
        }

        private Box getOrCreate(int x, int y) {
            if (boxMap[x][y] == null) {
                boxMap[x][y] = new Box(x, y);
            }
            return boxMap[x][y];
        }
    }


}
