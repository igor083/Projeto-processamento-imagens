package ui.QuestionPanels;

import operation.BinaryImageOperation;
import operation.UnaryImageOperation;
import operation.impl.*;
import operation.impl.binary.*;
import ui.BaseQuestionPanel;

import javax.swing.*;
import java.awt.*;
import java.util.LinkedHashMap;
import java.util.Map;
public class Question3Panel extends BaseQuestionPanel {
    public Question3Panel() {
        JTextArea area = new JTextArea();
        area.setEditable(false);
        area.setText("Questão 3 - Transformações de intensidade\n\n"
                + "Aba reservada para: negativo, gamma, log, ITF sigmoide, faixa dinâmica e transformação linear.");
        add(new JScrollPane(area), BorderLayout.CENTER);
    }
}