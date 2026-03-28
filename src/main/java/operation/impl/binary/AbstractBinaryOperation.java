package operation.impl.binary;

import model.GrayImage;
import operation.BinaryImageOperation;

public abstract class AbstractBinaryOperation implements BinaryImageOperation {

    @Override
    public GrayImage apply(GrayImage first, GrayImage second) {
        if (first == null || second == null) {
            throw new IllegalArgumentException("As duas imagens devem ser carregadas.");
        }
        if (first.getWidth() != second.getWidth() || first.getHeight() != second.getHeight()) {
            throw new IllegalArgumentException("As imagens devem ter mesmas dimensões.");
        }

        int w = first.getWidth();
        int h = first.getHeight();
        int maxGray = Math.min(first.getMaxGray(), second.getMaxGray());
        GrayImage out = new GrayImage(w, h, maxGray);

        for (int y = 0; y < h; y++) {
            for (int x = 0; x < w; x++) {
                int value = compute(first.getPixel(x, y), second.getPixel(x, y), maxGray);
                out.setPixel(x, y, Math.max(0, Math.min(maxGray, value)));
            }
        }
        return out;
    }

    protected abstract int compute(int a, int b, int maxGray);
}
