package io;
import model.GrayImage;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class PgmImageWriter {

    public void writeP2(File file, GrayImage image) throws IOException {

        // Cria um BufferedWriter para escrever no arquivo de saída.
        // O try-with-resources garante que o writer será fechado automaticamente.
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {

            // Escreve o identificador do formato PGM ASCII (P2).
            writer.write("P2");
            writer.newLine();

            // Escreve largura e altura da imagem.
            writer.write(image.getWidth() + " " + image.getHeight());
            writer.newLine();

            // Escreve o valor máximo de cinza (ex: 255).
            writer.write(String.valueOf(image.getMaxGray()));
            writer.newLine();

            // Percorre todos os pixels da imagem linha por linha
            for (int y = 0; y < image.getHeight(); y++) {

                for (int x = 0; x < image.getWidth(); x++) {

                    // Escreve o valor do pixel na posição (x, y)
                    writer.write(String.valueOf(image.getPixel(x, y)));

                    // Espaço entre valores (formato PGM exige separação)
                    writer.write(" ");
                }

                // Quebra de linha ao final de cada linha da imagem
                writer.newLine();
            }
        }
    }
}