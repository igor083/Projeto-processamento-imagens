package operation.geometric;

import model.GrayImage;
import operation.interfaces.UnaryImageOperation;

public class ScaleOperation implements UnaryImageOperation {

    private final double sx;
    private final double sy;

    public ScaleOperation(double sx, double sy) {
        if (sx <= 0 || sy <= 0) {
            throw new IllegalArgumentException("Os fatores de escala devem ser maiores que zero.");
        }
        this.sx = sx;
        this.sy = sy;
    }

    @Override
    public GrayImage apply(GrayImage image) {
        int originalWidth = image.getWidth();
        int originalHeight = image.getHeight();

        int newWidth = Math.max(1, (int) Math.round(originalWidth * sx));
        int newHeight = Math.max(1, (int) Math.round(originalHeight * sy));

        GrayImage output = new GrayImage(newWidth, newHeight, image.getMaxGray());

        for (int yOut = 0; yOut < newHeight; yOut++) {
            for (int xOut = 0; xOut < newWidth; xOut++) {
                double srcX = xOut / sx;
                double srcY = yOut / sy;

                int value = bilinearSample(image, srcX, srcY);
                output.setPixel(xOut, yOut, value);
            }
        }

        return output;
    }

    private int bilinearSample(GrayImage image, double x, double y) {
        int width = image.getWidth();
        int height = image.getHeight();

        int x0 = (int) Math.floor(x);
        int y0 = (int) Math.floor(y);
        int x1 = Math.min(x0 + 1, width - 1);
        int y1 = Math.min(y0 + 1, height - 1);

        x0 = clamp(x0, 0, width - 1);
        y0 = clamp(y0, 0, height - 1);

        double dx = x - x0;
        double dy = y - y0;

        int p00 = image.getPixel(x0, y0);
        int p10 = image.getPixel(x1, y0);
        int p01 = image.getPixel(x0, y1);
        int p11 = image.getPixel(x1, y1);

        double top = p00 * (1 - dx) + p10 * dx;
        double bottom = p01 * (1 - dx) + p11 * dx;
        double value = top * (1 - dy) + bottom * dy;

        return (int) Math.round(value);
    }

    private int clamp(int value, int min, int max) {
        return Math.max(min, Math.min(max, value));
    }

    @Override
    public String getName() {
        return "Escala";
    }
}