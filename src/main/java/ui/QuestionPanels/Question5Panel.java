package ui.QuestionPanels;

import operation.UnaryImageOperation;
import operation.impl.morphological.*;
import ui.BaseQuestionPanel;

import javax.swing.*;
import java.awt.*;
import java.util.LinkedHashMap;
import java.util.Map;

public class Question5Panel extends BaseQuestionPanel {

    // Mapa que associa nome da operação à sua implementação
    private final Map<String, UnaryImageOperation> operations = new LinkedHashMap<>();

    // Elemento estruturante atual (kernel)
    private StructuringElement currentSE = new StructuringElement();

    // Matriz de campos de texto para edição do kernel 3x3
    private JTextField[][] kernelFields;

    public Question5Panel() {
        // Registra operações
        registerOperations();

        // Monta interface
        build();
    }

    private void registerOperations() {
        // Operações básicas (sem kernel customizado)
        operations.put("Dilatação Binária", new BinaryDilationOperation());
        operations.put("Erosão Binária", new BinaryErosionOperation());
        operations.put("Abertura Binária", new BinaryOpeningOperation());
        operations.put("Fechamento Binário", new BinaryClosingOperation());

        // Operações nível de cinza
        operations.put("Dilatação Nível Cinza", new GrayDilationOperation());
        operations.put("Erosão Nível Cinza", new GrayErosionOperation());
        operations.put("Abertura Nível Cinza", new GrayOpeningOperation());
        operations.put("Fechamento Nível Cinza", new GrayClosingOperation());

        // Operações adicionais com kernel
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

    // Atualiza operações quando o kernel muda
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
        // Layout principal
        setLayout(new BorderLayout(10, 10));

        // Painel superior
        JPanel top = new JPanel(new FlowLayout(FlowLayout.LEFT));

        // Painel duplicado de topo (redundante no código original)
        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));

        JButton loadButton = new JButton("Carregar imagem");

        // Combo com operações
        JComboBox<String> opCombo = new JComboBox<>(operations.keySet().toArray(new String[0]));

        JButton executeButton = new JButton("Executar");
        JButton saveButton = new JButton("Salvar resultado");
        JButton clearButton = new JButton("Limpar Tudo");

        // Adiciona botões no topo
        top.add(loadButton);
        top.add(opCombo);
        top.add(executeButton);
        top.add(saveButton);
        top.add(clearButton);

        // Painel simples de imagens (não utilizado no final)
        JPanel center = new JPanel(new GridLayout(1, 2, 10, 10));
        center.add(createViewerPanel("Imagem Original", imagePanelA));
        center.add(createViewerPanel("Resultado", resultPanel));

        // Repetição de componentes no topPanel
        topPanel.add(loadButton);
        topPanel.add(opCombo);
        topPanel.add(executeButton);
        topPanel.add(saveButton);
        topPanel.add(clearButton);

        // Painel de imagens (lado esquerdo final)
        JPanel imagePanel = new JPanel(new GridLayout(1, 2, 10, 10));
        imagePanel.add(createViewerPanel("Imagem Original", imagePanelA));
        imagePanel.add(createViewerPanel("Resultado", resultPanel));

        // Painel do kernel (lado direito)
        JPanel kernelPanel = new JPanel(new BorderLayout(5, 5));
        kernelPanel.setBorder(BorderFactory.createTitledBorder("Elemento Estruturante (Kernel 3x3)"));
        kernelPanel.setPreferredSize(new Dimension(200, 200));

        // Grid 3x3 para edição do kernel
        JPanel kernelGridPanel = new JPanel(new GridLayout(3, 3, 5, 5));
        kernelFields = new JTextField[3][3];

        // Cria campos do kernel
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                JTextField field = new JTextField("1", 2);
                field.setHorizontalAlignment(JTextField.CENTER);
                kernelFields[i][j] = field;
                kernelGridPanel.add(field);
            }
        }

        // Botão para atualizar kernel
        JButton updateKernelButton = new JButton("Atualizar Kernel");
        updateKernelButton.addActionListener(e -> updateKernel());

        kernelPanel.add(kernelGridPanel, BorderLayout.CENTER);
        kernelPanel.add(updateKernelButton, BorderLayout.SOUTH);

        // Painel principal com imagem + kernel
        JPanel centerPanel = new JPanel(new BorderLayout(10, 10));
        centerPanel.add(imagePanel, BorderLayout.CENTER);
        centerPanel.add(kernelPanel, BorderLayout.EAST);

        // Ações
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

        // Adiciona componentes finais
        add(top, BorderLayout.NORTH);
        add(center, BorderLayout.CENTER);
        add(topPanel, BorderLayout.NORTH);
        add(centerPanel, BorderLayout.CENTER);
    }

    private void updateKernel() {
        // Cria matriz 3x3 do kernel
        int[][] kernelValues = new int[3][3];

        // Lê valores dos campos
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                try {
                    int val = Integer.parseInt(kernelFields[i][j].getText().trim());

                    // Garante valores binários (0 ou 1)
                    kernelValues[i][j] = (val == 0) ? 0 : 1;
                } catch (NumberFormatException e) {
                    // Valor inválido vira 1
                    kernelValues[i][j] = 1;
                }

                // Atualiza campo com valor corrigido
                kernelFields[i][j].setText(String.valueOf(kernelValues[i][j]));
            }
        }

        // Atualiza elemento estruturante
        currentSE = new StructuringElement(kernelValues);

        // Atualiza operações com novo kernel
        updateOperationsWithNewKernel();

        JOptionPane.showMessageDialog(this, "Kernel atualizado com sucesso!");
    }
}