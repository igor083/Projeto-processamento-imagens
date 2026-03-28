package operation;

import model.GrayImage;

public interface BinaryImageOperation {
    GrayImage apply(GrayImage first, GrayImage second);
    String getName();
}