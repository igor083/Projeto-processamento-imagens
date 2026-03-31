package operation.morphological;

import model.GrayImage;
import operation.interfaces.UnaryImageOperation;

public class ComplementOperation implements UnaryImageOperation {

    @Override
    public GrayImage apply(GrayImage image) {
        int width = image.getWidth();
        int height = image.getHeight();
        int maxGray = image.getMaxGray();
        GrayImage result = new GrayImage(width, height, maxGray);

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                int pixel = image.getPixel(x, y);
                // Complemento binário: 0 vira maxGray, qualquer outro vira 0
                if (pixel == 0) {
                    result.setPixel(x, y, maxGray);
                } else {
                    result.setPixel(x, y, 0);
                }
            }
        }
        return result;
    }

    @Override
    public String getName() {
        return "Complemento";
    }
}