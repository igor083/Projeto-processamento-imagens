package ui.QuestionPanels;

import operation.UnaryImageOperation;
import operation.impl.unary.transformation.*;
import ui.BaseQuestionPanel;

import javax.swing.*;
import java.awt.*;

public class Question6Panel extends BaseQuestionPanel {

    private static final String ESCALA = "Escala";
    private static final String TRANSLACAO = "Translação";
    private static final String REFLEXAO = "Reflexão";
    private static final String CISALHAMENTO = "Cisalhamento";
    private static final String ROTACAO = "Rotação";

    public Question6Panel() {
        build();
    }

    private void build() {
        JPanel top = new JPanel(new GridLayout(3, 1, 0, 8));

        JPanel operationPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JButton loadButton = new JButton("Carregar imagem");
        JComboBox<String> operationCombo = new JComboBox<>(new String[]{
                ESCALA,
                TRANSLACAO,
                REFLEXAO,
                CISALHAMENTO,
                ROTACAO
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

        JPanel scalePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JTextField scaleXField = new JTextField("1.5", 6);
        JTextField scaleYField = new JTextField("1.5", 6);
        scalePanel.add(new JLabel("sx:"));
        scalePanel.add(scaleXField);
        scalePanel.add(new JLabel("sy:"));
        scalePanel.add(scaleYField);

        JPanel translationPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JTextField translateXField = new JTextField("50", 6);
        JTextField translateYField = new JTextField("30", 6);
        translationPanel.add(new JLabel("tx:"));
        translationPanel.add(translateXField);
        translationPanel.add(new JLabel("ty:"));
        translationPanel.add(translateYField);

        JPanel reflectionPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JComboBox<String> reflectionTypeCombo = new JComboBox<>(new String[]{"X", "Y"});
        reflectionPanel.add(new JLabel("Tipo:"));
        reflectionPanel.add(reflectionTypeCombo);

        JPanel shearPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JTextField shearIterationsField = new JTextField("1", 6);
        shearPanel.add(new JLabel("Iterações:"));
        shearPanel.add(shearIterationsField);

        JPanel rotationPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JTextField angleField = new JTextField("30", 6);
        rotationPanel.add(new JLabel("Ângulo (graus):"));
        rotationPanel.add(angleField);

        paramsContainer.add(scalePanel, ESCALA);
        paramsContainer.add(translationPanel, TRANSLACAO);
        paramsContainer.add(reflectionPanel, REFLEXAO);
        paramsContainer.add(shearPanel, CISALHAMENTO);
        paramsContainer.add(rotationPanel, ROTACAO);

        JPanel infoPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        infoPanel.add(new JLabel("As transformações são aplicadas em uma imagem por vez."));

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
                UnaryImageOperation operation = createOperation(
                        (String) operationCombo.getSelectedItem(),
                        scaleXField.getText(),
                        scaleYField.getText(),
                        translateXField.getText(),
                        translateYField.getText(),
                        (String) reflectionTypeCombo.getSelectedItem(),
                        shearIterationsField.getText(),
                        angleField.getText()
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

        cardLayout.show(paramsContainer, ESCALA);

        top.add(operationPanel);
        top.add(paramsContainer);
        top.add(infoPanel);

        JPanel center = new JPanel(new GridLayout(1, 2, 10, 10));
        center.add(createViewerPanel("Imagem Original", imagePanelA));
        center.add(createViewerPanel("Resultado", resultPanel));

        add(top, BorderLayout.NORTH);
        add(center, BorderLayout.CENTER);
    }

    private UnaryImageOperation createOperation(
            String selected,
            String scaleXText,
            String scaleYText,
            String translateXText,
            String translateYText,
            String reflectionType,
            String shearIterationsText,
            String angleText
    ) {
        return switch (selected) {
            case ESCALA -> new ScaleOperation(
                    Double.parseDouble(scaleXText),
                    Double.parseDouble(scaleYText)
            );
            case TRANSLACAO -> new TranslationOperation(
                    Double.parseDouble(translateXText),
                    Double.parseDouble(translateYText)
            );
            case REFLEXAO -> new ReflectionOperation(
                    "X".equals(reflectionType) ? ReflectionOperation.Type.X : ReflectionOperation.Type.Y
            );
            case CISALHAMENTO -> new ShearOperation(
                    Integer.parseInt(shearIterationsText)
            );
            case ROTACAO -> new RotationOperation(
                    Double.parseDouble(angleText)
            );
            default -> throw new IllegalArgumentException("Transformação inválida.");
        };
    }
}