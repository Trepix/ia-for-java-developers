package io.trepix.ia.wastesorting;

import io.trepix.ia.Size;
import io.trepix.ia.WindowBuilder;
import io.trepix.ia.wastesorting.StartConfig.GarbageConfig;

import java.util.Random;

import static io.trepix.ia.WindowBuilder.SizeBuilder.width;

public class Application {

    public static void main(String[] args) {
        Size size = width(600)
                .height(400)
                .build();

        GarbageConfig garbageConfig = new GarbageConfig(50, 3);
        StartConfig startConfig = new StartConfig(new Random(), size, garbageConfig, 30);

        Field field = new Field(startConfig);

        WindowBuilder
                .withTitle("Selective trash collection")
                .withSize(size)
                .withComponent(new UserInterface(field))
                .show();
    }
}
