package io;

import model.GrayImage;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class PgmImageReader {

    public GrayImage read(File file) throws IOException {

        // Abre o arquivo como stream de bytes
        try (InputStream input = new FileInputStream(file)) {

            // Lê o "magic number" que define o tipo do PGM (P2 ou P5)
            String magic = nextToken(input);

            // Valida formato suportado
            if (!"P2".equals(magic) && !"P5".equals(magic)) {
                throw new IOException("Formato PGM não suportado. Use P2 ou P5.");
            }

            // Lê largura, altura e valor máximo de cinza
            int width = Integer.parseInt(nextToken(input));
            int height = Integer.parseInt(nextToken(input));
            int maxGray = Integer.parseInt(nextToken(input));

            // Cria imagem com essas dimensões
            GrayImage image = new GrayImage(width, height, maxGray);

            // Decide leitura com base no tipo
            if ("P2".equals(magic)) {
                // ASCII
                readAsciiPixels(input, image);
            } else {
                // Binário
                readBinaryPixels(input, image);
            }

            return image;
        }
    }

    private void readAsciiPixels(InputStream input, GrayImage image) throws IOException {

        // Percorre todos os pixels lendo tokens (valores em texto)
        for (int y = 0; y < image.getHeight(); y++) {
            for (int x = 0; x < image.getWidth(); x++) {

                // Converte token para inteiro
                int pixel = Integer.parseInt(nextToken(input));

                // Define pixel na imagem
                image.setPixel(x, y, pixel);
            }
        }
    }

    private void readBinaryPixels(InputStream input, GrayImage image) throws IOException {

        int width = image.getWidth();
        int height = image.getHeight();

        // Limitação: suporta apenas 1 byte por pixel
        if (image.getMaxGray() > 255) {
            throw new IOException("Somente P5 com maxGray <= 255 é suportado.");
        }

        // Lê todos os bytes restantes do arquivo
        byte[] data = input.readAllBytes();

        // Verifica se há dados suficientes
        if (data.length < width * height) {
            throw new IOException("Arquivo PGM binário incompleto.");
        }

        // Preenche imagem com os valores binários
        int index = 0;
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {

                // "& 0xFF" converte byte assinado para inteiro positivo (0–255)
                image.setPixel(x, y, data[index++] & 0xFF);
            }
        }
    }

    private String nextToken(InputStream input) throws IOException {

        // Lista para armazenar bytes do token
        List<Byte> bytes = new ArrayList<>();
        int c;

        // Ignora espaços em branco e comentários (# ...)
        do {
            c = input.read();

            // Se encontrar comentário, ignora até o fim da linha
            if (c == '#') {
                while (c != '\n' && c != -1) {
                    c = input.read();
                }
            }
        } while (Character.isWhitespace(c));

        // Lê caracteres do token até encontrar espaço
        while (c != -1 && !Character.isWhitespace(c)) {
            bytes.add((byte) c);
            c = input.read();
        }

        // Converte lista de bytes em string ASCII
        return new String(toByteArray(bytes), StandardCharsets.US_ASCII);
    }

    private byte[] toByteArray(List<Byte> bytes) {

        // Converte lista de Byte para array primitivo byte[]
        byte[] result = new byte[bytes.size()];
        for (int i = 0; i < bytes.size(); i++) {
            result[i] = bytes.get(i);
        }
        return result;
    }
}