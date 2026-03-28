package ui;

import model.GrayImage;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.image.BufferedImage;

public class CartesianImagePanel extends JPanel {

    private GrayImage image;
    private BufferedImage bufferedImage;

    private Integer mouseCartesianX;
    private Integer mouseCartesianY;

    public CartesianImagePanel() {
        setBackground(Color.WHITE);

        addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseMoved(MouseEvent e) {
                updateMousePosition(e.getX(), e.getY());
            }

            @Override
            public void mouseDragged(MouseEvent e) {
                updateMousePosition(e.getX(), e.getY());
            }
        });
    }

    public void setGrayImage(GrayImage image) {
        this.image = image;

        if (image == null) {
            bufferedImage = null;
            mouseCartesianX = null;
            mouseCartesianY = null;
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
                int v = image.getPixel(x, y);
                int rgb = (v << 16) | (v << 8) | v;
                bufferedImage.setRGB(x, y, rgb);
            }
        }

        repaint();
    }

    private void updateMousePosition(int mouseX, int mouseY) {
        int centerX = getWidth() / 2;
        int centerY = getHeight() / 2;

        mouseCartesianX = mouseX - centerX;
        mouseCartesianY = centerY - mouseY;

        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2d = (Graphics2D) g;

        int w = getWidth();
        int h = getHeight();

        int centerX = w / 2;
        int centerY = h / 2;

        g2d.setColor(Color.LIGHT_GRAY);
        g2d.drawLine(0, centerY, w, centerY);
        g2d.drawLine(centerX, 0, centerX, h);

        g2d.setColor(Color.GRAY);
        g2d.drawString("X", w - 15, centerY - 5);
        g2d.drawString("Y", centerX + 5, 15);

        if (bufferedImage != null) {
            int imgW = bufferedImage.getWidth();
            int imgH = bufferedImage.getHeight();

            int drawX = centerX - imgW / 2;
            int drawY = centerY - imgH / 2;

            g2d.drawImage(bufferedImage, drawX, drawY, null);

            g2d.setColor(Color.RED);
            g2d.fillOval(centerX - 3, centerY - 3, 1, 1);
        } else {
            g2d.setColor(Color.BLACK);
            g2d.drawString("Sem imagem", 10, 20);
        }

        g2d.setColor(Color.BLACK);
        if (mouseCartesianX != null && mouseCartesianY != null) {
            g2d.drawString(
                    "Mouse (x, y): (" + mouseCartesianX + ", " + mouseCartesianY + ")",
                    10,
                    h - 10
            );
        }
    }
}