package operation.impl.morphological;

import model.GrayImage;
import operation.UnaryImageOperation;

public class TopHatOperation implements UnaryImageOperation {

    private final StructuringElement se;

    public TopHatOperation() {
        this.se = new StructuringElement();
    }

    public TopHatOperation(StructuringElement se) {
        this.se = se;
    }

    @Override
    public GrayImage apply(GrayImage image) {
        GrayOpeningOperation opening = new GrayOpeningOperation(se);
        GrayImage opened = opening.apply(image);
        int width = image.getWidth();
        int height = image.getHeight();
        int maxGray = image.getMaxGray();
        GrayImage result = new GrayImage(width, height, maxGray);

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                int diff = image.getPixel(x, y) - opened.getPixel(x, y);
                result.setPixel(x, y, Math.max(0, Math.min(maxGray, diff)));
            }
        }
        return result;
    }

    @Override
    public String getName() {
        return "Top-Hat";
    }
}