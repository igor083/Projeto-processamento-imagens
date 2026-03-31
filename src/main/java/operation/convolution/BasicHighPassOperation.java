package operation.convolution;


import model.GrayImage;
import operation.interfaces.UnaryImageOperation;

public class BasicHighPassOperation extends ConvolutionSupport implements UnaryImageOperation {
    @Override
    public GrayImage apply(GrayImage image) {
        int[][] kernel = {
                {-1, -1, -1},
                {-1,  9, -1},
                {-1, -1, -1}
        };
        return applyKernel(image, kernel, 1, 0);
    }

    @Override
    public String getName() {
        return "Passa-Alta Básico";
    }
}
