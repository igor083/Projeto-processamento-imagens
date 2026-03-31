package ui.QuestionPanels;

import operation.binary.*;
import operation.convolution.*;
import operation.interfaces.BinaryImageOperation;
import operation.interfaces.UnaryImageOperation;
import ui.BaseQuestionPanel;

import javax.swing.*;
import java.awt.*;
import java.util.LinkedHashMap;
import java.util.Map;

public class Question1Panel extends BaseQuestionPanel {

    // Armazena as operações que usam apenas uma imagem.
    // A chave é o nome que aparece na interface, e o valor é a implementação da operação.
    private final Map<String, UnaryImageOperation> unaryOps = new LinkedHashMap<>();

    // Armazena as operações que usam duas imagens.
    // A chave é o nome exibido na interface, e o valor é a operação correspondente.
    private final Map<String, BinaryImageOperation> binaryOps = new LinkedHashMap<>();

    public Question1Panel() {
        // Registra todas as operações disponíveis.
        registerOperations();

        // Monta os componentes visuais da tela.
        build();
    }

    private void registerOperations() {
        // Cadastro das operações unárias: filtros e realces aplicados sobre uma única imagem.
        unaryOps.put("Filtro da Média 3x3", new MeanFilterOperation());
        unaryOps.put("Filtro da Mediana 3x3", new MedianFilterOperation());
        unaryOps.put("Passa-Alta Básico", new BasicHighPassOperation());
        unaryOps.put("Roberts", new RobertsOperation());
        unaryOps.put("Roberts Cruzado", new RobertsCrossOperation());
        unaryOps.put("Prewitt", new PrewittOperation());
        unaryOps.put("Sobel", new SobelOperation());
        unaryOps.put("High-Boost (A=1.2)", new HighBoostOperation(1.2));

        // Cadastro das operações binárias: operações entre duas imagens.
        binaryOps.put("Soma", new AddOperation());
        binaryOps.put("Subtração", new SubtractOperation());
        binaryOps.put("Multiplicação", new MultiplyOperation());
        binaryOps.put("Divisão", new DivideOperation());
        binaryOps.put("AND", new AndOperation());
        binaryOps.put("OR", new OrOperation());
        binaryOps.put("XOR", new XorOperation());
    }

    private void build() {
        // Painel superior que será dividido em duas linhas:
        // uma para operações unárias e outra para binárias.
        JPanel top = new JPanel(new GridLayout(2, 1, 0, 8));

        // Painel da primeira linha: operações com uma única imagem.
        JPanel unaryPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));

        // Botão para carregar a imagem que será usada na operação unária.
        JButton loadA1 = new JButton("Carregar imagem");

        // Lista suspensa com os nomes das operações unárias cadastradas.
        JComboBox<String> unaryCombo = new JComboBox<>(unaryOps.keySet().toArray(new String[0]));

        // Botão para executar o filtro ou realce selecionado.
        JButton runUnary = new JButton("Executar filtro/realce");

        // Botão para salvar a imagem resultante.
        JButton save = new JButton("Salvar resultado");

        loadA1.addActionListener(e -> {
            // Abre o seletor de arquivo e carrega a imagem escolhida em imageA.
            imageA = chooseAndLoadImage("Selecione a imagem PGM");

            // Exibe a imagem carregada no painel da imagem A.
            imagePanelA.setGrayImage(imageA);
        });

        runUnary.addActionListener(e -> {
            // Garante que uma imagem foi carregada antes de executar a operação.
            if (imageA == null) {
                JOptionPane.showMessageDialog(this, "Carregue uma imagem primeiro.");
                return;
            }

            // Recupera a operação selecionada no combo.
            UnaryImageOperation op = unaryOps.get(unaryCombo.getSelectedItem());

            // Executa a operação sobre a imagem A e guarda o resultado.
            result = imageService.execute(op, imageA);

            // Exibe o resultado no painel de saída.
            resultPanel.setGrayImage(result);
        });

        save.addActionListener(e -> saveResult());

        // Adiciona os componentes no painel de operações unárias.
        unaryPanel.add(new JLabel("Filtros/Realce:"));
        unaryPanel.add(loadA1);
        unaryPanel.add(unaryCombo);
        unaryPanel.add(runUnary);
        unaryPanel.add(save);

        // Painel da segunda linha: operações entre duas imagens.
        JPanel binaryPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));

        // Botão para carregar a primeira imagem.
        JButton loadA2 = new JButton("Imagem A");

        // Botão para carregar a segunda imagem.
        JButton loadB2 = new JButton("Imagem B");

        // Lista suspensa com as operações binárias disponíveis.
        JComboBox<String> binaryCombo = new JComboBox<>(binaryOps.keySet().toArray(new String[0]));

        // Botão para executar a operação entre as duas imagens.
        JButton runBinary = new JButton("Executar operação binária");

        // Botão para limpar todas as imagens exibidas.
        JButton clearButton = new JButton("Limpar Tudo");

        loadA2.addActionListener(e -> {
            // Carrega a imagem A usada nas operações binárias.
            imageA = chooseAndLoadImage("Selecione a imagem A");

            // Mostra a imagem no painel correspondente.
            imagePanelA.setGrayImage(imageA);
        });

        loadB2.addActionListener(e -> {
            // Carrega a imagem B usada nas operações binárias.
            imageB = chooseAndLoadImage("Selecione a imagem B");

            // Mostra a imagem no painel correspondente.
            imagePanelB.setGrayImage(imageB);
        });

        runBinary.addActionListener(e -> {
            // Verifica se as duas imagens foram carregadas.
            if (imageA == null || imageB == null) {
                JOptionPane.showMessageDialog(this, "Carregue as duas imagens.");
                return;
            }

            // Recupera a operação binária escolhida.
            BinaryImageOperation op = binaryOps.get(binaryCombo.getSelectedItem());

            // Executa a operação entre imageA e imageB.
            result = imageService.execute(op, imageA, imageB);

            // Exibe a imagem gerada.
            resultPanel.setGrayImage(result);
        });

        clearButton.addActionListener(e -> clearAllImages());

        // Adiciona os componentes no painel de operações binárias.
        binaryPanel.add(new JLabel("Aritméticas/Lógicas:"));
        binaryPanel.add(loadA2);
        binaryPanel.add(loadB2);
        binaryPanel.add(binaryCombo);
        binaryPanel.add(runBinary);
        binaryPanel.add(clearButton);

        // Adiciona os dois painéis no painel superior.
        top.add(unaryPanel);
        top.add(binaryPanel);

        // Painel central com três áreas de visualização:
        // imagem A, imagem B e resultado.
        JPanel center = new JPanel(new GridLayout(1, 3, 10, 10));
        center.add(createViewerPanel("Imagem A", imagePanelA));
        center.add(createViewerPanel("Imagem B", imagePanelB));
        center.add(createViewerPanel("Resultado", resultPanel));

        // Adiciona a parte de controles no topo da tela.
        add(top, BorderLayout.NORTH);

        // Adiciona a área de visualização no centro da tela.
        add(center, BorderLayout.CENTER);
    }
}