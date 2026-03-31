package ui.QuestionPanels;

import operation.UnaryImageOperation;
import operation.impl.morphological.*;
import ui.BaseQuestionPanel;

import javax.swing.*;
import java.awt.*;
import java.util.LinkedHashMap;
import java.util.Map;

public class Question5Panel extends BaseQuestionPanel {

    // Mapa que associa o nome da operação com sua implementação
    private final Map<String, UnaryImageOperation> operations = new LinkedHashMap<>();

    public Question5Panel() {
        // Registra todas as operações morfológicas disponíveis
        registerOperations();

        // Monta a interface da tela
        build();
    }

    private void registerOperations() {
        // Operações morfológicas para imagens binárias
        operations.put("Dilatação Binária", new BinaryDilationOperation());
        operations.put("Erosão Binária", new BinaryErosionOperation());
        operations.put("Abertura Binária", new BinaryOpeningOperation());
        operations.put("Fechamento Binário", new BinaryClosingOperation());

        // Operações morfológicas para imagens em nível de cinza
        operations.put("Dilatação Nível Cinza", new GrayDilationOperation());
        operations.put("Erosão Nível Cinza", new GrayErosionOperation());
        operations.put("Abertura Nível Cinza", new GrayOpeningOperation());
        operations.put("Fechamento Nível Cinza", new GrayClosingOperation());
    }

    private void build() {
        // Define layout principal
        setLayout(new BorderLayout(10, 10));

        // Painel superior com controles
        JPanel top = new JPanel(new FlowLayout(FlowLayout.LEFT));

        JButton loadButton = new JButton("Carregar imagem");

        // Combo com todas as operações cadastradas
        JComboBox<String> opCombo = new JComboBox<>(operations.keySet().toArray(new String[0]));

        JButton executeButton = new JButton("Executar");
        JButton saveButton = new JButton("Salvar resultado");
        JButton clearButton = new JButton("Limpar Tudo");

        // Adiciona componentes no topo
        top.add(loadButton);
        top.add(opCombo);
        top.add(executeButton);
        top.add(saveButton);
        top.add(clearButton);

        // Painel central com imagem original e resultado
        JPanel center = new JPanel(new GridLayout(1, 2, 10, 10));
        center.add(createViewerPanel("Imagem Original", imagePanelA));
        center.add(createViewerPanel("Resultado", resultPanel));

        loadButton.addActionListener(e -> {
            // Carrega a imagem original
            imageA = chooseAndLoadImage("Selecione a imagem PGM");

            // Exibe no painel
            imagePanelA.setGrayImage(imageA);
        });

        executeButton.addActionListener(e -> {
            // Verifica se a imagem foi carregada
            if (imageA == null) {
                JOptionPane.showMessageDialog(this, "Carregue uma imagem primeiro.");
                return;
            }

            // Recupera operação selecionada no combo
            UnaryImageOperation op = operations.get(opCombo.getSelectedItem());

            // Executa a operação morfológica
            result = imageService.execute(op, imageA);

            // Exibe o resultado
            resultPanel.setGrayImage(result);
        });

        // Salva imagem resultante
        saveButton.addActionListener(e -> saveResult());

        // Limpa imagens e resultado
        clearButton.addActionListener(e -> clearAllImages());

        // Adiciona componentes na tela
        add(top, BorderLayout.NORTH);
        add(center, BorderLayout.CENTER);
    }
}