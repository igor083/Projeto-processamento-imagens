package ui;

import ui.QuestionPanels.*;

import javax.swing.*;
import java.awt.*;

public class MainFrame extends JFrame {

    public MainFrame() {
        super("Processamento de Imagens - UEPB");
        build();
    }

    private void build() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setMinimumSize(new Dimension(1280, 760));
        setLocationRelativeTo(null);

        JTabbedPane tabs = new JTabbedPane();
        tabs.addTab("Questão 1", new Question1Panel());
        tabs.addTab("Questão 2", new Question2Panel());
        tabs.addTab("Questão 3", new Question3Panel());
        tabs.addTab("Questão 4", new Question4Panel());
        tabs.addTab("Questão 5", new Question5Panel());
        tabs.addTab("Questão 6", new Question6Panel());

        add(tabs, BorderLayout.CENTER);
        pack();
    }
}