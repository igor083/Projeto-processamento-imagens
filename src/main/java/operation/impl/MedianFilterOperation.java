package operation.impl;



import model.GrayImage;
import operation.UnaryImageOperation;

import java.util.Arrays;

public class MedianFilterOperation implements UnaryImageOperation {
    @Override
    public GrayImage apply(GrayImage image) {
        int h = image.getHeight();
        int w = image.getWidth();
        GrayImage out = new GrayImage(w, h, image.getMaxGray());
        int[] values = new int[9];

        for (int y = 0; y < h; y++) {
            for (int x = 0; x < w; x++) {
                int k = 0;
                for (int dy = -1; dy <= 1; dy++) {
                    for (int dx = -1; dx <= 1; dx++) {
                        int px = Math.max(0, Math.min(w - 1, x + dx));
                        int py = Math.max(0, Math.min(h - 1, y + dy));
                        values[k++] = image.getPixel(px, py);
                    }
                }
                Arrays.sort(values);
                out.setPixel(x, y, values[4]);
            }
        }
        return out;
    }

    @Override
    public String getName() {
        return "Filtro da Mediana 3x3";
    }
}