package operation.geometric;

import model.GrayImage;
import operation.interfaces.UnaryImageOperation;

// Implementa uma transformação baseada no "Gato de Arnold"
// Essa transformação embaralha os pixels da imagem de forma determinística
public class ShearOperation implements UnaryImageOperation {

    // Número de vezes que a transformação será aplicada
    private final int iterations;

    public ShearOperation(int iterations) {

        // Não permite número negativo de iterações
        if (iterations < 0) {
            throw new IllegalArgumentException("Número de iterações não pode ser negativo.");
        }

        this.iterations = iterations;
    }

    @Override
    public GrayImage apply(GrayImage image) {

        int width = image.getWidth();
        int height = image.getHeight();

        // O Gato de Arnold só funciona corretamente em imagens quadradas
        if (width != height) {
            throw new IllegalArgumentException("A transformação do gato de Arnold requer imagem quadrada.");
        }

        int N = width;

        // Faz uma cópia da imagem original (para não alterar diretamente)
        GrayImage current = image.copy();

        // Aplica a transformação várias vezes
        for (int iter = 0; iter < iterations; iter++) {

            // Nova imagem para armazenar o resultado da iteração atual
            GrayImage next = new GrayImage(N, N, current.getMaxGray());

            // Percorre todos os pixels
            for (int y = 0; y < N; y++) {
                for (int x = 0; x < N; x++) {

                    // Transformação do Gato de Arnold:
                    // [x'] = (x + y) mod N
                    // [y'] = (x + 2y) mod N
                    int newX = (x + y) % N;
                    int newY = (x + 2 * y) % N;

                    // Move o pixel para a nova posição
                    next.setPixel(newX, newY, current.getPixel(x, y));
                }
            }

            // Atualiza imagem atual para próxima iteração
            current = next;
        }

        // Retorna imagem após todas as iterações
        return current;
    }

    @Override
    public String getName() {
        return "Cisalhamento (Gato de Arnold)";
    }
}