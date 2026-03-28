package ui;

import model.GrayImage;
import service.ImageService;

import javax.swing.*;
import java.awt.*;
import java.io.File;

public abstract class BaseQuestionPanel extends JPanel {
    protected final ImageService imageService = new ImageService();
    protected final ImagePanel imagePanelA = new ImagePanel();
    protected final ImagePanel imagePanelB = new ImagePanel();
    protected final ImagePanel resultPanel = new ImagePanel();

    protected GrayImage imageA;
    protected GrayImage imageB;
    protected GrayImage result;

    public BaseQuestionPanel() {
        setLayout(new BorderLayout(10, 10));
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
    }

    protected JPanel createViewerPanel(String title, JComponent component) {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createTitledBorder(title));
        panel.add(component, BorderLayout.CENTER);
        return panel;
    }

    protected GrayImage chooseAndLoadImage(String title) {
        JFileChooser chooser = new JFileChooser();
        chooser.setDialogTitle(title);
        int option = chooser.showOpenDialog(this);
        if (option != JFileChooser.APPROVE_OPTION) return null;
        try {
            return imageService.load(chooser.getSelectedFile());
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
            return null;
        }
    }

    protected void saveResult() {
        if (result == null) {
            JOptionPane.showMessageDialog(this, "Nenhuma imagem resultante para salvar.");
            return;
        }
        JFileChooser chooser = new JFileChooser();
        if (chooser.showSaveDialog(this) == JFileChooser.APPROVE_OPTION) {
            try {
                File file = chooser.getSelectedFile();
                imageService.save(file, result);
                JOptionPane.showMessageDialog(this, "Imagem salva com sucesso.");
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    protected void clearAllImages() {
        imageA = null;
        imageB = null;
        result = null;

        imagePanelA.setGrayImage(null);
        imagePanelB.setGrayImage(null);
        resultPanel.setGrayImage(null);
    }
}