package operation.impl.morphological;

import model.GrayImage;
import operation.UnaryImageOperation;

public class GrayDilationOperation implements UnaryImageOperation {

    private final StructuringElement se;

    public GrayDilationOperation() {
        this.se = new StructuringElement();
    }

    public GrayDilationOperation(StructuringElement se) {
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
                int max = 0;
                for (int ky = 0; ky < size; ky++) {
                    for (int kx = 0; kx < size; kx++) {
                        if (se.isActive(ky, kx)) {
                            int px = x + (kx - offset);
                            int py = y + (ky - offset);
                            if (px >= 0 && px < width && py >= 0 && py < height) {
                                int pixel = image.getPixel(px, py);
                                if (pixel > max) {
                                    max = pixel;
                                }
                            }
                        }
                    }
                }
                result.setPixel(x, y, max);
            }
        }
        return result;
    }

    @Override
    public String getName() {
        return "Dilatação Nível Cinza";
    }
}