package operation.impl.morphological;

import model.GrayImage;
import operation.UnaryImageOperation;

public class BinaryClosingOperation implements UnaryImageOperation {

    private final StructuringElement se;

    public BinaryClosingOperation() {
        this.se = new StructuringElement();
    }

    public BinaryClosingOperation(StructuringElement se) {
        this.se = se;
    }

    @Override
    public GrayImage apply(GrayImage image) {
        BinaryDilationOperation dilation = new BinaryDilationOperation(se);
        BinaryErosionOperation erosion = new BinaryErosionOperation(se);
        GrayImage dilated = dilation.apply(image);
        return erosion.apply(dilated);
    }

    @Override
    public String getName() {
        return "Fechamento Binário";
    }
}