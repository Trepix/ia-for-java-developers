package io.trepix.ia.gameoflife;

import javax.swing.*;
import java.awt.*;

public class WindowBuilder {

    private String title;

    private Size size;

    private Component panel;

    private WindowBuilder() {}

    public static WindowBuilder withTitle(String title) {
        var builder = new WindowBuilder();
        builder.title = title;
        return builder;
    }

    public WindowBuilder withSize(Size size) {
        this.size = size;
        return this;
    }

    public WindowBuilder withComponent(Component panel) {
        this.panel = panel;
        return this;
    }

    public void show() {
        JFrame window = new JFrame();
        window.setTitle(title);
        window.setSize(size.width(), size.height());
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

        public Size build() {
            return new Size(width, height);
        }
    }
}
