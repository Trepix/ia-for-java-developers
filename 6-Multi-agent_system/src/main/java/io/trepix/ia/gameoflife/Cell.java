package io.trepix.ia.gameoflife;

import java.util.Set;

import static io.trepix.ia.gameoflife.Cell.State.ALIVE;
import static io.trepix.ia.gameoflife.Cell.State.DEAD;

public class Cell {

    private final GridPosition position;
    private final State state;

    private Cell(GridPosition position, State state) {
        this.position = position;
        this.state = state;
    }

    public static Cell alive(GridPosition position) {
        return new Cell(position, ALIVE);
    }

    public static Cell dead(GridPosition position) {
        return new Cell(position, State.DEAD);
    }

    public GridPosition position() {
        return this.position;
    }

    public Cell nextGeneration(Set<GridPosition> aliveCells) {
        int aliveNeighbours = aliveNeighbours(aliveCells);

        if (isUnderPopulated(aliveNeighbours)) return dead(this.position);
        if (isOverPopulated(aliveNeighbours)) return dead(this.position);
        if (isSurvivalSituation(aliveNeighbours)) return alive(this.position);
        if (canReproduce(aliveNeighbours)) return alive(this.position);
        return dead(this.position);
    }

    private boolean isUnderPopulated(long aliveNeighbours) {
        return aliveNeighbours < 2;
    }

    private boolean isOverPopulated(long aliveNeighbours) {
        return aliveNeighbours > 3;
    }

    private boolean isSurvivalSituation(int aliveNeighbours) {
        return state.equals(ALIVE) && (aliveNeighbours == 2 || aliveNeighbours == 3);
    }

    private boolean canReproduce(int aliveNeighbours) {
        return state.equals(DEAD) && aliveNeighbours == 3;
    }

    private int aliveNeighbours(Set<GridPosition> aliveCells) {
        return (int) position.neighbours().stream().filter(aliveCells::contains).count();
    }

    public boolean isAlive() {
        return state.equals(ALIVE);
    }

    enum State {
        DEAD, ALIVE
    }

}
