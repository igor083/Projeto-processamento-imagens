package operation.morphological;

import model.GrayImage;
import operation.interfaces.UnaryImageOperation;

public class GrayClosingOperation implements UnaryImageOperation {

    private final StructuringElement se;

    public GrayClosingOperation() {
        this.se = new StructuringElement();
    }

    public GrayClosingOperation(StructuringElement se) {
        this.se = se;
    }

    @Override
    public GrayImage apply(GrayImage image) {
        GrayDilationOperation dilation = new GrayDilationOperation(se);
        GrayErosionOperation erosion = new GrayErosionOperation(se);
        GrayImage dilated = dilation.apply(image);
        return erosion.apply(dilated);
    }

    @Override
    public String getName() {
        return "Fechamento Nível Cinza";
    }
}