package io;

import model.GrayImage;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class PgmImageReader {

    public GrayImage read(File file) throws IOException {
        try (InputStream input = new FileInputStream(file)) {
            String magic = nextToken(input);

            if (!"P2".equals(magic) && !"P5".equals(magic)) {
                throw new IOException("Formato PGM não suportado. Use P2 ou P5.");
            }

            int width = Integer.parseInt(nextToken(input));
            int height = Integer.parseInt(nextToken(input));
            int maxGray = Integer.parseInt(nextToken(input));

            GrayImage image = new GrayImage(width, height, maxGray);

            if ("P2".equals(magic)) {
                readAsciiPixels(input, image);
            } else {
                readBinaryPixels(input, image);
            }

            return image;
        }
    }

    private void readAsciiPixels(InputStream input, GrayImage image) throws IOException {
        for (int y = 0; y < image.getHeight(); y++) {
            for (int x = 0; x < image.getWidth(); x++) {
                int pixel = Integer.parseInt(nextToken(input));
                image.setPixel(x, y, pixel);
            }
        }
    }

    private void readBinaryPixels(InputStream input, GrayImage image) throws IOException {
        int width = image.getWidth();
        int height = image.getHeight();

        if (image.getMaxGray() > 255) {
            throw new IOException("Somente P5 com maxGray <= 255 é suportado.");
        }

        byte[] data = input.readAllBytes();
        if (data.length < width * height) {
            throw new IOException("Arquivo PGM binário incompleto.");
        }

        int index = 0;
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                image.setPixel(x, y, data[index++] & 0xFF);
            }
        }
    }

    private String nextToken(InputStream input) throws IOException {
        List<Byte> bytes = new ArrayList<>();
        int c;

        do {
            c = input.read();
            if (c == '#') {
                while (c != '\n' && c != -1) {
                    c = input.read();
                }
            }
        } while (Character.isWhitespace(c));

        while (c != -1 && !Character.isWhitespace(c)) {
            bytes.add((byte) c);
            c = input.read();
        }

        return new String(toByteArray(bytes), StandardCharsets.US_ASCII);
    }

    private byte[] toByteArray(List<Byte> bytes) {
        byte[] result = new byte[bytes.size()];
        for (int i = 0; i < bytes.size(); i++) {
            result[i] = bytes.get(i);
        }
        return result;
    }
}