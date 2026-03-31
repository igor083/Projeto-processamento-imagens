package operation.interfaces;

import model.GrayImage;

public interface UnaryImageOperation {
    GrayImage apply(GrayImage image);
    String getName();
}