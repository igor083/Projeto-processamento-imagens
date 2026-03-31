package operation.intensity;

import model.GrayImage;
import operation.interfaces.UnaryImageOperation;

// Aplica transformação logarítmica para realce de regiões escuras
public class LogOperation implements UnaryImageOperation {

    // Constante de escala do log
    private final double a;

    public LogOperation(double a) {

        // Garante valor válido para a constante
        if (a <= 0) {
            throw new IllegalArgumentException("A constante 'a' deve ser maior que zero.");
        }

        this.a = a;
    }

    @Override
    public GrayImage apply(GrayImage image) {

        // Cria imagem de saída
        GrayImage out = new GrayImage(image.getWidth(), image.getHeight(), image.getMaxGray());

        // Valor máximo de cinza (ex: 255)
        double max = image.getMaxGray();

        // Valor máximo da transformação log (usado para normalização)
        double maxLog = a * Math.log(max + 1);

        // Percorre todos os pixels
        for (int y = 0; y < image.getHeight(); y++) {
            for (int x = 0; x < image.getWidth(); x++) {

                // Valor original do pixel
                int r = image.getPixel(x, y);

                // Aplica transformação logarítmica: s = a * log(r + 1)
                double value = a * Math.log(r + 1);

                // Normaliza para faixa [0, maxGray]
                int s = (int) Math.round((value / maxLog) * max);

                // Define pixel transformado
                out.setPixel(x, y, s);
            }
        }

        // Retorna imagem transformada
        return out;
    }

    @Override
    public String getName() {
        return "Logaritmo";
    }
}