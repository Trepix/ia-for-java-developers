package io.trepix.ia.juegoDeLaVida;

import javax.swing.*;
import java.awt.*;

public class WindowBuilder {

    private String title;

    private SizeBuilder size;

    private Component panel;

    private WindowBuilder() {}

    public static WindowBuilder withTitle(String title) {
        var builder = new WindowBuilder();
        builder.title = title;
        return builder;
    }

    public WindowBuilder withSize(SizeBuilder sizeBuilder) {
        this.size = sizeBuilder;
        return this;
    }

    public WindowBuilder withComponent(Component panel) {
        this.panel = panel;
        return this;
    }

    public void show() {
        JFrame window = new JFrame();
        window.setTitle(title);
        window.setSize(size.width, size.height);
        window.setLocationRelativeTo(null);
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setResizable(false);

        window.setContentPane((Container) panel);
        window.setVisible(true);

        panel.start();
    }

    public static class SizeBuilder {
        private int width;
        private int height;

        private SizeBuilder() {}

        public static SizeBuilder width(int width) {
            var builder = new SizeBuilder();
            builder.width = width;
            return builder;
        }

        public SizeBuilder height(int height) {
            this.height = height;
            return this;
        }
    }
}
