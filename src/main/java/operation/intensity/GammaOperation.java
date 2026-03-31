package operation.intensity;

import model.GrayImage;
import operation.interfaces.UnaryImageOperation;

public class GammaOperation implements UnaryImageOperation {

    private final double gamma;

    public GammaOperation(double gamma) {
        if (gamma < 0.0 || gamma > 1.0) {
            throw new IllegalArgumentException("Gamma deve estar entre 0 e 1.");
        }
        this.gamma = gamma;
    }

    @Override
    public GrayImage apply(GrayImage image) {
        GrayImage out = new GrayImage(image.getWidth(), image.getHeight(), image.getMaxGray());
        double max = image.getMaxGray();

        for (int y = 0; y < image.getHeight(); y++) {
            for (int x = 0; x < image.getWidth(); x++) {
                double r = image.getPixel(x, y) / max;
                int s = (int) Math.round(Math.pow(r, gamma) * max);
                out.setPixel(x, y, s);
            }
        }

        return out;
    }

    @Override
    public String getName() {
        return "Gamma";
    }
}