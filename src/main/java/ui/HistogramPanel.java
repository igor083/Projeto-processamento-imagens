package ui;


import javax.swing.*;
import java.awt.*;

public class HistogramPanel extends JPanel {

    private int[] histogram;

    public HistogramPanel() {
        setPreferredSize(new Dimension(400, 250));
        setBackground(Color.WHITE);
    }

    public void setHistogram(int[] histogram) {
        this.histogram = histogram;
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        if (histogram == null || histogram.length == 0) {
            g.setColor(Color.GRAY);
            g.drawString("Histograma não carregado.", 20, 20);
            return;
        }

        int width = getWidth();
        int height = getHeight();
        int padding = 30;
        int chartWidth = width - 2 * padding;
        int chartHeight = height - 2 * padding;

        int maxValue = 0;
        for (int value : histogram) {
            if (value > maxValue) {
                maxValue = value;
            }
        }

        if (maxValue == 0) {
            return;
        }

        g.setColor(Color.BLACK);
        g.drawRect(padding, padding, chartWidth, chartHeight);
        g.setColor(Color.BLACK);

        g.drawString("Nível de cinza", padding + chartWidth / 2 - 40, padding + chartHeight + 30);

        Graphics2D g2d = (Graphics2D) g;
        g2d.rotate(-Math.PI / 2);

        g2d.drawString("Intensidade", -(padding + chartHeight / 2 + 40), padding - 10);

        g2d.rotate(Math.PI / 2);
        double xScale = (double) chartWidth / histogram.length;
        double yScale = (double) chartHeight / maxValue;

        g.setColor(Color.DARK_GRAY);

        for (int i = 0; i < histogram.length; i++) {
            int barHeight = (int) Math.round(histogram[i] * yScale);
            int x = padding + (int) Math.round(i * xScale);
            int y = padding + chartHeight - barHeight;
            int barWidth = Math.max(1, (int) Math.ceil(xScale));

            g.fillRect(x, y, barWidth, barHeight);
        }

        g.setColor(Color.BLACK);
        g.drawString("0", padding - 5, padding + chartHeight + 15);
        g.drawString(String.valueOf(histogram.length - 1), padding + chartWidth - 15, padding + chartHeight + 15);
    }
}