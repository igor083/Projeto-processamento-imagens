package operation.geometric;

import model.GrayImage;
import operation.interfaces.UnaryImageOperation;

public class ShearOperation implements UnaryImageOperation {

    private final int iterations;

    public ShearOperation(int iterations) {
        if (iterations < 0) {
            throw new IllegalArgumentException("Número de iterações não pode ser negativo.");
        }
        this.iterations = iterations;
    }

    @Override
    public GrayImage apply(GrayImage image) {
        int width = image.getWidth();
        int height = image.getHeight();

        if (width != height) {
            throw new IllegalArgumentException("A transformação do gato de Arnold requer imagem quadrada.");
        }

        int N = width;
        GrayImage current = image.copy();

        for (int iter = 0; iter < iterations; iter++) {
            GrayImage next = new GrayImage(N, N, current.getMaxGray());

            for (int y = 0; y < N; y++) {
                for (int x = 0; x < N; x++) {
                    int newX = (x + y) % N;
                    int newY = (x + 2 * y) % N;
                    next.setPixel(newX, newY, current.getPixel(x, y));
                }
            }
            current = next;
        }

        return current;
    }

    @Override
    public String getName() {
        return "Cisalhamento (Gato de Arnold)";
    }
}