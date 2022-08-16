package io.trepix.ia.gameoflife;

import java.util.LinkedHashSet;
import java.util.Set;

public class GameOfLife implements MultiAgentSystem {
    private final Size size;
    private boolean[][] cells;

    public GameOfLife(Size size, StartConfig startConfig) {
        var density = startConfig.density();
        var generator = startConfig.generator();
        this.size = size;

        cells = new boolean[size.width()][size.height()];
        for (int i = 0; i < size.width(); i++) {
            for (int j = 0; j < size.height(); j++) {
                if (generator.nextDouble() < density) {
                    cells[i][j] = true;
                }
            }
        }
    }

    public Set<Position> aliveCells() {
        Set<Position> aliveCells = new LinkedHashSet<>();
        for (int i = 0; i < size.width(); i++) {
            for (int j = 0; j < size.height(); j++) {
                if (cells[i][j]) aliveCells.add(new Position(i, j));
            }
        }
        return aliveCells;
    }

    public void changeState(Position position) {
        cells[position.x()][position.y()] = !cells[position.x()][position.y()];
    }

    private int aliveNeighbours(int row, int column) {
        int i_min = Math.max(0, row - 1);
        int i_max = Math.min(size.width() - 1, row + 1);
        int j_min = Math.max(0, column - 1);
        int j_max = Math.min(size.height() - 1, column + 1);
        int nb = 0;
        for (int i = i_min; i <= i_max; i++) {
            for (int j = j_min; j <= j_max; j++) {
                if (cells[i][j] && !(i == row && j == column)) {
                    nb++;
                }
            }
        }
        return nb;
    }

    @Override
    public void evolve() {
        boolean[][] newCells = new boolean[size.width()][size.height()];
        for (int i = 0; i < size.width(); i++) {
            for (int j = 0; j < size.height(); j++) {
                int nb = aliveNeighbours(i, j);
                if (nb == 3 || (nb == 2 && cells[i][j])) {
                    newCells[i][j] = true;
                }
            }
        }
        cells = newCells;
    }
}
