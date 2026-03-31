package operation.morphological;

import model.GrayImage;
import operation.interfaces.UnaryImageOperation;

public class InternalBoundaryOperation implements UnaryImageOperation {

    private final StructuringElement se;

    public InternalBoundaryOperation() {
        this.se = new StructuringElement();
    }

    public InternalBoundaryOperation(StructuringElement se) {
        this.se = se;
    }

    @Override
    public GrayImage apply(GrayImage image) {
        BinaryErosionOperation erosion = new BinaryErosionOperation(se);
        GrayImage eroded = erosion.apply(image);
        int width = image.getWidth();
        int height = image.getHeight();
        int maxGray = image.getMaxGray();
        GrayImage result = new GrayImage(width, height, maxGray);

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                int original = image.getPixel(x, y);
                int erodedVal = eroded.getPixel(x, y);
                result.setPixel(x, y, (original == maxGray && erodedVal == 0) ? maxGray : 0);
            }
        }
        return result;
    }

    @Override
    public String getName() {
        return "Fronteira Interna";
    }
}