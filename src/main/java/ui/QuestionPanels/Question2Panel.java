package ui.QuestionPanels;

import ui.BaseQuestionPanel;

import javax.swing.*;
import java.awt.*;
public class Question2Panel extends BaseQuestionPanel {
    public Question2Panel() {
        JTextArea area = new JTextArea();
        area.setEditable(false);
        area.setText("Questão 2 - Morfismo dependente do tempo\n\n"
                + "Implementação pendente de duas imagens do usuário em formato PGM: uma da infância e uma atual.\n"
                + "Sem essas imagens, não é possível concluir a funcionalidade corretamente.");
        add(new JScrollPane(area), BorderLayout.CENTER);
    }
}