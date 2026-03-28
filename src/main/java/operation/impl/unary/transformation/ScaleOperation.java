package operation.impl.unary.transformation;


import model.GrayImage;
import operation.UnaryImageOperation;
import operation.impl.GeometricTransformUtils;

public class ScaleOperation implements UnaryImageOperation {

    private final double sx;
    private final double sy;

    public ScaleOperation(double sx, double sy) {
        if (sx == 0 || sy == 0) {
            throw new IllegalArgumentException("Os fatores de escala não podem ser zero.");
        }
        this.sx = sx;
        this.sy = sy;
    }

    @Override
    public GrayImage apply(GrayImage image) {
        double[][] transform = GeometricTransformUtils.aroundCenter(
                image,
                GeometricTransformUtils.scale(sx, sy)
        );
        return GeometricTransformUtils.applyAffine(image, transform);
    }

    @Override
    public String getName() {
        return "Escala";
    }
}