package io.trepix.ia.gameoflife;

import io.trepix.ia.Size;
import org.junit.Assert;
import org.junit.Test;

import java.util.Collection;
import java.util.Random;
import java.util.Set;

public class GameOfLifeTest {

    static long seed = 1922;
    static StartConfig config = new StartConfig(new Random(seed), 0.2);
    static GameOfLife gameOfLife = new GameOfLife(new Size(10, 10), config);

    static int iterations = 0;

    @Test
    public void run_test() {
        assertIteration(Set.of(new GridPosition(0,4), new GridPosition(1,4), new GridPosition(5,0), new GridPosition(5,1), new GridPosition(7,0), new GridPosition(7,1), new GridPosition(8,0), new GridPosition(8,1), new GridPosition(8,2)));
        assertIteration(Set.of(new GridPosition(7, 0), new GridPosition(7, 2), new GridPosition(8, 0), new GridPosition(8, 2), new GridPosition(9, 1)));
        assertIteration(Set.of(new GridPosition(8,0), new GridPosition(8,2), new GridPosition(9,1)));
        assertIteration(Set.of(new GridPosition(8,1), new GridPosition(9,1)));

        System.out.println("Everything OK, refactor didn't broke anything, ''anything'' exercised by tests :)");
    }

    private static void assertIteration(Set<GridPosition> expectedAliveCells) {
        gameOfLife.evolve();
        var aliveCells = gameOfLife.aliveCells();
        assert expectedAliveCells.equals(aliveCells);
        assertContains("Alive cells are different in iteration " + iterations++, expectedAliveCells, aliveCells);
    }

    private static <T> void assertContains(String message, Collection<T> expected, Collection<T> actual) {
        Assert.assertTrue(message, expected.containsAll(actual));
    }
}
