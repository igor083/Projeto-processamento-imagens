package io;
import model.GrayImage;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;


public class PgmImageWriter {


    public void writeP2(File file, GrayImage image) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
            writer.write("P2");
            writer.newLine();
            writer.write(image.getWidth() + " " + image.getHeight());
            writer.newLine();
            writer.write(String.valueOf(image.getMaxGray()));
            writer.newLine();

            for (int y = 0; y < image.getHeight(); y++) {
                for (int x = 0; x < image.getWidth(); x++) {
                    writer.write(String.valueOf(image.getPixel(x, y)));
                    writer.write(" ");
                }
                writer.newLine();
            }
        }
    }
}
