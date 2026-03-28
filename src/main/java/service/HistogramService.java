package service;

import model.GrayImage;

public class HistogramService {

    public int[] calculateHistogram(GrayImage image) {
        int[] histogram = new int[image.getMaxGray() + 1];

        for (int y = 0; y < image.getHeight(); y++) {
            for (int x = 0; x < image.getWidth(); x++) {
                int value = image.getPixel(x, y);
                histogram[value]++;
            }
        }

        return histogram;
    }

    public GrayImage equalize(GrayImage image) {
        int[] histogram = calculateHistogram(image);
        int totalPixels = image.getWidth() * image.getHeight();
        int maxGray = image.getMaxGray();

        double[] cdf = new double[maxGray + 1];
        cdf[0] = (double) histogram[0] / totalPixels;

        for (int i = 1; i <= maxGray; i++) {
            cdf[i] = cdf[i - 1] + ((double) histogram[i] / totalPixels);
        }

        int[] transform = new int[maxGray + 1];
        for (int i = 0; i <= maxGray; i++) {
            transform[i] = (int) Math.round(cdf[i] * maxGray);
        }

        GrayImage result = new GrayImage(image.getWidth(), image.getHeight(), maxGray);

        for (int y = 0; y < image.getHeight(); y++) {
            for (int x = 0; x < image.getWidth(); x++) {
                int original = image.getPixel(x, y);
                result.setPixel(x, y, transform[original]);
            }
        }

        return result;
    }
}