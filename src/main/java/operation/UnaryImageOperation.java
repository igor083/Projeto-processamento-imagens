package operation;

import model.GrayImage;

public interface UnaryImageOperation {
    GrayImage apply(GrayImage image);
    String getName();
}