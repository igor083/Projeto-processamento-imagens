package ui.QuestionPanels;

import operation.BinaryImageOperation;
import operation.UnaryImageOperation;
import operation.impl.*;
import operation.impl.binary.*;
import ui.BaseQuestionPanel;

import javax.swing.*;
import java.awt.*;
import java.util.LinkedHashMap;
import java.util.Map;

public class Question1Panel extends BaseQuestionPanel {

    private final Map<String, UnaryImageOperation> unaryOps = new LinkedHashMap<>();
    private final Map<String, BinaryImageOperation> binaryOps = new LinkedHashMap<>();

    public Question1Panel() {
        registerOperations();
        build();
    }

    private void registerOperations() {
        unaryOps.put("Filtro da Média 3x3", new MeanFilterOperation());
        unaryOps.put("Filtro da Mediana 3x3", new MedianFilterOperation());
        unaryOps.put("Passa-Alta Básico", new BasicHighPassOperation());
        unaryOps.put("Roberts", new RobertsOperation());
        unaryOps.put("Roberts Cruzado", new RobertsCrossOperation());
        unaryOps.put("Prewitt", new PrewittOperation());
        unaryOps.put("Sobel", new SobelOperation());
        unaryOps.put("High-Boost (A=1.2)", new HighBoostOperation(1.2));

        binaryOps.put("Soma", new AddOperation());
        binaryOps.put("Subtração", new SubtractOperation());
        binaryOps.put("Multiplicação", new MultiplyOperation());
        binaryOps.put("Divisão", new DivideOperation());
        binaryOps.put("AND", new AndOperation());
        binaryOps.put("OR", new OrOperation());
        binaryOps.put("XOR", new XorOperation());
    }

    private void build() {
        JPanel top = new JPanel(new GridLayout(2, 1, 0, 8));

        JPanel unaryPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JButton loadA1 = new JButton("Carregar imagem");
        JComboBox<String> unaryCombo = new JComboBox<>(unaryOps.keySet().toArray(new String[0]));
        JButton runUnary = new JButton("Executar filtro/realce");
        JButton save = new JButton("Salvar resultado");

        loadA1.addActionListener(e -> {
            imageA = chooseAndLoadImage("Selecione a imagem PGM");
            imagePanelA.setGrayImage(imageA);
        });

        runUnary.addActionListener(e -> {
            if (imageA == null) {
                JOptionPane.showMessageDialog(this, "Carregue uma imagem primeiro.");
                return;
            }
            UnaryImageOperation op = unaryOps.get(unaryCombo.getSelectedItem());
            result = imageService.execute(op, imageA);
            resultPanel.setGrayImage(result);
        });

        save.addActionListener(e -> saveResult());

        unaryPanel.add(new JLabel("Filtros/Realce:"));
        unaryPanel.add(loadA1);
        unaryPanel.add(unaryCombo);
        unaryPanel.add(runUnary);
        unaryPanel.add(save);

        JPanel binaryPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JButton loadA2 = new JButton("Imagem A");
        JButton loadB2 = new JButton("Imagem B");
        JComboBox<String> binaryCombo = new JComboBox<>(binaryOps.keySet().toArray(new String[0]));
        JButton runBinary = new JButton("Executar operação binária");
        JButton clearButton = new JButton("Limpar Tudo");

        loadA2.addActionListener(e -> {
            imageA = chooseAndLoadImage("Selecione a imagem A");
            imagePanelA.setGrayImage(imageA);
        });

        loadB2.addActionListener(e -> {
            imageB = chooseAndLoadImage("Selecione a imagem B");
            imagePanelB.setGrayImage(imageB);
        });

        runBinary.addActionListener(e -> {
            if (imageA == null || imageB == null) {
                JOptionPane.showMessageDialog(this, "Carregue as duas imagens.");
                return;
            }
            BinaryImageOperation op = binaryOps.get(binaryCombo.getSelectedItem());
            result = imageService.execute(op, imageA, imageB);
            resultPanel.setGrayImage(result);
        });

        clearButton.addActionListener(e -> clearAllImages());

        binaryPanel.add(new JLabel("Aritméticas/Lógicas:"));
        binaryPanel.add(loadA2);
        binaryPanel.add(loadB2);
        binaryPanel.add(binaryCombo);
        binaryPanel.add(runBinary);
        binaryPanel.add(clearButton);

        top.add(unaryPanel);
        top.add(binaryPanel);

        JPanel center = new JPanel(new GridLayout(1, 3, 10, 10));
        center.add(createViewerPanel("Imagem A", imagePanelA));
        center.add(createViewerPanel("Imagem B", imagePanelB));
        center.add(createViewerPanel("Resultado", resultPanel));

        add(top, BorderLayout.NORTH);
        add(center, BorderLayout.CENTER);
    }
}
