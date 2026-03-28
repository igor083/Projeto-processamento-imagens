package operation.impl.unary;

import model.GrayImage;
import operation.UnaryImageOperation;

public class NegativeOperation implements UnaryImageOperation {

    @Override
    public GrayImage apply(GrayImage image) {
        GrayImage out = new GrayImage(image.getWidth(), image.getHeight(), image.getMaxGray());

        for (int y = 0; y < image.getHeight(); y++) {
            for (int x = 0; x < image.getWidth(); x++) {
                int r = image.getPixel(x, y);
                out.setPixel(x, y, image.getMaxGray() - r);
            }
        }

        return out;
    }

    @Override
    public String getName() {
        return "Negativo";
    }
}