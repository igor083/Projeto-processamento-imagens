package operation.impl.unary.transformation;

import model.GrayImage;
import operation.UnaryImageOperation;

public class ReflectionOperation implements UnaryImageOperation {

    public enum Type {
        X, // horizontal (espelha em cima/baixo)
        Y  // vertical (espelha esquerda/direita)
    }

    private final Type type;

    public ReflectionOperation(Type type) {
        this.type = type;
    }

    @Override
    public GrayImage apply(GrayImage image) {
        int width = image.getWidth();
        int height = image.getHeight();

        GrayImage output = new GrayImage(width, height, image.getMaxGray());

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {

                int srcX = x;
                int srcY = y;

                if (type == Type.Y) {
                    // espelho vertical (esquerda ↔ direita)
                    srcX = width - 1 - x;
                }

                if (type == Type.X) {
                    // espelho horizontal (cima ↔ baixo)
                    srcY = height - 1 - y;
                }

                output.setPixel(x, y, image.getPixel(srcX, srcY));
            }
        }

        return output;
    }

    @Override
    public String getName() {
        return "Reflexão";
    }
}