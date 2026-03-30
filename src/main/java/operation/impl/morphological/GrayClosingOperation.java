package operation.impl.morphological;

import model.GrayImage;
import operation.UnaryImageOperation;

public class GrayClosingOperation implements UnaryImageOperation {

    @Override
    public GrayImage apply(GrayImage image) {
        GrayDilationOperation dilation = new GrayDilationOperation();
        GrayErosionOperation erosion = new GrayErosionOperation();
        GrayImage dilated = dilation.apply(image);
        return erosion.apply(dilated);
    }

    @Override
    public String getName() {
        return "Fechamento Nível Cinza";
    }
}