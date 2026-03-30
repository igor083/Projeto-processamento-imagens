package operation.impl.morphological;

import model.GrayImage;
import operation.UnaryImageOperation;

public class GrayDilationOperation implements UnaryImageOperation {

    private final int[][] kernel;
    private final int kernelWidth;
    private final int kernelHeight;

    public GrayDilationOperation() {
        this.kernel = new int[][]{
                {0, 0, 0},
                {0, 0, 0},
                {0, 0, 0}
        };
        this.kernelWidth = 3;
        this.kernelHeight = 3;
    }

    @Override
    public GrayImage apply(GrayImage image) {
        int width = image.getWidth();
        int height = image.getHeight();
        GrayImage result = new GrayImage(width, height, image.getMaxGray());

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                int max = 0;
                for (int ky = -kernelHeight / 2; ky <= kernelHeight / 2; ky++) {
                    for (int kx = -kernelWidth / 2; kx <= kernelWidth / 2; kx++) {
                        int px = Math.max(0, Math.min(width - 1, x + kx));
                        int py = Math.max(0, Math.min(height - 1, y + ky));
                        int pixel = image.getPixel(px, py);
                        int value = pixel + kernel[ky + kernelHeight / 2][kx + kernelWidth / 2];
                        if (value > max) {
                            max = value;
                        }
                    }
                }
                result.setPixel(x, y, Math.min(max, image.getMaxGray()));
            }
        }
        return result;
    }

    @Override
    public String getName() {
        return "Dilatação Nível Cinza";
    }
}