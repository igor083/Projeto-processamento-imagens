package operation.interfaces;

import model.GrayImage;

public interface BinaryImageOperation {
    GrayImage apply(GrayImage first, GrayImage second);
    String getName();
}