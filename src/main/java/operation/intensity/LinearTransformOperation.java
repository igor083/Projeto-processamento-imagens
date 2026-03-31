package operation.intensity;

import model.GrayImage;
import operation.interfaces.UnaryImageOperation;

// Classe que implementa uma transformação linear de intensidade
public class LinearTransformOperation implements UnaryImageOperation {

    // Coeficientes da transformação: s = a*r + b
    private final double a;
    private final double b;

    public LinearTransformOperation(double a, double b) {
        // a controla o contraste (ganho)
        // b controla o brilho (deslocamento)
        this.a = a;
        this.b = b;
    }

    @Override
    public GrayImage apply(GrayImage image) {

        // Cria a imagem de saída com mesmas dimensões e faixa de cinza
        GrayImage out = new GrayImage(image.getWidth(), image.getHeight(), image.getMaxGray());

        // Percorre todos os pixels da imagem
        for (int y = 0; y < image.getHeight(); y++) {
            for (int x = 0; x < image.getWidth(); x++) {

                // Valor original do pixel
                int r = image.getPixel(x, y);

                // Aplica a transformação linear: s = a*r + b
                int s = (int) Math.round(a * r + b);

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
        return "Transformação Linear";
    }
}