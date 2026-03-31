package operation.impl.morphological;

import model.GrayImage;
import operation.UnaryImageOperation;

public class BinaryErosionOperation implements UnaryImageOperation {

    private final StructuringElement se;

    public BinaryErosionOperation() {
        this.se = new StructuringElement();
    }

    public BinaryErosionOperation(StructuringElement se) {
        this.se = se;
    }

    @Override
    public GrayImage apply(GrayImage image) {
        int width = image.getWidth();
        int height = image.getHeight();
        int maxGray = image.getMaxGray();
        GrayImage result = new GrayImage(width, height, maxGray);
        int size = se.getSize();
        int offset = size / 2;

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                boolean allWhite = true;
                for (int ky = 0; ky < size; ky++) {
                    for (int kx = 0; kx < size; kx++) {
                        if (se.isActive(ky, kx)) {
                            int px = x + (kx - offset);
                            int py = y + (ky - offset);
                            if (px < 0 || px >= width || py < 0 || py >= height) {
                                allWhite = false;
                            } else if (image.getPixel(px, py) != maxGray) {
                                allWhite = false;
                            }
                        }
                    }
                }
                result.setPixel(x, y, allWhite ? maxGray : 0);
            }
        }
        return result;
    }

    @Override
    public String getName() {
        return "Erosão Binária";
    }
}