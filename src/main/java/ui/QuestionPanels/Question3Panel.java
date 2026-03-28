package ui.QuestionPanels;

import operation.UnaryImageOperation;

import operation.impl.unary.*;
import ui.BaseQuestionPanel;

import javax.swing.*;
import java.awt.*;


import javax.swing.*;
import java.awt.*;

public class Question3Panel extends BaseQuestionPanel {

    private static final String NEGATIVO = "Negativo";
    private static final String GAMMA = "Gamma";
    private static final String LOG = "Logaritmo";
    private static final String SIGMOIDE = "ITF Sigmoide";
    private static final String FAIXA = "Faixa Dinâmica";
    private static final String LINEAR = "Transformação Linear";

    public Question3Panel() {
        build();
    }

    private void build() {
        setLayout(new BorderLayout(10, 10));

        JPanel top = new JPanel(new GridLayout(3, 1, 0, 8));

        JPanel operationPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JButton loadButton = new JButton("Carregar imagem");
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

        operationPanel.add(new JLabel("Transformação:"));
        operationPanel.add(loadButton);
        operationPanel.add(operationCombo);
        operationPanel.add(executeButton);
        operationPanel.add(saveButton);
        operationPanel.add(clearButton);

        CardLayout cardLayout = new CardLayout();
        JPanel paramsContainer = new JPanel(cardLayout);

        JPanel emptyPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        emptyPanel.add(new JLabel("Essa operação não possui parâmetros configuráveis."));

        JPanel gammaPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JTextField gammaField = new JTextField("0.5", 6);
        gammaPanel.add(new JLabel("γ:"));
        gammaPanel.add(gammaField);

        JPanel logPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JTextField logAField = new JTextField("45", 6);
        logPanel.add(new JLabel("a:"));
        logPanel.add(logAField);

        JPanel sigmoidPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JTextField sigmoidWField = new JTextField("127", 6);
        JTextField sigmoidSigmaField = new JTextField("25", 6);
        sigmoidPanel.add(new JLabel("w:"));
        sigmoidPanel.add(sigmoidWField);
        sigmoidPanel.add(new JLabel("σ:"));
        sigmoidPanel.add(sigmoidSigmaField);

        JPanel dynamicPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JTextField dynamicRangeField = new JTextField("64", 6);
        dynamicPanel.add(new JLabel("Faixa alvo:"));
        dynamicPanel.add(dynamicRangeField);

        JPanel linearPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JTextField linearAField = new JTextField("1.2", 6);
        JTextField linearBField = new JTextField("10", 6);
        linearPanel.add(new JLabel("a:"));
        linearPanel.add(linearAField);
        linearPanel.add(new JLabel("b:"));
        linearPanel.add(linearBField);

        paramsContainer.add(emptyPanel, NEGATIVO);
        paramsContainer.add(gammaPanel, GAMMA);
        paramsContainer.add(logPanel, LOG);
        paramsContainer.add(sigmoidPanel, SIGMOIDE);
        paramsContainer.add(dynamicPanel, FAIXA);
        paramsContainer.add(linearPanel, LINEAR);

        JPanel infoPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JLabel infoLabel = new JLabel("Selecione uma transformação para configurar seus parâmetros.");
        infoPanel.add(infoLabel);

        operationCombo.addActionListener(e -> {
            String selected = (String) operationCombo.getSelectedItem();
            cardLayout.show(paramsContainer, selected);
        });

        loadButton.addActionListener(e -> {
            imageA = chooseAndLoadImage("Selecione a imagem PGM");
            imagePanelA.setGrayImage(imageA);
        });

        executeButton.addActionListener(e -> {
            if (imageA == null) {
                JOptionPane.showMessageDialog(this, "Carregue uma imagem primeiro.");
                return;
            }

            try {
                String selected = (String) operationCombo.getSelectedItem();
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

                result = imageService.execute(operation, imageA);
                resultPanel.setGrayImage(result);

            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(
                        this,
                        "Verifique os valores numéricos informados.",
                        "Erro de entrada",
                        JOptionPane.ERROR_MESSAGE
                );
            } catch (IllegalArgumentException ex) {
                JOptionPane.showMessageDialog(
                        this,
                        ex.getMessage(),
                        "Valor inválido",
                        JOptionPane.ERROR_MESSAGE
                );
            }
        });

        saveButton.addActionListener(e -> saveResult());
        clearButton.addActionListener(e -> clearAllImages());

        cardLayout.show(paramsContainer, NEGATIVO);

        top.add(operationPanel);
        top.add(paramsContainer);
        top.add(infoPanel);

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