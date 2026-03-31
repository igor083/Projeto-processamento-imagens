package ui.QuestionPanels;

import operation.intensity.*;
import operation.interfaces.UnaryImageOperation;
import ui.BaseQuestionPanel;

import javax.swing.*;
import java.awt.*;

public class Question3Panel extends BaseQuestionPanel {

    // Constantes que representam os nomes das operações
    private static final String NEGATIVO = "Negativo";
    private static final String GAMMA = "Gamma";
    private static final String LOG = "Logaritmo";
    private static final String SIGMOIDE = "ITF Sigmoide";
    private static final String FAIXA = "Faixa Dinâmica";
    private static final String LINEAR = "Transformação Linear";

    public Question3Panel() {
        // Inicializa a interface
        build();
    }

    private void build() {
        // Define layout principal
        setLayout(new BorderLayout(10, 10));

        // Painel superior com 3 linhas: controles, parâmetros e info
        JPanel top = new JPanel(new GridLayout(3, 1, 0, 8));

        // Painel com controles principais
        JPanel operationPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));

        JButton loadButton = new JButton("Carregar imagem");

        // Combo com todas as transformações disponíveis
        JComboBox<String> operationCombo = new JComboBox<>(new String[]{
                NEGATIVO,
                GAMMA,
                LOG,
                SIGMOIDE,
                FAIXA,
                LINEAR
        });

        JButton executeButton = new JButton("Executar");
        JButton saveButton = new JButton("Salvar resultado");
        JButton clearButton = new JButton("Limpar Tudo");

        // Adiciona componentes no painel
        operationPanel.add(new JLabel("Transformação:"));
        operationPanel.add(loadButton);
        operationPanel.add(operationCombo);
        operationPanel.add(executeButton);
        operationPanel.add(saveButton);
        operationPanel.add(clearButton);

        // CardLayout para trocar dinamicamente os parâmetros
        CardLayout cardLayout = new CardLayout();
        JPanel paramsContainer = new JPanel(cardLayout);

        // Painel vazio (operações sem parâmetros)
        JPanel emptyPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        emptyPanel.add(new JLabel("Essa operação não possui parâmetros configuráveis."));

        // Painel de parâmetros do Gamma
        JPanel gammaPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JTextField gammaField = new JTextField("0.5", 6);
        gammaPanel.add(new JLabel("γ:"));
        gammaPanel.add(gammaField);

        // Painel de parâmetros do Log
        JPanel logPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JTextField logAField = new JTextField("45", 6);
        logPanel.add(new JLabel("a:"));
        logPanel.add(logAField);

        // Painel de parâmetros da Sigmoide
        JPanel sigmoidPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JTextField sigmoidWField = new JTextField("127", 6);
        JTextField sigmoidSigmaField = new JTextField("25", 6);
        sigmoidPanel.add(new JLabel("w:"));
        sigmoidPanel.add(sigmoidWField);
        sigmoidPanel.add(new JLabel("σ:"));
        sigmoidPanel.add(sigmoidSigmaField);

        // Painel de faixa dinâmica
        JPanel dynamicPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JTextField dynamicRangeField = new JTextField("64", 6);
        dynamicPanel.add(new JLabel("Faixa alvo:"));
        dynamicPanel.add(dynamicRangeField);

        // Painel de transformação linear
        JPanel linearPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JTextField linearAField = new JTextField("1.2", 6);
        JTextField linearBField = new JTextField("10", 6);
        linearPanel.add(new JLabel("a:"));
        linearPanel.add(linearAField);
        linearPanel.add(new JLabel("b:"));
        linearPanel.add(linearBField);

        // Associa cada painel ao nome da operação
        paramsContainer.add(emptyPanel, NEGATIVO);
        paramsContainer.add(gammaPanel, GAMMA);
        paramsContainer.add(logPanel, LOG);
        paramsContainer.add(sigmoidPanel, SIGMOIDE);
        paramsContainer.add(dynamicPanel, FAIXA);
        paramsContainer.add(linearPanel, LINEAR);

        // Painel de informação
        JPanel infoPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JLabel infoLabel = new JLabel("Selecione uma transformação para configurar seus parâmetros.");
        infoPanel.add(infoLabel);

        // Troca o painel de parâmetros conforme a operação selecionada
        operationCombo.addActionListener(e -> {
            String selected = (String) operationCombo.getSelectedItem();
            cardLayout.show(paramsContainer, selected);
        });

        loadButton.addActionListener(e -> {
            // Carrega imagem original
            imageA = chooseAndLoadImage("Selecione a imagem PGM");
            imagePanelA.setGrayImage(imageA);
        });

        executeButton.addActionListener(e -> {
            // Verifica se imagem foi carregada
            if (imageA == null) {
                JOptionPane.showMessageDialog(this, "Carregue uma imagem primeiro.");
                return;
            }

            try {
                // Recupera operação selecionada
                String selected = (String) operationCombo.getSelectedItem();

                // Cria operação com base nos parâmetros informados
                UnaryImageOperation operation = createOperation(
                        selected,
                        gammaField.getText(),
                        logAField.getText(),
                        sigmoidWField.getText(),
                        sigmoidSigmaField.getText(),
                        dynamicRangeField.getText(),
                        linearAField.getText(),
                        linearBField.getText()
                );

                // Executa operação na imagem
                result = imageService.execute(operation, imageA);

                // Mostra resultado
                resultPanel.setGrayImage(result);

            } catch (NumberFormatException ex) {
                // Erro de conversão de número
                JOptionPane.showMessageDialog(
                        this,
                        "Verifique os valores numéricos informados.",
                        "Erro de entrada",
                        JOptionPane.ERROR_MESSAGE
                );
            } catch (IllegalArgumentException ex) {
                // Erro de valor inválido
                JOptionPane.showMessageDialog(
                        this,
                        ex.getMessage(),
                        "Valor inválido",
                        JOptionPane.ERROR_MESSAGE
                );
            }
        });

        // Salvar resultado
        saveButton.addActionListener(e -> saveResult());

        // Limpar imagens
        clearButton.addActionListener(e -> clearAllImages());

        // Define estado inicial (NEGATIVO)
        cardLayout.show(paramsContainer, NEGATIVO);

        // Montagem da interface
        top.add(operationPanel);
        top.add(paramsContainer);
        top.add(infoPanel);

        // Painel central com imagem original e resultado
        JPanel center = new JPanel(new GridLayout(1, 3, 10, 10));
        center.add(createViewerPanel("Imagem Original", imagePanelA));
        center.add(createViewerPanel("Resultado", resultPanel));

        add(top, BorderLayout.NORTH);
        add(center, BorderLayout.CENTER);
    }

    private UnaryImageOperation createOperation(
            String selected,
            String gammaText,
            String logAText,
            String sigmoidWText,
            String sigmoidSigmaText,
            String dynamicRangeText,
            String linearAText,
            String linearBText
    ) {
        // Cria a operação com base na seleção do usuário
        return switch (selected) {
            case NEGATIVO -> new NegativeOperation();
            case GAMMA -> new GammaOperation(Double.parseDouble(gammaText));
            case LOG -> new LogOperation(Double.parseDouble(logAText));
            case SIGMOIDE -> new SigmoidOperation(
                    Double.parseDouble(sigmoidWText),
                    Double.parseDouble(sigmoidSigmaText)
            );
            case FAIXA -> new DynamicRangeOperation(
                    Integer.parseInt(dynamicRangeText)
            );
            case LINEAR -> new LinearTransformOperation(
                    Double.parseDouble(linearAText),
                    Double.parseDouble(linearBText)
            );
            default -> throw new IllegalArgumentException("Operação inválida.");
        };
    }
}