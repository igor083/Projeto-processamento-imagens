package ui.QuestionPanels;
import ui.BaseQuestionPanel;

import javax.swing.*;
import java.awt.*;

public class Question4Panel extends BaseQuestionPanel {
    public Question4Panel() {
        JTextArea area = new JTextArea();
        area.setEditable(false);
        area.setText("Questão 4 - Equalização de histograma\n\n"
                + "Aba reservada para equalização e exibição dos histogramas original e equalizado.");
        add(new JScrollPane(area), BorderLayout.CENTER);
    }
}