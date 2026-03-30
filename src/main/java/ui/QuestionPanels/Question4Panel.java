package ui.QuestionPanels;
import ui.BaseQuestionPanel;

import javax.swing.*;
import java.awt.*;

import model.GrayImage;
import service.HistogramService;
import ui.HistogramPanel;

import javax.swing.*;
import java.awt.*;

public class Question4Panel extends BaseQuestionPanel {

    private final HistogramService histogramService = new HistogramService();

    private final HistogramPanel originalHistogramPanel = new HistogramPanel();
    private final HistogramPanel equalizedHistogramPanel = new HistogramPanel();

    public Question4Panel() {
        build();
    }

    private void build() {
        JPanel top = new JPanel(new FlowLayout(FlowLayout.LEFT));

        JButton loadButton = new JButton("Carregar imagem");
        JButton equalizeButton = new JButton("Equalizar Histograma");
        JButton saveButton = new JButton("Salvar resultado");
        JButton clearButton = new JButton("Limpar Tudo");

        loadButton.addActionListener(e -> {
            imageA = chooseAndLoadImage("Selecione a imagem lena.pgm");
            imagePanelA.setGrayImage(imageA);

            if (imageA != null) {
                originalHistogramPanel.setHistogram(histogramService.calculateHistogram(imageA));
                result = null;
                resultPanel.setGrayImage(null);
                equalizedHistogramPanel.setHistogram(null);
            }
        });

        equalizeButton.addActionListener(e -> {
            if (imageA == null) {
                JOptionPane.showMessageDialog(this, "Carregue uma imagem primeiro.");
                return;
            }

            GrayImage equalized = histogramService.equalize(imageA);
            result = equalized;
            resultPanel.setGrayImage(result);

            originalHistogramPanel.setHistogram(histogramService.calculateHistogram(imageA));
            equalizedHistogramPanel.setHistogram(histogramService.calculateHistogram(result));
        });

        saveButton.addActionListener(e -> saveResult());

        clearButton.addActionListener(e -> {
            clearAllImages();
            originalHistogramPanel.setHistogram(null);
            equalizedHistogramPanel.setHistogram(null);
        });

        top.add(loadButton);
        top.add(equalizeButton);
        top.add(saveButton);
        top.add(clearButton);

        JPanel imageContainer = new JPanel(new GridLayout(1, 2, 10, 10));
        imageContainer.add(createViewerPanel("Imagem Original", imagePanelA));
        imageContainer.add(createViewerPanel("Imagem Equalizada", resultPanel));

        JPanel histogramContainer = new JPanel(new GridLayout(1, 2, 10, 10));
        histogramContainer.add(createViewerPanel("Histograma Original", originalHistogramPanel));
        histogramContainer.add(createViewerPanel("Histograma Equalizado", equalizedHistogramPanel));

        JPanel center = new JPanel(new GridLayout(2, 1, 10, 10));
        center.add(imageContainer);
        center.add(histogramContainer);

        add(top, BorderLayout.NORTH);
        add(center, BorderLayout.CENTER);
    }
}