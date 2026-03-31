package operation.binary;

import model.GrayImage;
import operation.interfaces.BinaryImageOperation;

// Classe base para operações binárias (envolvem duas imagens)
public abstract class AbstractBinaryOperation implements BinaryImageOperation {

    @Override
    public GrayImage apply(GrayImage first, GrayImage second) {

        // Valida se ambas as imagens foram carregadas
        if (first == null || second == null) {
            throw new IllegalArgumentException("As duas imagens devem ser carregadas.");
        }

        // Verifica se as imagens têm mesmas dimensões
        if (first.getWidth() != second.getWidth() || first.getHeight() != second.getHeight()) {
            throw new IllegalArgumentException("As imagens devem ter mesmas dimensões.");
        }

        // Obtém dimensões da imagem
        int w = first.getWidth();
        int h = first.getHeight();

        // Define o nível máximo de cinza como o menor entre as duas imagens
        int maxGray = Math.min(first.getMaxGray(), second.getMaxGray());

        // Cria imagem de saída
        GrayImage out = new GrayImage(w, h, maxGray);

        // Percorre todos os pixels
        for (int y = 0; y < h; y++) {
            for (int x = 0; x < w; x++) {

                // Aplica operação específica (definida nas subclasses)
                int value = compute(
                        first.getPixel(x, y),
                        second.getPixel(x, y),
                        maxGray
                );

                // Garante que o valor fique dentro do intervalo válido
                out.setPixel(x, y, Math.max(0, Math.min(maxGray, value)));
            }
        }

        // Retorna imagem resultante
        return out;
    }

    // Método abstrato que cada operação (soma, subtração, AND, etc.) implementa
    protected abstract int compute(int a, int b, int maxGray);
}