package operation.intensity;

import model.GrayImage;
import operation.interfaces.UnaryImageOperation;

// Classe que implementa a correção gama (transformação de intensidade)
public class GammaOperation implements UnaryImageOperation {

    // Valor do gamma (controla o brilho/contraste não linear)
    private final double gamma;

    public GammaOperation(double gamma) {
        // Garante que o valor esteja no intervalo permitido
        if (gamma < 0.0 || gamma > 1.0) {
            throw new IllegalArgumentException("Gamma deve estar entre 0 e 1.");
        }
        this.gamma = gamma;
    }

    @Override
    public GrayImage apply(GrayImage image) {

        // Cria imagem de saída com mesmas dimensões e faixa de cinza
        GrayImage out = new GrayImage(image.getWidth(), image.getHeight(), image.getMaxGray());

        // Valor máximo de intensidade (ex: 255)
        double max = image.getMaxGray();

        // Percorre todos os pixels da imagem
        for (int y = 0; y < image.getHeight(); y++) {
            for (int x = 0; x < image.getWidth(); x++) {

                // Normaliza o pixel para o intervalo [0, 1]
                double r = image.getPixel(x, y) / max;

                // Aplica a transformação gama: s = r^gamma
                // Depois reescala para o intervalo original [0, max]
                int s = (int) Math.round(Math.pow(r, gamma) * max);

                // Define o novo valor do pixel
                out.setPixel(x, y, s);
            }
        }

        // Retorna a imagem transformada
        return out;
    }

    @Override
    public String getName() {
        // Nome da operação
        return "Gamma";
    }
}