package operation.impl.morphological;

import model.GrayImage;
import operation.UnaryImageOperation;

public class BinaryErosionOperation implements UnaryImageOperation {

    private final int[][] kernel;
    private final int kernelWidth;
    private final int kernelHeight;
    private final int threshold;

    public BinaryErosionOperation() {
        this(128);
    }

    public BinaryErosionOperation(int threshold) {
        this.kernel = new int[][]{
                {1, 1, 1},
                {1, 1, 1},
                {1, 1, 1}
        };
        this.kernelWidth = 3;
        this.kernelHeight = 3;
        this.threshold = threshold;
    }

    private boolean isWhite(int pixel) {
        return pixel > threshold;
    }

    @Override
    public GrayImage apply(GrayImage image) {
        int width = image.getWidth();
        int height = image.getHeight();
        GrayImage result = new GrayImage(width, height, image.getMaxGray());

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                boolean allWhite = true;
                for (int ky = -kernelHeight / 2; ky <= kernelHeight / 2; ky++) {
                    for (int kx = -kernelWidth / 2; kx <= kernelWidth / 2; kx++) {
                        int px = Math.max(0, Math.min(width - 1, x + kx));
                        int py = Math.max(0, Math.min(height - 1, y + ky));
                        int pixel = image.getPixel(px, py);
                        if (kernel[ky + kernelHeight / 2][kx + kernelWidth / 2] == 1 && !isWhite(pixel)) {
                            allWhite = false;
                        }
                    }
                }
                result.setPixel(x, y, allWhite ? image.getMaxGray() : 0);
            }
        }
        return result;
    }

    @Override
    public String getName() {
        return "Erosão Binária";
    }
}