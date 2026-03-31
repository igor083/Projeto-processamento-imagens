package operation.convolution;

import model.GrayImage;
import operation.interfaces.UnaryImageOperation;

// Classe que implementa o operador de Sobel (detecção de bordas)
public class SobelOperation implements UnaryImageOperation {

    @Override
    public GrayImage apply(GrayImage image) {

        // Kernel Sobel para detectar variação no eixo Y (bordas horizontais)
        int[][] gx = {
                {-1, -2, -1},
                { 0,  0,  0},
                { 1,  2,  1}
        };

        // Kernel Sobel para detectar variação no eixo X (bordas verticais)
        int[][] gy = {
                {-1, 0, 1},
                {-2, 0, 2},
                {-1, 0, 1}
        };

        // Largura e altura da imagem
        int w = image.getWidth();
        int h = image.getHeight();

        // Imagem de saída
        GrayImage out = new GrayImage(w, h, image.getMaxGray());

        // Percorre todos os pixels da imagem
        for (int y = 0; y < h; y++) {
            for (int x = 0; x < w; x++) {

                // Acumuladores para os gradientes em X e Y
                int sx = 0;
                int sy = 0;

                // Percorre a vizinhança 3x3
                for (int ky = -1; ky <= 1; ky++) {
                    for (int kx = -1; kx <= 1; kx++) {

                        // Trata bordas da imagem (clamping)
                        int px = Math.max(0, Math.min(w - 1, x + kx));
                        int py = Math.max(0, Math.min(h - 1, y + ky));

                        // Valor do pixel vizinho
                        int pixel = image.getPixel(px, py);

                        // Aplica convolução com os kernels Sobel
                        sx += pixel * gx[ky + 1][kx + 1];
                        sy += pixel * gy[ky + 1][kx + 1];
                    }
                }

                // Combina os gradientes (aproximação do módulo: |Gx| + |Gy|)
                // Limita ao valor máximo permitido pela imagem
                out.setPixel(x, y, Math.min(Math.abs(sx) + Math.abs(sy), image.getMaxGray()));
            }
        }

        // Retorna a imagem com as bordas detectadas
        return out;
    }

    @Override
    public String getName() {
        // Nome da operação
        return "Sobel";
    }
}