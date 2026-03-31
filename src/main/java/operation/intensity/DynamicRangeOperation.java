package operation.intensity;

import model.GrayImage;
import operation.interfaces.UnaryImageOperation;

// Ajusta a faixa dinâmica da imagem (expansão de contraste)
public class DynamicRangeOperation implements UnaryImageOperation {

    // Faixa alvo desejada (ex: 255)
    private final int targetRange;

    public DynamicRangeOperation(int targetRange) {

        // Garante que a faixa seja válida
        if (targetRange <= 0) {
            throw new IllegalArgumentException("A faixa alvo deve ser maior que zero.");
        }

        this.targetRange = targetRange;
    }

    @Override
    public GrayImage apply(GrayImage image) {

        // Cria imagem de saída
        GrayImage out = new GrayImage(image.getWidth(), image.getHeight(), image.getMaxGray());

        // Inicializa min e max para encontrar extremos da imagem
        int min = Integer.MAX_VALUE;
        int max = Integer.MIN_VALUE;

        // Primeiro passo: encontrar o menor e maior valor de pixel
        for (int y = 0; y < image.getHeight(); y++) {
            for (int x = 0; x < image.getWidth(); x++) {

                int p = image.getPixel(x, y);

                if (p < min) min = p;
                if (p > max) max = p;
            }
        }

        // Se todos os pixels forem iguais, não há contraste para ajustar
        if (max == min) {
            return image.copy();
        }

        // Segundo passo: normalizar os pixels para a nova faixa
        for (int y = 0; y < image.getHeight(); y++) {
            for (int x = 0; x < image.getWidth(); x++) {

                int f = image.getPixel(x, y);

                // Normaliza para intervalo [0, targetRange]
                double normalized = ((double) (f - min) / (max - min)) * targetRange;

                // Converte para inteiro
                int s = (int) Math.round(normalized);

                // Ajusta novamente para o maxGray da imagem original (se necessário)
                if (targetRange != image.getMaxGray()) {
                    s = (int) Math.round((s / (double) targetRange) * image.getMaxGray());
                }

                // Define pixel na imagem de saída
                out.setPixel(x, y, s);
            }
        }

        // Retorna imagem com contraste ajustado
        return out;
    }

    @Override
    public String getName() {
        return "Faixa Dinâmica";
    }
}