package operation.convolution;

import model.GrayImage;

public abstract class ConvolutionSupport {

    protected GrayImage applyKernel(GrayImage image, int[][] kernel, int divisor, int offset) {

        // Altura e largura da imagem
        int h = image.getHeight();
        int w = image.getWidth();

        // Cria imagem de saída com mesmas dimensões
        GrayImage out = new GrayImage(w, h, image.getMaxGray());

        // Percorre todos os pixels da imagem
        for (int y = 0; y < h; y++) {
            for (int x = 0; x < w; x++) {

                // Acumulador da convolução
                int sum = 0;

                // Percorre a vizinhança 3x3 do pixel atual
                for (int ky = -1; ky <= 1; ky++) {
                    for (int kx = -1; kx <= 1; kx++) {

                        // Ajusta coordenadas para não sair da imagem (tratamento de borda)
                        int px = clampCoord(x + kx, 0, w - 1);
                        int py = clampCoord(y + ky, 0, h - 1);

                        // Soma ponderada: pixel * valor do kernel
                        sum += image.getPixel(px, py) * kernel[ky + 1][kx + 1];
                    }
                }

                // Aplica divisor (normalização) e offset
                // Se divisor for 0, evita divisão (usado em filtros como Sobel)
                int value = divisor == 0 ? sum + offset : (sum / divisor) + offset;

                // Limita valor final entre 0 e maxGray
                out.setPixel(x, y, clamp(value, 0, image.getMaxGray()));
            }
        }

        // Retorna imagem filtrada
        return out;
    }

    protected int clamp(int value, int min, int max) {
        // Garante que o valor esteja dentro do intervalo permitido
        return Math.max(min, Math.min(max, value));
    }

    protected int clampCoord(int v, int min, int max) {
        // Garante que a coordenada não saia dos limites da imagem
        return Math.max(min, Math.min(max, v));
    }
}