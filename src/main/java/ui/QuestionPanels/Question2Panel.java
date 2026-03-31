package ui.QuestionPanels;

import model.GrayImage;
import operation.interfaces.UnaryImageOperation;
import operation.intensity.MorphOperation;
import ui.BaseQuestionPanel;

import javax.swing.*;
import java.awt.*;

public class Question2Panel extends BaseQuestionPanel {

    // Armazena a imagem da criança.
    private GrayImage childImage;

    // Armazena a imagem atual.
    private GrayImage currentImage;

    // Slider usado para controlar o valor de t do morfismo.
    private JSlider morphSlider;

    // Label que mostra o valor atual do slider e a proporção entre as imagens.
    private JLabel sliderValueLabel;

    // Botão para carregar a imagem da criança.
    private JButton loadChildButton;

    // Botão para carregar a imagem atual.
    private JButton loadCurrentButton;

    public Question2Panel() {
        // Monta toda a interface da tela.
        build();
    }

    private void build() {
        // Define o layout principal do painel.
        setLayout(new BorderLayout(10, 10));

        // Painel superior com os botões principais.
        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));

        loadChildButton = new JButton("Carregar imagem (criança)");
        loadCurrentButton = new JButton("Carregar imagem (atual)");
        JButton saveButton = new JButton("Salvar resultado");
        JButton clearButton = new JButton("Limpar Tudo");

        // Adiciona os botões no painel superior.
        topPanel.add(loadChildButton);
        topPanel.add(loadCurrentButton);
        topPanel.add(saveButton);
        topPanel.add(clearButton);

        // Painel inferior que contém o controle do morfismo.
        JPanel sliderPanel = new JPanel(new BorderLayout(10, 5));
        sliderPanel.setBorder(BorderFactory.createTitledBorder("Morfismo - Controle de Tempo"));

        // Slider horizontal com valores de 0 a 100.
        // Esse valor será convertido para o intervalo de 0.0 a 1.0.
        morphSlider = new JSlider(JSlider.HORIZONTAL, 0, 100, 0);
        morphSlider.setMajorTickSpacing(25);
        morphSlider.setMinorTickSpacing(5);
        morphSlider.setPaintTicks(true);
        morphSlider.setPaintLabels(true);

        // Texto inicial do slider:
        // t = 0 significa 100% imagem da criança e 0% imagem atual.
        sliderValueLabel = new JLabel("t = 0.00 (100% criança → 0% atual)", SwingConstants.CENTER);

        // Adiciona o slider e o texto no painel inferior.
        sliderPanel.add(morphSlider, BorderLayout.CENTER);
        sliderPanel.add(sliderValueLabel, BorderLayout.SOUTH);

        // Painel central com três visualizadores:
        // imagem da criança, imagem atual e resultado do morfismo.
        JPanel centerPanel = new JPanel(new GridLayout(1, 3, 10, 10));
        centerPanel.add(createViewerPanel("Imagem Criança", imagePanelA));
        centerPanel.add(createViewerPanel("Imagem Atual", imagePanelB));
        centerPanel.add(createViewerPanel("Morfismo (resultado)", resultPanel));

        // Organiza os painéis principais na tela.
        add(topPanel, BorderLayout.NORTH);
        add(sliderPanel, BorderLayout.SOUTH);
        add(centerPanel, BorderLayout.CENTER);

        loadChildButton.addActionListener(e -> {
            // Carrega a imagem da criança.
            childImage = chooseAndLoadImage("Selecione a imagem quando criança (formato PGM)");

            // Exibe a imagem carregada no painel A.
            imagePanelA.setGrayImage(childImage);

            // Tenta atualizar o morfismo, caso a outra imagem já tenha sido carregada.
            checkAndUpdateMorph();
        });

        loadCurrentButton.addActionListener(e -> {
            // Carrega a imagem atual.
            currentImage = chooseAndLoadImage("Selecione a imagem atual (formato PGM)");

            // Exibe a imagem carregada no painel B.
            imagePanelB.setGrayImage(currentImage);

            // Tenta atualizar o morfismo, caso a outra imagem já tenha sido carregada.
            checkAndUpdateMorph();
        });

        // Salva a imagem resultante atual.
        saveButton.addActionListener(e -> saveResult());

        clearButton.addActionListener(e -> {
            // Limpa as imagens carregadas e o resultado.
            childImage = null;
            currentImage = null;
            result = null;

            // Remove as imagens exibidas nos painéis.
            imagePanelA.setGrayImage(null);
            imagePanelB.setGrayImage(null);
            resultPanel.setGrayImage(null);

            // Retorna o slider para o valor inicial.
            morphSlider.setValue(0);

            // Atualiza o texto para o estado inicial.
            sliderValueLabel.setText("t = 0.00 (100% criança → 0% atual)");
        });

        morphSlider.addChangeListener(e -> {
            // Atualiza o resultado apenas quando o usuário termina de mover o slider.
            if (!morphSlider.getValueIsAdjusting()) {
                checkAndUpdateMorph();
            }
        });
    }

    private void checkAndUpdateMorph() {
        // Só executa o morfismo se as duas imagens já tiverem sido carregadas.
        if (childImage == null || currentImage == null) {
            return;
        }

        // Verifica se as duas imagens possuem as mesmas dimensões.
        // O morfismo depende de correspondência pixel a pixel.
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

        // Converte o valor do slider para o intervalo [0.0, 1.0].
        // Exemplo:
        // 0   -> 0.0
        // 50  -> 0.5
        // 100 -> 1.0
        double t = morphSlider.getValue() / 100.0;

        // Atualiza o texto do slider mostrando a proporção usada entre as imagens.
        sliderValueLabel.setText(String.format("t = %.2f (%.0f%% criança → %.0f%% atual)",
                t, (1-t)*100, t*100));

        try {
            // Cria a operação de morfismo.
            // A imagem atual é passada como referência, e childImage será a imagem base da execução.
            UnaryImageOperation morphOp = new MorphOperation(currentImage, t);

            // Executa o morfismo entre childImage e currentImage.
            result = imageService.execute(morphOp, childImage);

            // Exibe a imagem resultante.
            resultPanel.setGrayImage(result);
        } catch (Exception ex) {
            // Exibe mensagem de erro caso a operação falhe.
            JOptionPane.showMessageDialog(this,
                    "Erro ao aplicar morfismo: " + ex.getMessage(),
                    "Erro",
                    JOptionPane.ERROR_MESSAGE);
        }
    }
}