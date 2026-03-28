package operation.impl;

import model.GrayImage;
import operation.UnaryImageOperation;

public class RobertsOperation implements UnaryImageOperation {
    @Override
    public GrayImage apply(GrayImage image) {
        int w = image.getWidth();
        int h = image.getHeight();
        GrayImage out = new GrayImage(w, h, image.getMaxGray());

        for (int y = 0; y < h - 1; y++) {
            for (int x = 0; x < w - 1; x++) {
                int z5 = image.getPixel(x, y);
                int z6 = image.getPixel(x + 1, y);
                int z8 = image.getPixel(x, y + 1);
                int gx = z5 - z8;
                int gy = z5 - z6;
                int value = Math.abs(gx) + Math.abs(gy);
                out.setPixel(x, y, Math.min(value, image.getMaxGray()));
            }
        }
        return out;
    }

    @Override
    public String getName() {
        return "Roberts";
    }
}
