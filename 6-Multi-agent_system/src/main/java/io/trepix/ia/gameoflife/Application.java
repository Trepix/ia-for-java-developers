package io.trepix.ia.gameoflife;

import static io.trepix.ia.gameoflife.UserInterface.applyRatio;
import static io.trepix.ia.gameoflife.WindowBuilder.SizeBuilder.width;
public class Application {

    public static final double ALIVE_CELLS_DENSITY = 0.1;

    public static void main(String[] args) {
        Size size = width(600)
                .height(400)
                .build();

        GameOfLife gameOfLife = new GameOfLife(applyRatio(size), ALIVE_CELLS_DENSITY);

        WindowBuilder
                .withTitle("Game of Life")
                .withSize(size)
                .withComponent(new UserInterface(gameOfLife))
                .show();
    }
}
