package operation.impl;

import model.GrayImage;
import operation.UnaryImageOperation;

public class MeanFilterOperation extends ConvolutionSupport implements UnaryImageOperation {
    @Override
    public GrayImage apply(GrayImage image) {
        int[][] kernel = {
                {1, 1, 1},
                {1, 1, 1},
                {1, 1, 1}
        };
        return applyKernel(image, kernel, 9, 0);
    }

    @Override
    public String getName() {
        return "Filtro da Média 3x3";
    }
}