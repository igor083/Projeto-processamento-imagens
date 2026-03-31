package service;

import model.GrayImage;

// Classe responsável por operações relacionadas ao histograma da imagem
public class HistogramService {

    public int[] calculateHistogram(GrayImage image) {

        // Cria um vetor de frequência com tamanho igual ao número de níveis de cinza
        int[] histogram = new int[image.getMaxGray() + 1];

        // Percorre todos os pixels da imagem
        for (int y = 0; y < image.getHeight(); y++) {
            for (int x = 0; x < image.getWidth(); x++) {

                // Obtém o valor do pixel
                int value = image.getPixel(x, y);

                // Incrementa a frequência daquele nível de cinza
                histogram[value]++;
            }
        }

        // Retorna o histograma (frequência de cada intensidade)
        return histogram;
    }

    public GrayImage equalize(GrayImage image) {

        // Calcula o histograma da imagem
        int[] histogram = calculateHistogram(image);

        // Total de pixels da imagem
        int totalPixels = image.getWidth() * image.getHeight();

        // Valor máximo de intensidade
        int maxGray = image.getMaxGray();

        // Vetor da função de distribuição acumulada (CDF)
        double[] cdf = new double[maxGray + 1];

        // Inicializa o primeiro valor da CDF
        cdf[0] = (double) histogram[0] / totalPixels;

        // Calcula a CDF acumulando as probabilidades
        for (int i = 1; i <= maxGray; i++) {
            cdf[i] = cdf[i - 1] + ((double) histogram[i] / totalPixels);
        }

        // Vetor de transformação (mapeia intensidades antigas para novas)
        int[] transform = new int[maxGray + 1];

        // Aplica a fórmula da equalização:
        // novo_valor = CDF * maxGray
        for (int i = 0; i <= maxGray; i++) {
            transform[i] = (int) Math.round(cdf[i] * maxGray);
        }

        // Cria a imagem resultante
        GrayImage result = new GrayImage(image.getWidth(), image.getHeight(), maxGray);

        // Aplica a transformação em cada pixel da imagem
        for (int y = 0; y < image.getHeight(); y++) {
            for (int x = 0; x < image.getWidth(); x++) {

                // Valor original do pixel
                int original = image.getPixel(x, y);

                // Substitui pelo valor equalizado
                result.setPixel(x, y, transform[original]);
            }
        }

        // Retorna a imagem com histograma equalizado
        return result;
    }
}