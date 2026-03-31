package operation.convolution;

import model.GrayImage;
import operation.interfaces.UnaryImageOperation;

// Classe que aplica um filtro passa-alta usando convolução
public class BasicHighPassOperation extends ConvolutionSupport implements UnaryImageOperation {

    @Override
    public GrayImage apply(GrayImage image) {

        // Kernel passa-alta clássico (realce de bordas)
        // Centro positivo forte e vizinhos negativos
        int[][] kernel = {
                {-1, -1, -1},
                {-1,  9, -1},
                {-1, -1, -1}
        };

        // Aplica convolução usando metodo herdado de ConvolutionSupport
        // divisor = 1 (não normaliza)
        // offset = 0 (sem ajuste adicional)
        return applyKernel(image, kernel, 1, 0);
    }

    @Override
    public String getName() {
        // Nome exibido na interface
        return "Passa-Alta Básico";
    }
}