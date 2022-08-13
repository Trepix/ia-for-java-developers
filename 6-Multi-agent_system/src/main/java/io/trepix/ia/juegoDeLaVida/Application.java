package io.trepix.ia.juegoDeLaVida;

import static io.trepix.ia.juegoDeLaVida.WindowBuilder.SizeBuilder.width;
public class Application {
    public static void main(String[] args) {
        JuegoDeLaVidaJPanel panel = new JuegoDeLaVidaJPanel();

        WindowBuilder
                .withTitle("Game of Life")
                .withSize(width(600).height(400))
                .withComponent(panel)
                .show();
    }
}
