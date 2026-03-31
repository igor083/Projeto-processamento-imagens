package operation.convolution;

import model.GrayImage;
import operation.interfaces.UnaryImageOperation;

public class PrewittOperation implements UnaryImageOperation {
    @Override
    public GrayImage apply(GrayImage image) {
        int[][] gx = {
                {-1, -1, -1},
                { 0,  0,  0},
                { 1,  1,  1}
        };
        int[][] gy = {
                {-1, 0, 1},
                {-1, 0, 1},
                {-1, 0, 1}
        };

        int w = image.getWidth();
        int h = image.getHeight();
        GrayImage out = new GrayImage(w, h, image.getMaxGray());

        for (int y = 0; y < h; y++) {
            for (int x = 0; x < w; x++) {
                int sx = 0;
                int sy = 0;
                for (int ky = -1; ky <= 1; ky++) {
                    for (int kx = -1; kx <= 1; kx++) {
                        int px = Math.max(0, Math.min(w - 1, x + kx));
                        int py = Math.max(0, Math.min(h - 1, y + ky));
                        int pixel = image.getPixel(px, py);
                        sx += pixel * gx[ky + 1][kx + 1];
                        sy += pixel * gy[ky + 1][kx + 1];
                    }
                }
                out.setPixel(x, y, Math.min(Math.abs(sx) + Math.abs(sy), image.getMaxGray()));
            }
        }
        return out;
    }

    @Override
    public String getName() {
        return "Prewitt";
    }
}