package operation.intensity;

import model.GrayImage;
import operation.interfaces.UnaryImageOperation;

// Aplica transformação de negativo da imagem
public class NegativeOperation implements UnaryImageOperation {

    @Override
    public GrayImage apply(GrayImage image) {

        // Cria imagem de saída com mesmas dimensões e faixa de cinza
        GrayImage out = new GrayImage(image.getWidth(), image.getHeight(), image.getMaxGray());

        // Percorre todos os pixels da imagem
        for (int y = 0; y < image.getHeight(); y++) {
            for (int x = 0; x < image.getWidth(); x++) {

                // Valor original do pixel
                int r = image.getPixel(x, y);

                // Aplica transformação de negativo:
                // s = maxGray - r
                out.setPixel(x, y, image.getMaxGray() - r);
            }
        }

        // Retorna imagem invertida (negativo)
        return out;
    }

    @Override
    public String getName() {
        return "Negativo";
    }
}