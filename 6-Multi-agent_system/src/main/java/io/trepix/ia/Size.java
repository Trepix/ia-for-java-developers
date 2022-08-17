package io.trepix.ia;

public record Size(int width, int height) {
    public Size resize(int ratio) {
        return new Size(width / ratio, height / ratio);
    }
}
