package operation.morphological;

import model.GrayImage;
import operation.interfaces.UnaryImageOperation;

public class BinaryOpeningOperation implements UnaryImageOperation {

    private final StructuringElement se;

    public BinaryOpeningOperation() {
        this.se = new StructuringElement();
    }

    public BinaryOpeningOperation(StructuringElement se) {
        this.se = se;
    }

    @Override
    public GrayImage apply(GrayImage image) {
        BinaryErosionOperation erosion = new BinaryErosionOperation(se);
        BinaryDilationOperation dilation = new BinaryDilationOperation(se);
        GrayImage eroded = erosion.apply(image);
        return dilation.apply(eroded);
    }

    @Override
    public String getName() {
        return "Abertura Binária";
    }
}