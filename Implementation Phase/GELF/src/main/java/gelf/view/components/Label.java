package gelf.view.components;

import java.awt.Color;
import java.awt.Graphics;

import javax.swing.JLabel;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;

import gelf.view.composites.ColorTheme;

public class Label extends JLabel {
    private Color cText = ColorTheme.text;
    private Color cBackground = ColorTheme.interactable;

    public Label() {
        super();
        setup();
    }

    public Label(String text) {
        super(text);
        setup();
    }

    private void setup() {
        Border margin = new EmptyBorder(5, 7, 5, 7);
        this.setBorder(margin);
        this.setForeground(this.cText);
        this.setBackground(this.cBackground);
    }

    @Override
    protected void paintComponent(Graphics g) {
        g.setColor(this.getBackground());
        g.fillRoundRect(0, 0, this.getWidth(), this.getHeight(), 10, 10);
        super.paintComponent(g);
    }
}
