package operation.intensity;

import model.GrayImage;
import operation.interfaces.UnaryImageOperation;

public class LogOperation implements UnaryImageOperation {

    private final double a;

    public LogOperation(double a) {
        if (a <= 0) {
            throw new IllegalArgumentException("A constante 'a' deve ser maior que zero.");
        }
        this.a = a;
    }

    @Override
    public GrayImage apply(GrayImage image) {
        GrayImage out = new GrayImage(image.getWidth(), image.getHeight(), image.getMaxGray());
        double max = image.getMaxGray();

        double maxLog = a * Math.log(max + 1);

        for (int y = 0; y < image.getHeight(); y++) {
            for (int x = 0; x < image.getWidth(); x++) {
                int r = image.getPixel(x, y);
                double value = a * Math.log(r + 1);
                int s = (int) Math.round((value / maxLog) * max);
                out.setPixel(x, y, s);
            }
        }

        return out;
    }

    @Override
    public String getName() {
        return "Logaritmo";
    }
}
