package operation.geometric;


import model.GrayImage;
import operation.interfaces.UnaryImageOperation;

public class TranslationOperation implements UnaryImageOperation {

    private final int tx;
    private final int ty;

    public TranslationOperation(double tx, double ty) {
        this.tx = (int) Math.round(tx);
        this.ty = (int) Math.round(ty);
    }

    @Override
    public GrayImage apply(GrayImage image) {
        int width = image.getWidth();
        int height = image.getHeight();

        GrayImage output = new GrayImage(width, height, image.getMaxGray());

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                int sourceX = x - tx;
                int sourceY = y + ty;

                if (sourceX >= 0 && sourceX < width && sourceY >= 0 && sourceY < height) {
                    output.setPixel(x, y, image.getPixel(sourceX, sourceY));
                } else {
                    output.setPixel(x, y, 0);
                }
            }
        }

        return output;
    }

    @Override
    public String getName() {
        return "Translação";
    }
}