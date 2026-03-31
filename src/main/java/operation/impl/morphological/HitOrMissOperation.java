package operation.impl.morphological;

import model.GrayImage;
import operation.UnaryImageOperation;

public class HitOrMissOperation implements UnaryImageOperation {

    private final int[][] kernelForeground;
    private final int[][] kernelBackground;

    public HitOrMissOperation() {
        this.kernelForeground = new int[][]{
                {0, 0, 0},
                {0, 1, 0},
                {0, 0, 0}
        };
        this.kernelBackground = new int[][]{
                {1, 1, 1},
                {1, 0, 1},
                {1, 1, 1}
        };
    }

    @Override
    public GrayImage apply(GrayImage image) {
        int width = image.getWidth();
        int height = image.getHeight();
        int maxGray = image.getMaxGray();
        GrayImage result = new GrayImage(width, height, maxGray);

        GrayImage complement = new GrayImage(width, height, maxGray);
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                complement.setPixel(x, y, image.getPixel(x, y) == maxGray ? 0 : maxGray);
            }
        }

        GrayImage erodedForeground = erosion(image, kernelForeground);
        GrayImage erodedBackground = erosion(complement, kernelBackground);

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                if (erodedForeground.getPixel(x, y) == maxGray && erodedBackground.getPixel(x, y) == maxGray) {
                    result.setPixel(x, y, maxGray);
                } else {
                    result.setPixel(x, y, 0);
                }
            }
        }
        return result;
    }

    private GrayImage erosion(GrayImage image, int[][] kernel) {
        int width = image.getWidth();
        int height = image.getHeight();
        int maxGray = image.getMaxGray();
        int kH = kernel.length;
        int kW = kernel[0].length;
        int offY = kH / 2;
        int offX = kW / 2;

        GrayImage result = new GrayImage(width, height, maxGray);
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                boolean allMatch = true;
                for (int ky = 0; ky < kH; ky++) {
                    for (int kx = 0; kx < kW; kx++) {
                        int px = Math.max(0, Math.min(width - 1, x + kx - offX));
                        int py = Math.max(0, Math.min(height - 1, y + ky - offY));
                        int pixel = image.getPixel(px, py);
                        if (kernel[ky][kx] == 1 && pixel != maxGray) {
                            allMatch = false;
                        }
                    }
                }
                result.setPixel(x, y, allMatch ? maxGray : 0);
            }
        }
        return result;
    }

    @Override
    public String getName() {
        return "Hit-or-Miss";
    }
}