package gelf.view.components;

import java.awt.Color;

import javax.swing.JPanel;

/**
 * Panel
 */
public class Panel extends JPanel {
    public Panel(int width, int height) {
        super();
        this.setLayout(null);
        this.setSize(width, height);
        this.setBackground(new Color(0.3f, 0.3f, 0.3f));
    }
}