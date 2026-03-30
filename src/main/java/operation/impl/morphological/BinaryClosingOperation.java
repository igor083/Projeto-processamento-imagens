package operation.impl.morphological;

import model.GrayImage;
import operation.UnaryImageOperation;

public class BinaryClosingOperation implements UnaryImageOperation {

    private final int threshold;

    public BinaryClosingOperation() {
        this(128);
    }

    public BinaryClosingOperation(int threshold) {
        this.threshold = threshold;
    }

    @Override
    public GrayImage apply(GrayImage image) {
        BinaryDilationOperation dilation = new BinaryDilationOperation(threshold);
        BinaryErosionOperation erosion = new BinaryErosionOperation(threshold);
        GrayImage dilated = dilation.apply(image);
        return erosion.apply(dilated);
    }

    @Override
    public String getName() {
        return "Fechamento Binário";
    }
}