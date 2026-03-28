package ui.QuestionPanels;
import ui.BaseQuestionPanel;

import javax.swing.*;
import java.awt.*;

public class Question5Panel extends BaseQuestionPanel {
    public Question5Panel() {
        JTextArea area = new JTextArea();
        area.setEditable(false);
        area.setText("Questão 5 - Operadores morfológicos\n\n"
                + "Não é possível concluir ainda com segurança, porque o enunciado remete aos operadores definidos na aula 10, mas a aula 10 não foi anexada.\n"
                + "Falta a lista exata dos operadores morfológicos binários e em níveis de cinza cobrados.");
        add(new JScrollPane(area), BorderLayout.CENTER);
    }
}