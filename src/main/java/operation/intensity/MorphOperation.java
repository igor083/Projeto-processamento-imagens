package operation.intensity;

import model.GrayImage;
import operation.interfaces.UnaryImageOperation;

// Classe que implementa um morfismo (interpolação) entre duas imagens
public class MorphOperation implements UnaryImageOperation {

    // Imagem alvo (destino da transformação)
    private final GrayImage targetImage;

    // Parâmetro de interpolação (0 <= t <= 1)
    private final double t;

    public MorphOperation(GrayImage targetImage, double t) {
        this.targetImage = targetImage;

        // Garante que t fique no intervalo [0, 1]
        this.t = Math.max(0, Math.min(1, t));
    }

    @Override
    public GrayImage apply(GrayImage sourceImage) {

        // Verifica se as duas imagens possuem as mesmas dimensões
        if (sourceImage.getWidth() != targetImage.getWidth() ||
                sourceImage.getHeight() != targetImage.getHeight()) {
            throw new IllegalArgumentException(
                    "As duas imagens devem ter as mesmas dimensões para o morfismo."
            );
        }

        // Dimensões da imagem
        int width = sourceImage.getWidth();
        int height = sourceImage.getHeight();

        // Define o máximo de intensidade como o menor entre as duas imagens
        int maxGray = Math.min(sourceImage.getMaxGray(), targetImage.getMaxGray());

        // Imagem de saída
        GrayImage result = new GrayImage(width, height, maxGray);

        // Percorre todos os pixels
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {

                // Pixel da imagem de origem
                int child = sourceImage.getPixel(x, y);

                // Pixel correspondente da imagem alvo
                int current = targetImage.getPixel(x, y);

                // Interpolação linear entre as duas imagens:
                // t = 0 → apenas imagem de origem
                // t = 1 → apenas imagem alvo
                int interpolated = (int) Math.round((1 - t) * child + t * current);

                // Define o valor do pixel resultante
                result.setPixel(x, y, interpolated);
            }
        }

        // Retorna a imagem resultante do morfismo
        return result;
    }

    @Override
    public String getName() {
        // Nome da operação com o valor de t formatado
        return "Morfismo (t = " + String.format("%.2f", t) + ")";
    }
}