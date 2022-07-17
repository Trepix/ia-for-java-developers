package io.trepix.ia.geneticalgorithm.pathfinder;

import java.util.List;
import java.util.Optional;

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

        public static final String START = "S";
        public static final String END = "E";
        private Box[][] boxMap;

        public Box[] parse(String map) {
            String[] lines = map.split("\n");
            int rows = lines.length;
            int columns = lines[0].length();

            boxMap = new Box[rows][columns];

            Box start = null;
            Box end = null;
            for (int linesCounter = 0; linesCounter < lines.length; linesCounter++) {
                String line = lines[linesCounter];
                final int y = linesCounter / 2;

                if (isWallLine(linesCounter)) {
                    String[] boxes = line.substring(1).split("\\*");
                    for (int x = 0; x < boxes.length; x++) {
                        if ("  ".equals(boxes[x])) {
                            linkToTop(x, y);
                        }
                    }
                } else {
                    start = getIndexOf(line, START)
                            .map(x -> getOrCreate(x, y))
                            .orElse(start);

                    end = getIndexOf(line, END)
                            .map(x -> getOrCreate(x, y))
                            .orElse(end);

                    for (int x = 0; x < line.length() / 3; x++) {
                        String box = line.substring(x * 3, (x + 1) * 3);
                        if (!(isSurroundedByWalls(box) || isEnd(box) || isStart(box))) {
                            linkToLeft(x, y);
                        }
                    }
                }
            }
            return new Box[]{start, end};
        }

        private boolean isStart(String box) {
            return box.contains("S");
        }

        private boolean isEnd(String box) {
            return box.contains("E");
        }

        private boolean isSurroundedByWalls(String box) {
            return box.contains("|");
        }

        private Optional<Integer> getIndexOf(String line, String character) {
            int index = line.indexOf(character);
            if (index == -1) return Optional.empty();
            if (index == line.length() - 1) {
                index--;
            }
            return Optional.of(index / 3);
        }

        private void linkToLeft(int x, int y) {
            Box origin = getOrCreate(x - 1, y);
            Box destination = getOrCreate(x, y);
            origin.addAdjacent(destination);
        }

        private void linkToTop(int x, int y) {
            Box origin = getOrCreate(x, y - 1);
            Box destination = getOrCreate(x, y);
            origin.addAdjacent(destination);
        }

        private boolean isWallLine(int linesCounter) {
            return linesCounter % 2 == 0;
        }

        private Box getOrCreate(int x, int y) {
            if (boxMap[x][y] == null) {
                boxMap[x][y] = new Box(x, y);
            }
            return boxMap[x][y];
        }
    }


}
