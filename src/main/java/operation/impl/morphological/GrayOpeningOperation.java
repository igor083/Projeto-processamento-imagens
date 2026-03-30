package operation.impl.morphological;

import model.GrayImage;
import operation.UnaryImageOperation;

public class GrayOpeningOperation implements UnaryImageOperation {

    private final StructuringElement se;

    public GrayOpeningOperation() {
        this.se = new StructuringElement();
    }

    public GrayOpeningOperation(StructuringElement se) {
        this.se = se;
    }

    @Override
    public GrayImage apply(GrayImage image) {
        GrayErosionOperation erosion = new GrayErosionOperation(se);
        GrayDilationOperation dilation = new GrayDilationOperation(se);
        GrayImage eroded = erosion.apply(image);
        return dilation.apply(eroded);
    }

    @Override
    public String getName() {
        return "Abertura Nível Cinza";
    }
}