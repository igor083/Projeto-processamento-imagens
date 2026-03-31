package operation.intensity;

import model.GrayImage;
import operation.interfaces.UnaryImageOperation;

// Classe que implementa uma transformação sigmoide (não linear) de intensidade
public class SigmoidOperation implements UnaryImageOperation {

    // w: ponto de transição (centro da sigmoide)
    private final double w;

    // sigma: controla a inclinação da curva (contraste local)
    private final double sigma;

    public SigmoidOperation(double w, double sigma) {
        // Garante que sigma seja válido (evita divisão por zero)
        if (sigma <= 0) {
            throw new IllegalArgumentException("Sigma deve ser maior que zero.");
        }
        this.w = w;
        this.sigma = sigma;
    }

    @Override
    public GrayImage apply(GrayImage image) {

        // Cria a imagem de saída
        GrayImage out = new GrayImage(image.getWidth(), image.getHeight(), image.getMaxGray());

        // Valor máximo de intensidade (ex: 255)
        double max = image.getMaxGray();

        // Percorre todos os pixels
        for (int y = 0; y < image.getHeight(); y++) {
            for (int x = 0; x < image.getWidth(); x++) {

                // Valor original do pixel
                double r = image.getPixel(x, y);

                // Função sigmoide:
                // s = max * (1 / (1 + e^(-(r - w)/sigma)))
                // - w define o ponto central da transição
                // - sigma controla quão suave ou abrupta é a curva
                double s = max * (1.0 / (1.0 + Math.exp(-(r - w) / sigma)));

                // Define o novo valor do pixel
                out.setPixel(x, y, (int) Math.round(s));
            }
        }

        // Retorna a imagem transformada
        return out;
    }

    @Override
    public String getName() {
        // Nome da operação
        return "ITF Sigmoide";
    }
}