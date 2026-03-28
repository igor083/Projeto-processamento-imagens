package ui;

import model.GrayImage;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public class ImagePanel extends JPanel {
    private BufferedImage image;

    public ImagePanel() {
        setBackground(new Color(245, 245, 245));
    }

    public void setGrayImage(GrayImage grayImage) {
        if (grayImage == null) {
            image = null;
            repaint();
            return;
        }

        image = new BufferedImage(grayImage.getWidth(), grayImage.getHeight(), BufferedImage.TYPE_BYTE_GRAY);
        for (int y = 0; y < grayImage.getHeight(); y++) {
            for (int x = 0; x < grayImage.getWidth(); x++) {
                int g = grayImage.getPixel(x, y);
                int rgb = (g << 16) | (g << 8) | g;
                image.setRGB(x, y, rgb);
            }
        }
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (image == null) return;

        int pw = getWidth();
        int ph = getHeight();
        int iw = image.getWidth();
        int ih = image.getHeight();

        double scale = Math.min((double) pw / iw, (double) ph / ih);
        int dw = (int) (iw * scale);
        int dh = (int) (ih * scale);
        int x = (pw - dw) / 2;
        int y = (ph - dh) / 2;

        g.drawImage(image, x, y, dw, dh, null);
    }
}