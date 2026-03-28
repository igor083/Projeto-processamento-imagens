package operation.impl.unary;

import model.GrayImage;
import operation.UnaryImageOperation;

public class SigmoidOperation implements UnaryImageOperation {

    private final double w;
    private final double sigma;

    public SigmoidOperation(double w, double sigma) {
        if (sigma <= 0) {
            throw new IllegalArgumentException("Sigma deve ser maior que zero.");
        }
        this.w = w;
        this.sigma = sigma;
    }

    @Override
    public GrayImage apply(GrayImage image) {
        GrayImage out = new GrayImage(image.getWidth(), image.getHeight(), image.getMaxGray());
        double max = image.getMaxGray();

        for (int y = 0; y < image.getHeight(); y++) {
            for (int x = 0; x < image.getWidth(); x++) {
                double r = image.getPixel(x, y);
                double s = max * (1.0 / (1.0 + Math.exp(-(r - w) / sigma)));
                out.setPixel(x, y, (int) Math.round(s));
            }
        }

        return out;
    }

    @Override
    public String getName() {
        return "ITF Sigmoide";
    }
}
