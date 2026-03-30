package operation.impl.morphological;

import model.GrayImage;
import operation.UnaryImageOperation;

public class GrayOpeningOperation implements UnaryImageOperation {

    @Override
    public GrayImage apply(GrayImage image) {
        GrayErosionOperation erosion = new GrayErosionOperation();
        GrayDilationOperation dilation = new GrayDilationOperation();
        GrayImage eroded = erosion.apply(image);
        return dilation.apply(eroded);
    }

    @Override
    public String getName() {
        return "Abertura Nível Cinza";
    }
}