package io.trepix.ia.recogidaSelectiva;

import io.trepix.ia.Size;
import io.trepix.ia.WindowBuilder;
import io.trepix.ia.recogidaSelectiva.StartConfig.TrashConfig;

import java.util.Random;

import static io.trepix.ia.WindowBuilder.SizeBuilder.width;

public class Application {

    public static void main(String[] args) {
        Size size = width(600)
                .height(400)
                .build();

        TrashConfig trashConfig = new TrashConfig(50, 3);
        StartConfig startConfig = new StartConfig(new Random(), size, trashConfig, 30);

        Field field = new Field(startConfig);

        WindowBuilder
                .withTitle("Selective trash collection")
                .withSize(size)
                .withComponent(new UserInterface(field))
                .show();
    }
}
