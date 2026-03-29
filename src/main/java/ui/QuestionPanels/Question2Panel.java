package ui.QuestionPanels;

import model.GrayImage;
import operation.UnaryImageOperation;
import operation.impl.unary.MorphOperation;
import ui.BaseQuestionPanel;

import javax.swing.*;
import java.awt.*;

public class Question2Panel extends BaseQuestionPanel {

    private GrayImage childImage;   // imagem quando criança
    private GrayImage currentImage; // imagem atual

    private JSlider morphSlider;
    private JLabel sliderValueLabel;
    private JButton loadChildButton;
    private JButton loadCurrentButton;

    public Question2Panel() {
        build();
    }

    private void build() {
        setLayout(new BorderLayout(10, 10));

        // Painel superior com botões
        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));

        loadChildButton = new JButton("Carregar imagem (criança)");
        loadCurrentButton = new JButton("Carregar imagem (atual)");
        JButton saveButton = new JButton("Salvar resultado");
        JButton clearButton = new JButton("Limpar Tudo");

        topPanel.add(loadChildButton);
        topPanel.add(loadCurrentButton);
        topPanel.add(saveButton);
        topPanel.add(clearButton);

        // Painel do slider
        JPanel sliderPanel = new JPanel(new BorderLayout(10, 5));
        sliderPanel.setBorder(BorderFactory.createTitledBorder("Morfismo - Controle de Tempo"));

        morphSlider = new JSlider(JSlider.HORIZONTAL, 0, 100, 0);
        morphSlider.setMajorTickSpacing(25);
        morphSlider.setMinorTickSpacing(5);
        morphSlider.setPaintTicks(true);
        morphSlider.setPaintLabels(true);

        sliderValueLabel = new JLabel("t = 0.00 (100% criança → 0% atual)", SwingConstants.CENTER);

        sliderPanel.add(morphSlider, BorderLayout.CENTER);
        sliderPanel.add(sliderValueLabel, BorderLayout.SOUTH);

        // Painel central com as imagens - USANDO O MESMO PADRÃO DAS OUTRAS QUESTÕES
        JPanel centerPanel = new JPanel(new GridLayout(1, 3, 10, 10));
        centerPanel.add(createViewerPanel("Imagem Criança", imagePanelA));
        centerPanel.add(createViewerPanel("Imagem Atual", imagePanelB));
        centerPanel.add(createViewerPanel("Morfismo (resultado)", resultPanel));

        // ADICIONA NO CENTRO (não no sul) - isso resolve o tamanho
        add(topPanel, BorderLayout.NORTH);
        add(sliderPanel, BorderLayout.SOUTH);  // slider fica embaixo
        add(centerPanel, BorderLayout.CENTER); // imagens ocupam o centro

        // --- Ações dos botões ---
        loadChildButton.addActionListener(e -> {
            childImage = chooseAndLoadImage("Selecione a imagem quando criança (formato PGM)");
            imagePanelA.setGrayImage(childImage);
            checkAndUpdateMorph();
        });

        loadCurrentButton.addActionListener(e -> {
            currentImage = chooseAndLoadImage("Selecione a imagem atual (formato PGM)");
            imagePanelB.setGrayImage(currentImage);
            checkAndUpdateMorph();
        });

        saveButton.addActionListener(e -> saveResult());

        clearButton.addActionListener(e -> {
            childImage = null;
            currentImage = null;
            result = null;
            imagePanelA.setGrayImage(null);
            imagePanelB.setGrayImage(null);
            resultPanel.setGrayImage(null);
            morphSlider.setValue(0);
            sliderValueLabel.setText("t = 0.00 (100% criança → 0% atual)");
        });

        // Ação do slider: atualiza o morfismo em tempo real
        morphSlider.addChangeListener(e -> {
            if (!morphSlider.getValueIsAdjusting()) {
                checkAndUpdateMorph();
            }
        });
    }

    private void checkAndUpdateMorph() {
        // Verifica se as duas imagens estão carregadas
        if (childImage == null || currentImage == null) {
            return;
        }

        // Verifica se as dimensões são compatíveis
        if (childImage.getWidth() != currentImage.getWidth() ||
                childImage.getHeight() != currentImage.getHeight()) {
            JOptionPane.showMessageDialog(this,
                    "As imagens devem ter as mesmas dimensões para o morfismo.\n" +
                            "Criança: " + childImage.getWidth() + "x" + childImage.getHeight() + "\n" +
                            "Atual: " + currentImage.getWidth() + "x" + currentImage.getHeight(),
                    "Erro de dimensões",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Calcula o valor de t (0 a 1)
        double t = morphSlider.getValue() / 100.0;
        sliderValueLabel.setText(String.format("t = %.2f (%.0f%% criança → %.0f%% atual)",
                t, (1-t)*100, t*100));

        // Executa o morfismo
        try {
            UnaryImageOperation morphOp = new MorphOperation(currentImage, t);
            result = imageService.execute(morphOp, childImage);
            resultPanel.setGrayImage(result);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this,
                    "Erro ao aplicar morfismo: " + ex.getMessage(),
                    "Erro",
                    JOptionPane.ERROR_MESSAGE);
        }
    }
}