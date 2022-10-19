package io.trepix.ia.recogidaSelectiva;

import io.trepix.ia.Size;
import io.trepix.ia.WindowBuilder;

import java.util.Random;

import static io.trepix.ia.WindowBuilder.SizeBuilder.width;

public class Application {

    public static void main(String[] args) {
        Size size = width(600)
                .height(400)
                .build();

        Field field = new Field(new Random());
        field.Initializar(50, 30, size.width(), size.height(), 3);

        WindowBuilder
                .withTitle("Selective trash collection")
                .withSize(size)
                .withComponent(new UserInterface(field))
                .show();
    }
}
