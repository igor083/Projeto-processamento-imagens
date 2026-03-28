package operation.impl.unary;


import model.GrayImage;
import operation.UnaryImageOperation;
import operation.impl.GeometricTransformUtils;

public class ReflectionOperation implements UnaryImageOperation {

    public enum Type {
        HORIZONTAL,
        VERTICAL
    }

    private final Type type;

    public ReflectionOperation(Type type) {
        this.type = type;
    }

    @Override
    public GrayImage apply(GrayImage image) {
        double sx = (type == Type.VERTICAL) ? -1 : 1;
        double sy = (type == Type.HORIZONTAL) ? -1 : 1;

        double[][] transform = GeometricTransformUtils.aroundCenter(
                image,
                GeometricTransformUtils.scale(sx, sy)
        );

        return GeometricTransformUtils.applyAffine(image, transform);
    }

    @Override
    public String getName() {
        return "Reflexão";
    }
}
