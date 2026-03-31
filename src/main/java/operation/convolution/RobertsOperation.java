package operation.convolution;

import model.GrayImage;
import operation.interfaces.UnaryImageOperation;

public class RobertsOperation implements UnaryImageOperation {

    @Override
    public GrayImage apply(GrayImage image) {

        // Dimensões da imagem
        int w = image.getWidth();
        int h = image.getHeight();

        // Imagem de saída
        GrayImage out = new GrayImage(w, h, image.getMaxGray());

        // Percorre a imagem (até w-1 e h-1 pois usa vizinhos à direita e abaixo)
        for (int y = 0; y < h - 1; y++) {
            for (int x = 0; x < w - 1; x++) {

                // Pega os pixels da vizinhança 2x2
                int z5 = image.getPixel(x, y);         // pixel atual
                int z6 = image.getPixel(x + 1, y);     // direita
                int z8 = image.getPixel(x, y + 1);     // abaixo

                // Gradiente vertical (diferença com pixel abaixo)
                int gx = z5 - z8;

                // Gradiente horizontal (diferença com pixel à direita)
                int gy = z5 - z6;

                // Magnitude aproximada do gradiente
                int value = Math.abs(gx) + Math.abs(gy);

                // Limita ao valor máximo permitido
                out.setPixel(x, y, Math.min(value, image.getMaxGray()));
            }
        }

        // Retorna imagem com bordas detectadas
        return out;
    }

    @Override
    public String getName() {
        return "Roberts";
    }
}