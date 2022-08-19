package io.trepix.ia.bancoPeces;

import io.trepix.ia.Size;
import io.trepix.ia.WindowBuilder;

import java.util.Random;

import static io.trepix.ia.WindowBuilder.SizeBuilder.width;

public class Application {

    private static final int FISH_NUMBER = 250;

    public static void main(String[] args) {
        Size size = width(600)
                .height(400)
                .build();

        Ocean ocean = new Ocean(size, new StartConfig(FISH_NUMBER, new Random()));

        WindowBuilder
                .withTitle("School of Fish")
                .withSize(size)
                .withComponent(new UserInterface(ocean))
                .show();
    }

}
