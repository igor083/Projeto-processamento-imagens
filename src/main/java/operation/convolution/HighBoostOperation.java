package operation.convolution;

import model.GrayImage;
import operation.interfaces.UnaryImageOperation;

// Classe que implementa uma operação de realce de imagem do tipo High-Boost
public class HighBoostOperation implements UnaryImageOperation {

    // Fator de amplificação do detalhe (A > 1)
    private final double a;

    // Construtor recebe o fator A
    public HighBoostOperation(double a) {
        // Garante que o fator seja válido para high-boost
        if (a <= 1.0) throw new IllegalArgumentException("Para high-boost, use A > 1.");
        this.a = a;
    }

    @Override
    public GrayImage apply(GrayImage image) {
        // Cria um filtro de média (suavização / low-pass)
        MeanFilterOperation mean = new MeanFilterOperation();

        // Aplica o filtro de média para obter a versão suavizada da imagem
        GrayImage low = mean.apply(image);

        // Cria a imagem de saída com mesmas dimensões e faixa de cinza
        GrayImage out = new GrayImage(image.getWidth(), image.getHeight(), image.getMaxGray());

        // Percorre todos os pixels da imagem
        for (int y = 0; y < image.getHeight(); y++) {
            for (int x = 0; x < image.getWidth(); x++) {

                // Valor do pixel original
                int original = image.getPixel(x, y);

                // Máscara de detalhes (high-frequency): original - suavizado
                int mask = original - low.getPixel(x, y);

                // Fórmula do high-boost: f + A * (f - f_low)
                int value = (int) Math.round(original + a * mask);

                // Garante que o valor final fique dentro do intervalo válido [0, maxGray]
                out.setPixel(x, y, Math.max(0, Math.min(image.getMaxGray(), value)));
            }
        }

        // Retorna a imagem resultante com realce de detalhes
        return out;
    }

    @Override
    public String getName() {
        // Nome da operação
        return "High-Boost";
    }
}