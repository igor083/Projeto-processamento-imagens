package operation.impl.morphological;

import model.GrayImage;
import operation.UnaryImageOperation;

public class BinaryOpeningOperation implements UnaryImageOperation {

    private final int threshold;

    public BinaryOpeningOperation() {
        this(128);
    }

    public BinaryOpeningOperation(int threshold) {
        this.threshold = threshold;
    }

    @Override
    public GrayImage apply(GrayImage image) {
        BinaryErosionOperation erosion = new BinaryErosionOperation(threshold);
        BinaryDilationOperation dilation = new BinaryDilationOperation(threshold);
        GrayImage eroded = erosion.apply(image);
        return dilation.apply(eroded);
    }

    @Override
    public String getName() {
        return "Abertura Binária";
    }
}