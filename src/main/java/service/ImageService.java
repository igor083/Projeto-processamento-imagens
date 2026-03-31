package service;

import io.PgmImageReader;
import io.PgmImageWriter;
import model.GrayImage;
import operation.interfaces.BinaryImageOperation;
import operation.interfaces.UnaryImageOperation;

import java.io.File;
import java.io.IOException;

// Classe de serviço responsável por centralizar operações com imagens
public class ImageService {

    // Responsável por ler imagens PGM
    private final PgmImageReader reader = new PgmImageReader();

    // Responsável por escrever imagens PGM
    private final PgmImageWriter writer = new PgmImageWriter();

    public GrayImage load(File file) throws IOException {

        // Carrega imagem a partir de um arquivo
        return reader.read(file);
    }

    public void save(File file, GrayImage image) throws IOException {

        // Salva imagem no formato PGM (P2)
        writer.writeP2(file, image);
    }

    public GrayImage execute(UnaryImageOperation operation, GrayImage image) {

        // Executa uma operação unária (uma imagem → uma imagem)
        return operation.apply(image);
    }

    public GrayImage execute(BinaryImageOperation operation, GrayImage first, GrayImage second) {

        // Executa uma operação binária (duas imagens → uma imagem)
        return operation.apply(first, second);
    }
}