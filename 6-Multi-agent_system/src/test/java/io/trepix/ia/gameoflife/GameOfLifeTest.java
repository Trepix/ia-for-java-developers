package io.trepix.ia.gameoflife;

import java.util.Random;
import java.util.Set;

//Should be executed with option -ea in VM Arguments to enable assertions
public class GameOfLifeTest {

    static long seed = 1922;
    static StartConfig config = new StartConfig(new Random(seed), 0.2);
    static GameOfLife gameOfLife = new GameOfLife(new Size(10, 10), config);

    static int iterations = 0;

    public static void main(String[] args) {
        assertIteration(Set.of(new Cell(0,4), new Cell(1,4), new Cell(5,0), new Cell(5,1), new Cell(7,0), new Cell(7,1), new Cell(8,0), new Cell(8,1), new Cell(8,2)));
        assertIteration(Set.of(new Cell(7, 0), new Cell(7, 2), new Cell(8, 0), new Cell(8, 2), new Cell(9, 1)));
        assertIteration(Set.of(new Cell(8,0), new Cell(8,2), new Cell(9,1)));
        assertIteration(Set.of(new Cell(8,1), new Cell(9,1)));

        System.out.println("Everything OK, refactor didn't broke anything, ''anything'' exercised by tests :)");
    }

    private static void assertIteration(Set<Cell> expectedAliveCells) {
        gameOfLife.update();
        var aliveCells = gameOfLife.aliveCells();
        assert expectedAliveCells.equals(aliveCells) : "Alive cells are different in iteration " + iterations++;
    }
}
