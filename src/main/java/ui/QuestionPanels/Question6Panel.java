package ui.QuestionPanels;

import ui.BaseQuestionPanel;

import javax.swing.*;
import java.awt.*;

public class Question6Panel extends BaseQuestionPanel {
    public Question6Panel() {
        JTextArea area = new JTextArea();
        area.setEditable(false);
        area.setText("Questão 6 - Transformações geométricas\n\n"
                + "Aba reservada para escala, translação, reflexão, cisalhamento e rotação.");
        add(new JScrollPane(area), BorderLayout.CENTER);
    }
}