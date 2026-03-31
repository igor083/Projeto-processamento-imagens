package ui;

import model.GrayImage;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public class ImagePanel extends JPanel {

    private BufferedImage bufferedImage;

    public ImagePanel() {
        setBackground(Color.WHITE);
        setPreferredSize(new Dimension(256, 256));
        setMinimumSize(new Dimension(256, 256));
        setMaximumSize(new Dimension(256, 256));
    }

    public void setGrayImage(GrayImage image) {
        if (image == null) {
            bufferedImage = null;
            setPreferredSize(new Dimension(400, 400));
            revalidate();
            repaint();
            return;
        }

        bufferedImage = new BufferedImage(
                image.getWidth(),
                image.getHeight(),
                BufferedImage.TYPE_BYTE_GRAY
        );

        for (int y = 0; y < image.getHeight(); y++) {
            for (int x = 0; x < image.getWidth(); x++) {
                int g = image.getPixel(x, y);
                int rgb = (g << 16) | (g << 8) | g;
                bufferedImage.setRGB(x, y, rgb);
            }
        }

        setPreferredSize(new Dimension(bufferedImage.getWidth(), bufferedImage.getHeight()));
        revalidate();
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        if (bufferedImage == null) {
            g.setColor(Color.GRAY);
            g.drawString("Sem imagem", 10, 20);
            return;
        }

        int x = Math.max(0, (getWidth() - bufferedImage.getWidth()) / 2);
        int y = Math.max(0, (getHeight() - bufferedImage.getHeight()) / 2);

        g.drawImage(bufferedImage, x, y, null);
    }
}