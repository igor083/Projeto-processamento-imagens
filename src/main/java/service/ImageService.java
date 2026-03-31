package service;

import io.PgmImageReader;
import io.PgmImageWriter;
import model.GrayImage;
import operation.interfaces.BinaryImageOperation;
import operation.interfaces.UnaryImageOperation;


import java.io.File;
import java.io.IOException;

public class ImageService {
    private final PgmImageReader reader = new PgmImageReader();
    private final PgmImageWriter writer = new PgmImageWriter();

    public GrayImage load(File file) throws IOException {
        return reader.read(file);
    }

    public void save(File file, GrayImage image) throws IOException {
        writer.writeP2(file, image);
    }

    public GrayImage execute(UnaryImageOperation operation, GrayImage image) {
        return operation.apply(image);
    }

    public GrayImage execute(BinaryImageOperation operation, GrayImage first, GrayImage second) {
        return operation.apply(first, second);
    }
}