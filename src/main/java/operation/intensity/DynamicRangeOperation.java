package operation.intensity;


import model.GrayImage;
import operation.interfaces.UnaryImageOperation;

public class DynamicRangeOperation implements UnaryImageOperation {

    private final int targetRange;

    public DynamicRangeOperation(int targetRange) {
        if (targetRange <= 0) {
            throw new IllegalArgumentException("A faixa alvo deve ser maior que zero.");
        }
        this.targetRange = targetRange;
    }

    @Override
    public GrayImage apply(GrayImage image) {
        GrayImage out = new GrayImage(image.getWidth(), image.getHeight(), image.getMaxGray());

        int min = Integer.MAX_VALUE;
        int max = Integer.MIN_VALUE;

        for (int y = 0; y < image.getHeight(); y++) {
            for (int x = 0; x < image.getWidth(); x++) {
                int p = image.getPixel(x, y);
                if (p < min) min = p;
                if (p > max) max = p;
            }
        }

        if (max == min) {
            return image.copy();
        }

        for (int y = 0; y < image.getHeight(); y++) {
            for (int x = 0; x < image.getWidth(); x++) {
                int f = image.getPixel(x, y);
                double normalized = ((double) (f - min) / (max - min)) * targetRange;
                int s = (int) Math.round(normalized);

                if (targetRange != image.getMaxGray()) {
                    s = (int) Math.round((s / (double) targetRange) * image.getMaxGray());
                }

                out.setPixel(x, y, s);
            }
        }

        return out;
    }

    @Override
    public String getName() {
        return "Faixa Dinâmica";
    }
}