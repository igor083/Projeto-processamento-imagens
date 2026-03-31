package operation.geometric;

import model.GrayImage;
import operation.interfaces.UnaryImageOperation;

public class RotationOperation implements UnaryImageOperation {

    private final double angleDegrees;

    public RotationOperation(double angleDegrees) {
        this.angleDegrees = angleDegrees;
    }

    @Override
    public GrayImage apply(GrayImage image) {
        double[][] transform = GeometricTransformUtils.aroundCenter(
                image,
                GeometricTransformUtils.rotationDegrees(angleDegrees)
        );
        return GeometricTransformUtils.applyAffine(image, transform);
    }

    @Override
    public String getName() {
        return "Rotação";
    }
}