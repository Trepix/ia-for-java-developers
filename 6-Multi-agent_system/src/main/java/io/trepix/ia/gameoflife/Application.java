package io.trepix.ia.gameoflife;

import java.util.Random;

import static io.trepix.ia.gameoflife.UserInterface.applyRatio;
import static io.trepix.ia.gameoflife.WindowBuilder.SizeBuilder.width;
import static java.lang.Long.parseLong;

public class Application {

    public static final double ALIVE_CELLS_DENSITY = 0.1;

    public static void main(String[] args) {
        Size size = width(600)
                .height(400)
                .build();

        StartConfig startConfig = new StartConfig(random(args), ALIVE_CELLS_DENSITY);

        GameOfLife gameOfLife = new GameOfLife(applyRatio(size), startConfig);

        WindowBuilder
                .withTitle("Game of Life")
                .withSize(size)
                .withComponent(new UserInterface(gameOfLife))
                .show();
    }


    private static long seed(String[] args) {
        if (args.length < 1) return new Random().nextLong();
        return parseLong(args[0]);
    }

    private static Random random(String[] args) {
        return new Random(seed(args));
    }
}
