package operation.impl;


import model.GrayImage;

public abstract class ConvolutionSupport {

    protected GrayImage applyKernel(GrayImage image, int[][] kernel, int divisor, int offset) {
        int h = image.getHeight();
        int w = image.getWidth();
        GrayImage out = new GrayImage(w, h, image.getMaxGray());

        for (int y = 0; y < h; y++) {
            for (int x = 0; x < w; x++) {
                int sum = 0;
                for (int ky = -1; ky <= 1; ky++) {
                    for (int kx = -1; kx <= 1; kx++) {
                        int px = clampCoord(x + kx, 0, w - 1);
                        int py = clampCoord(y + ky, 0, h - 1);
                        sum += image.getPixel(px, py) * kernel[ky + 1][kx + 1];
                    }
                }
                int value = divisor == 0 ? sum + offset : (sum / divisor) + offset;
                out.setPixel(x, y, clamp(value, 0, image.getMaxGray()));
            }
        }
        return out;
    }

    protected int clamp(int value, int min, int max) {
        return Math.max(min, Math.min(max, value));
    }

    protected int clampCoord(int v, int min, int max) {
        return Math.max(min, Math.min(max, v));
    }
}