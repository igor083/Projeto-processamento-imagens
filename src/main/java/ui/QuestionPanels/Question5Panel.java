package ui.QuestionPanels;

import operation.UnaryImageOperation;
import operation.impl.morphological.*;
import ui.BaseQuestionPanel;

import javax.swing.*;
import java.awt.*;
import java.util.LinkedHashMap;
import java.util.Map;

public class Question5Panel extends BaseQuestionPanel {

    private final Map<String, UnaryImageOperation> operations = new LinkedHashMap<>();

    public Question5Panel() {
        registerOperations();
        build();
    }

    private void registerOperations() {
        operations.put("Dilatação Binária", new BinaryDilationOperation());
        operations.put("Erosão Binária", new BinaryErosionOperation());
        operations.put("Abertura Binária", new BinaryOpeningOperation());
        operations.put("Fechamento Binário", new BinaryClosingOperation());
        operations.put("Dilatação Nível Cinza", new GrayDilationOperation());
        operations.put("Erosão Nível Cinza", new GrayErosionOperation());
        operations.put("Abertura Nível Cinza", new GrayOpeningOperation());
        operations.put("Fechamento Nível Cinza", new GrayClosingOperation());
    }

    private void build() {
        setLayout(new BorderLayout(10, 10));

        JPanel top = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JButton loadButton = new JButton("Carregar imagem");
        JComboBox<String> opCombo = new JComboBox<>(operations.keySet().toArray(new String[0]));
        JButton executeButton = new JButton("Executar");
        JButton saveButton = new JButton("Salvar resultado");
        JButton clearButton = new JButton("Limpar Tudo");

        top.add(loadButton);
        top.add(opCombo);
        top.add(executeButton);
        top.add(saveButton);
        top.add(clearButton);

        JPanel center = new JPanel(new GridLayout(1, 2, 10, 10));
        center.add(createViewerPanel("Imagem Original", imagePanelA));
        center.add(createViewerPanel("Resultado", resultPanel));

        loadButton.addActionListener(e -> {
            imageA = chooseAndLoadImage("Selecione a imagem PGM");
            imagePanelA.setGrayImage(imageA);
        });

        executeButton.addActionListener(e -> {
            if (imageA == null) {
                JOptionPane.showMessageDialog(this, "Carregue uma imagem primeiro.");
                return;
            }
            UnaryImageOperation op = operations.get(opCombo.getSelectedItem());
            result = imageService.execute(op, imageA);
            resultPanel.setGrayImage(result);
        });

        saveButton.addActionListener(e -> saveResult());
        clearButton.addActionListener(e -> clearAllImages());

        add(top, BorderLayout.NORTH);
        add(center, BorderLayout.CENTER);
    }
}