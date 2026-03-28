package operation.impl.unary;

import model.GrayImage;
import operation.UnaryImageOperation;

public class LinearTransformOperation implements UnaryImageOperation {

    private final double a;
    private final double b;

    public LinearTransformOperation(double a, double b) {
        this.a = a;
        this.b = b;
    }

    @Override
    public GrayImage apply(GrayImage image) {
        GrayImage out = new GrayImage(image.getWidth(), image.getHeight(), image.getMaxGray());

        for (int y = 0; y < image.getHeight(); y++) {
            for (int x = 0; x < image.getWidth(); x++) {
                int r = image.getPixel(x, y);
                int s = (int) Math.round(a * r + b);
                out.setPixel(x, y, s);
            }
        }

        return out;
    }

    @Override
    public String getName() {
        return "Transformação Linear";
    }
}
