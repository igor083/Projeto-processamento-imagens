package operation.impl.unary;


import model.GrayImage;
import operation.UnaryImageOperation;
import operation.impl.GeometricTransformUtils;

public class ShearOperation implements UnaryImageOperation {

    private final double shx;
    private final double shy;

    public ShearOperation(double shx, double shy) {
        this.shx = shx;
        this.shy = shy;
    }

    @Override
    public GrayImage apply(GrayImage image) {
        double[][] transform = GeometricTransformUtils.aroundCenter(
                image,
                GeometricTransformUtils.shear(shx, shy)
        );
        return GeometricTransformUtils.applyAffine(image, transform);
    }

    @Override
    public String getName() {
        return "Cisalhamento";
    }
}