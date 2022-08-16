package io.trepix.ia.gameoflife;

import java.util.HashSet;
import java.util.Set;

import static java.util.stream.Collectors.toSet;

public class GameOfLife implements MultiAgentSystem {
    private final Size size;
    private Set<Position> alive = new HashSet<>();

    public GameOfLife(Size size, StartConfig startConfig) {
        var density = startConfig.density();
        var generator = startConfig.generator();
        this.size = size;

        for (int i = 0; i < size.width(); i++) {
            for (int j = 0; j < size.height(); j++) {
                if (generator.nextDouble() < density) {
                    alive.add(new Position(i, j));
                }
            }
        }
    }

    public Set<Position> aliveCells() {
        return alive;
    }

    public void changeState(Position position) {
        if (alive.contains(position)) alive.remove(position);
        else alive.add(position);
    }

    @Override
    public void evolve() {
        Set<Cell> cellsToEvaluate = cellsToEvaluate();

        alive = cellsToEvaluate.stream()
                .map(cell -> cell.nextGeneration(alive))
                .filter(Cell::isAlive)
                .map(Cell::position)
                .collect(toSet());
    }

    private Set<Cell> cellsToEvaluate() {
        var bounds = new Bounds(0, size.width(), 0, size.height());

        Set<Cell> deadNeighbours = alive.stream()
                .map(Position::neighbours)
                .flatMap(Set::stream)
                .distinct()
                .filter(position -> position.isInsideBounds(bounds))
                .filter(position -> !alive.contains(position))
                .map(Cell::dead)
                .collect(toSet());

        Set<Cell> result = alive.stream().map(Cell::alive).collect(toSet());
        result.addAll(deadNeighbours);
        return result;
    }
}
