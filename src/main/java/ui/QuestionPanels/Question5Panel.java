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
    private StructuringElement currentSE = new StructuringElement();
    private JTextField[][] kernelFields;

    public Question5Panel() {
        registerOperations();
        build();
    }

    private void registerOperations() {
        operations.put("Complemento", new ComplementOperation());
        operations.put("Dilatação Binária", new BinaryDilationOperation(currentSE));
        operations.put("Erosão Binária", new BinaryErosionOperation(currentSE));
        operations.put("Abertura Binária", new BinaryOpeningOperation(currentSE));
        operations.put("Fechamento Binário", new BinaryClosingOperation(currentSE));
        operations.put("Fronteira Interna", new InternalBoundaryOperation(currentSE));
        operations.put("Fronteira Externa", new ExternalBoundaryOperation(currentSE));
        operations.put("Dilatação Nível Cinza", new GrayDilationOperation(currentSE));
        operations.put("Erosão Nível Cinza", new GrayErosionOperation(currentSE));
        operations.put("Abertura Nível Cinza", new GrayOpeningOperation(currentSE));
        operations.put("Fechamento Nível Cinza", new GrayClosingOperation(currentSE));
        operations.put("Gradiente Morfológico", new MorphologicalGradientOperation(currentSE));
        operations.put("Top-Hat", new TopHatOperation(currentSE));
        operations.put("Bottom-Hat", new BottomHatOperation(currentSE));
        operations.put("Hit-or-Miss", new HitOrMissOperation());
    }

    private void updateOperationsWithNewKernel() {
        operations.put("Dilatação Binária", new BinaryDilationOperation(currentSE));
        operations.put("Erosão Binária", new BinaryErosionOperation(currentSE));
        operations.put("Abertura Binária", new BinaryOpeningOperation(currentSE));
        operations.put("Fechamento Binário", new BinaryClosingOperation(currentSE));
        operations.put("Fronteira Interna", new InternalBoundaryOperation(currentSE));
        operations.put("Fronteira Externa", new ExternalBoundaryOperation(currentSE));
        operations.put("Dilatação Nível Cinza", new GrayDilationOperation(currentSE));
        operations.put("Erosão Nível Cinza", new GrayErosionOperation(currentSE));
        operations.put("Abertura Nível Cinza", new GrayOpeningOperation(currentSE));
        operations.put("Fechamento Nível Cinza", new GrayClosingOperation(currentSE));
        operations.put("Gradiente Morfológico", new MorphologicalGradientOperation(currentSE));
        operations.put("Top-Hat", new TopHatOperation(currentSE));
        operations.put("Bottom-Hat", new BottomHatOperation(currentSE));
    }

    private void build() {
        setLayout(new BorderLayout(10, 10));

        // Painel superior com botões
        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JButton loadButton = new JButton("Carregar imagem");
        JComboBox<String> opCombo = new JComboBox<>(operations.keySet().toArray(new String[0]));
        JButton executeButton = new JButton("Executar");
        JButton saveButton = new JButton("Salvar resultado");
        JButton clearButton = new JButton("Limpar Tudo");

        topPanel.add(loadButton);
        topPanel.add(opCombo);
        topPanel.add(executeButton);
        topPanel.add(saveButton);
        topPanel.add(clearButton);

        // Painel das imagens (esquerda)
        JPanel imagePanel = new JPanel(new GridLayout(1, 2, 10, 10));
        imagePanel.add(createViewerPanel("Imagem Original", imagePanelA));
        imagePanel.add(createViewerPanel("Resultado", resultPanel));

        // Painel do kernel (direita)
        JPanel kernelPanel = new JPanel(new BorderLayout(5, 5));
        kernelPanel.setBorder(BorderFactory.createTitledBorder("Elemento Estruturante (Kernel 3x3)"));
        kernelPanel.setPreferredSize(new Dimension(200, 200));

        JPanel kernelGridPanel = new JPanel(new GridLayout(3, 3, 5, 5));
        kernelFields = new JTextField[3][3];
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                JTextField field = new JTextField("1", 2);
                field.setHorizontalAlignment(JTextField.CENTER);
                kernelFields[i][j] = field;
                kernelGridPanel.add(field);
            }
        }

        JButton updateKernelButton = new JButton("Atualizar Kernel");
        updateKernelButton.addActionListener(e -> updateKernel());

        kernelPanel.add(kernelGridPanel, BorderLayout.CENTER);
        kernelPanel.add(updateKernelButton, BorderLayout.SOUTH);

        // Painel principal: imagens à esquerda, kernel à direita
        JPanel centerPanel = new JPanel(new BorderLayout(10, 10));
        centerPanel.add(imagePanel, BorderLayout.CENTER);
        centerPanel.add(kernelPanel, BorderLayout.EAST);

        // Ações dos botões
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

        add(topPanel, BorderLayout.NORTH);
        add(centerPanel, BorderLayout.CENTER);
    }

    private void updateKernel() {
        int[][] kernelValues = new int[3][3];
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                try {
                    int val = Integer.parseInt(kernelFields[i][j].getText().trim());
                    kernelValues[i][j] = (val == 0) ? 0 : 1;
                } catch (NumberFormatException e) {
                    kernelValues[i][j] = 1;
                }
                kernelFields[i][j].setText(String.valueOf(kernelValues[i][j]));
            }
        }
        currentSE = new StructuringElement(kernelValues);
        updateOperationsWithNewKernel();
        JOptionPane.showMessageDialog(this, "Kernel atualizado com sucesso!");
    }
}