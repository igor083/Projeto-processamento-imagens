package operation.convolution;

import model.GrayImage;
import operation.interfaces.UnaryImageOperation;

public class PrewittOperation implements UnaryImageOperation {

    @Override
    public GrayImage apply(GrayImage image) {

        // Kernel para detectar variação vertical (bordas horizontais)
        int[][] gx = {
                {-1, -1, -1},
                { 0,  0,  0},
                { 1,  1,  1}
        };

        // Kernel para detectar variação horizontal (bordas verticais)
        int[][] gy = {
                {-1, 0, 1},
                {-1, 0, 1},
                {-1, 0, 1}
        };

        // Dimensões da imagem
        int w = image.getWidth();
        int h = image.getHeight();

        // Imagem de saída
        GrayImage out = new GrayImage(w, h, image.getMaxGray());

        // Percorre todos os pixels
        for (int y = 0; y < h; y++) {
            for (int x = 0; x < w; x++) {

                // Gradientes nas direções x e y
                int sx = 0;
                int sy = 0;

                // Percorre vizinhança 3x3
                for (int ky = -1; ky <= 1; ky++) {
                    for (int kx = -1; kx <= 1; kx++) {

                        // Trata borda (replicação)
                        int px = Math.max(0, Math.min(w - 1, x + kx));
                        int py = Math.max(0, Math.min(h - 1, y + ky));

                        // Valor do pixel vizinho
                        int pixel = image.getPixel(px, py);

                        // Aplica convolução com os kernels gx e gy
                        sx += pixel * gx[ky + 1][kx + 1];
                        sy += pixel * gy[ky + 1][kx + 1];
                    }
                }

                // Combina os gradientes (aproximação da magnitude)
                // |Gx| + |Gy|
                int magnitude = Math.abs(sx) + Math.abs(sy);

                // Limita ao valor máximo permitido
                out.setPixel(x, y, Math.min(magnitude, image.getMaxGray()));
            }
        }

        // Retorna imagem com detecção de bordas
        return out;
    }

    @Override
    public String getName() {
        return "Prewitt";
    }
}