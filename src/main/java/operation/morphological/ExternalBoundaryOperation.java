package operation.morphological;

import model.GrayImage;
import operation.interfaces.UnaryImageOperation;

public class ExternalBoundaryOperation implements UnaryImageOperation {

    private final StructuringElement se;

    public ExternalBoundaryOperation() {
        this.se = new StructuringElement();
    }

    public ExternalBoundaryOperation(StructuringElement se) {
        this.se = se;
    }

    @Override
    public GrayImage apply(GrayImage image) {
        BinaryDilationOperation dilation = new BinaryDilationOperation(se);
        GrayImage dilated = dilation.apply(image);
        int width = image.getWidth();
        int height = image.getHeight();
        int maxGray = image.getMaxGray();
        GrayImage result = new GrayImage(width, height, maxGray);

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                int original = image.getPixel(x, y);
                int dilatedVal = dilated.getPixel(x, y);
                result.setPixel(x, y, (original == 0 && dilatedVal == maxGray) ? maxGray : 0);
            }
        }
        return result;
    }

    @Override
    public String getName() {
        return "Fronteira Externa";
    }
}