package model;
public class GrayImage {
    private final int width;
    private final int height;
    private final int maxGray;
    private final int[][] pixels;

    public GrayImage(int width, int height, int maxGray) {
        if (width <= 0 || height <= 0) {
            throw new IllegalArgumentException("Dimensões inválidas.");
        }
        if (maxGray <= 0) {
            throw new IllegalArgumentException("maxGray inválido.");
        }

        this.width = width;
        this.height = height;
        this.maxGray = maxGray;
        this.pixels = new int[height][width];
    }

    public GrayImage(int width, int height, int maxGray, int[][] pixels) {
        this(width, height, maxGray);

        if (pixels.length != height || pixels[0].length != width) {
            throw new IllegalArgumentException("Matriz de pixels incompatível com as dimensões.");
        }

        for (int y = 0; y < height; y++) {
            if (pixels[y].length != width) {
                throw new IllegalArgumentException("Linha de pixels com tamanho inválido.");
            }
            for (int x = 0; x < width; x++) {
                this.pixels[y][x] = clamp(pixels[y][x], 0, maxGray);
            }
        }
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public int getMaxGray() {
        return maxGray;
    }

    public int getPixel(int x, int y) {
        validateCoordinates(x, y);
        return pixels[y][x];
    }

    public void setPixel(int x, int y, int value) {
        validateCoordinates(x, y);
        pixels[y][x] = clamp(value, 0, maxGray);
    }

    public int[][] copyPixels() {
        int[][] copy = new int[height][width];
        for (int y = 0; y < height; y++) {
            System.arraycopy(pixels[y], 0, copy[y], 0, width);
        }
        return copy;
    }

    public GrayImage copy() {
        return new GrayImage(width, height, maxGray, copyPixels());
    }

    private void validateCoordinates(int x, int y) {
        if (x < 0 || x >= width || y < 0 || y >= height) {
            throw new IllegalArgumentException("Coordenadas fora da imagem.");
        }
    }

    private int clamp(int value, int min, int max) {
        return Math.max(min, Math.min(max, value));
    }
}