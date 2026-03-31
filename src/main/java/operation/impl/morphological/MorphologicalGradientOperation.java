package operation.impl.morphological;

import model.GrayImage;
import operation.UnaryImageOperation;

public class MorphologicalGradientOperation implements UnaryImageOperation {

    private final StructuringElement se;

    public MorphologicalGradientOperation() {
        this.se = new StructuringElement();
    }

    public MorphologicalGradientOperation(StructuringElement se) {
        this.se = se;
    }

    @Override
    public GrayImage apply(GrayImage image) {
        GrayDilationOperation dilation = new GrayDilationOperation(se);
        GrayErosionOperation erosion = new GrayErosionOperation(se);
        GrayImage dilated = dilation.apply(image);
        GrayImage eroded = erosion.apply(image);
        int width = image.getWidth();
        int height = image.getHeight();
        int maxGray = image.getMaxGray();
        GrayImage result = new GrayImage(width, height, maxGray);

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                int diff = dilated.getPixel(x, y) - eroded.getPixel(x, y);
                result.setPixel(x, y, Math.max(0, Math.min(maxGray, diff)));
            }
        }
        return result;
    }

    @Override
    public String getName() {
        return "Gradiente Morfológico";
    }
}