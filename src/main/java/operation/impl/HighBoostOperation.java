package operation.impl;

import model.GrayImage;
import operation.UnaryImageOperation;

public class HighBoostOperation implements UnaryImageOperation {
    private final double a;

    public HighBoostOperation(double a) {
        if (a <= 1.0) throw new IllegalArgumentException("Para high-boost, use A > 1.");
        this.a = a;
    }

    @Override
    public GrayImage apply(GrayImage image) {
        MeanFilterOperation mean = new MeanFilterOperation();
        GrayImage low = mean.apply(image);
        GrayImage out = new GrayImage(image.getWidth(), image.getHeight(), image.getMaxGray());

        for (int y = 0; y < image.getHeight(); y++) {
            for (int x = 0; x < image.getWidth(); x++) {
                int original = image.getPixel(x, y);
                int mask = original - low.getPixel(x, y);
                int value = (int) Math.round(original + a * mask);
                out.setPixel(x, y, Math.max(0, Math.min(image.getMaxGray(), value)));
            }
        }
        return out;
    }

    @Override
    public String getName() {
        return "High-Boost";
    }
}
