package operation.impl.morphological;

import model.GrayImage;
import operation.UnaryImageOperation;

public class BottomHatOperation implements UnaryImageOperation {

    private final StructuringElement se;

    public BottomHatOperation() {
        this.se = new StructuringElement();
    }

    public BottomHatOperation(StructuringElement se) {
        this.se = se;
    }

    @Override
    public GrayImage apply(GrayImage image) {
        GrayClosingOperation closing = new GrayClosingOperation(se);
        GrayImage closed = closing.apply(image);
        int width = image.getWidth();
        int height = image.getHeight();
        int maxGray = image.getMaxGray();
        GrayImage result = new GrayImage(width, height, maxGray);

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                int diff = closed.getPixel(x, y) - image.getPixel(x, y);
                result.setPixel(x, y, Math.max(0, Math.min(maxGray, diff)));
            }
        }
        return result;
    }

    @Override
    public String getName() {
        return "Bottom-Hat";
    }
}