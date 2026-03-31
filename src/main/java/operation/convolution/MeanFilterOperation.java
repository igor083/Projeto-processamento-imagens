package operation.convolution;

import model.GrayImage;
import operation.interfaces.UnaryImageOperation;

// Classe que implementa o filtro da média (suavização)
public class MeanFilterOperation extends ConvolutionSupport implements UnaryImageOperation {

    @Override
    public GrayImage apply(GrayImage image) {

        // Kernel 3x3 com todos os valores iguais a 1
        // Representa uma média simples dos pixels da vizinhança
        int[][] kernel = {
                {1, 1, 1},
                {1, 1, 1},
                {1, 1, 1}
        };

        // Aplica o kernel:
        // - divisor = 9 (soma dos valores do kernel, para calcular a média)
        // - offset = 0 (nenhum ajuste adicional no resultado)
        return applyKernel(image, kernel, 9, 0);
    }

    @Override
    public String getName() {
        // Nome da operação
        return "Filtro da Média 3x3";
    }
}