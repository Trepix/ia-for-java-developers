package io.trepix.ia.gameoflife;

import static io.trepix.ia.gameoflife.WindowBuilder.SizeBuilder.width;
public class Application {
    public static void main(String[] args) {
        WindowBuilder
                .withTitle("Game of Life")
                .withSize(width(600).height(400))
                .withComponent(new UserInterface())
                .show();
    }
}
