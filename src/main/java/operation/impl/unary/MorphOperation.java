package operation.impl.unary;

import model.GrayImage;
import operation.UnaryImageOperation;

public class MorphOperation implements UnaryImageOperation {

    private final GrayImage targetImage;
    private final double t;

    public MorphOperation(GrayImage targetImage, double t) {
        this.targetImage = targetImage;
        this.t = Math.max(0, Math.min(1, t));
    }

    @Override
    public GrayImage apply(GrayImage sourceImage) {
        if (sourceImage.getWidth() != targetImage.getWidth() ||
                sourceImage.getHeight() != targetImage.getHeight()) {
            throw new IllegalArgumentException(
                    "As duas imagens devem ter as mesmas dimensões para o morfismo."
            );
        }

        int width = sourceImage.getWidth();
        int height = sourceImage.getHeight();
        int maxGray = Math.min(sourceImage.getMaxGray(), targetImage.getMaxGray());

        GrayImage result = new GrayImage(width, height, maxGray);

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                int child = sourceImage.getPixel(x, y);
                int current = targetImage.getPixel(x, y);

                int interpolated = (int) Math.round((1 - t) * child + t * current);
                result.setPixel(x, y, interpolated);
            }
        }

        return result;
    }

    @Override
    public String getName() {
        return "Morfismo (t = " + String.format("%.2f", t) + ")";
    }
}