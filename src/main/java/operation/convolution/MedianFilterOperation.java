package operation.convolution;

import model.GrayImage;
import operation.interfaces.UnaryImageOperation;

import java.util.Arrays;

public class MedianFilterOperation implements UnaryImageOperation {

    @Override
    public GrayImage apply(GrayImage image) {

        // Obtém dimensões da imagem
        int h = image.getHeight();
        int w = image.getWidth();

        // Cria imagem de saída com mesmas dimensões
        GrayImage out = new GrayImage(w, h, image.getMaxGray());

        // Array para armazenar os 9 valores da vizinhança 3x3
        int[] values = new int[9];

        // Percorre todos os pixels da imagem
        for (int y = 0; y < h; y++) {
            for (int x = 0; x < w; x++) {

                int k = 0;

                // Percorre vizinhança 3x3 ao redor do pixel (x, y)
                for (int dy = -1; dy <= 1; dy++) {
                    for (int dx = -1; dx <= 1; dx++) {

                        // Ajusta coordenadas para não sair da imagem (tratamento de borda)
                        int px = Math.max(0, Math.min(w - 1, x + dx));
                        int py = Math.max(0, Math.min(h - 1, y + dy));

                        // Armazena valor do pixel na vizinhança
                        values[k++] = image.getPixel(px, py);
                    }
                }

                // Ordena os 9 valores da vizinhança
                Arrays.sort(values);

                // Seleciona o valor central (mediana)
                out.setPixel(x, y, values[4]);
            }
        }

        // Retorna imagem filtrada
        return out;
    }

    @Override
    public String getName() {
        // Nome exibido na interface
        return "Filtro da Mediana 3x3";
    }
}