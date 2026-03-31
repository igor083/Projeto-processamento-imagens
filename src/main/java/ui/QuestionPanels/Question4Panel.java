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

    // Serviço responsável por calcular e equalizar histogramas
    private final HistogramService histogramService = new HistogramService();

    // Painel que exibe o histograma da imagem original
    private final HistogramPanel originalHistogramPanel = new HistogramPanel();

    // Painel que exibe o histograma da imagem equalizada
    private final HistogramPanel equalizedHistogramPanel = new HistogramPanel();

    public Question4Panel() {
        // Monta a interface da tela
        build();
    }

    private void build() {
        // Painel superior com botões
        JPanel top = new JPanel(new FlowLayout(FlowLayout.LEFT));

        JButton loadButton = new JButton("Carregar imagem");
        JButton equalizeButton = new JButton("Equalizar Histograma");
        JButton saveButton = new JButton("Salvar resultado");
        JButton clearButton = new JButton("Limpar Tudo");

        loadButton.addActionListener(e -> {
            // Carrega a imagem original
            imageA = chooseAndLoadImage("Selecione a imagem lena.pgm");

            // Exibe no painel da imagem original
            imagePanelA.setGrayImage(imageA);

            if (imageA != null) {
                // Calcula e mostra o histograma da imagem original
                originalHistogramPanel.setHistogram(histogramService.calculateHistogram(imageA));

                // Limpa resultado anterior
                result = null;
                resultPanel.setGrayImage(null);

                // Limpa histograma equalizado
                equalizedHistogramPanel.setHistogram(null);
            }
        });

        equalizeButton.addActionListener(e -> {
            // Verifica se a imagem foi carregada
            if (imageA == null) {
                JOptionPane.showMessageDialog(this, "Carregue uma imagem primeiro.");
                return;
            }

            // Aplica equalização de histograma
            GrayImage equalized = histogramService.equalize(imageA);

            // Armazena e exibe o resultado
            result = equalized;
            resultPanel.setGrayImage(result);

            // Atualiza histograma original
            originalHistogramPanel.setHistogram(histogramService.calculateHistogram(imageA));

            // Calcula e mostra histograma da imagem equalizada
            equalizedHistogramPanel.setHistogram(histogramService.calculateHistogram(result));
        });

        // Salva a imagem resultante
        saveButton.addActionListener(e -> saveResult());

        clearButton.addActionListener(e -> {
            // Limpa imagens e resultado
            clearAllImages();

            // Limpa histogramas
            originalHistogramPanel.setHistogram(null);
            equalizedHistogramPanel.setHistogram(null);
        });

        // Adiciona botões ao painel superior
        top.add(loadButton);
        top.add(equalizeButton);
        top.add(saveButton);
        top.add(clearButton);

        // Painel para exibir imagens (original e equalizada)
        JPanel imageContainer = new JPanel(new GridLayout(1, 2, 10, 10));
        imageContainer.add(createViewerPanel("Imagem Original", imagePanelA));
        imageContainer.add(createViewerPanel("Imagem Equalizada", resultPanel));

        // Painel para exibir histogramas (original e equalizado)
        JPanel histogramContainer = new JPanel(new GridLayout(1, 2, 10, 10));
        histogramContainer.add(createViewerPanel("Histograma Original", originalHistogramPanel));
        histogramContainer.add(createViewerPanel("Histograma Equalizado", equalizedHistogramPanel));

        // Painel central com imagens e histogramas
        JPanel center = new JPanel(new GridLayout(2, 1, 10, 10));
        center.add(imageContainer);
        center.add(histogramContainer);

        // Adiciona componentes na tela
        add(top, BorderLayout.NORTH);
        add(center, BorderLayout.CENTER);
    }
}