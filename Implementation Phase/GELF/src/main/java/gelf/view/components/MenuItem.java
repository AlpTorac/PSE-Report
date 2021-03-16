package gelf.view.components;

import java.awt.Color;

import javax.swing.JMenuItem;

/**
 * MenuItem
 */
public class MenuItem extends JMenuItem {
    Color cBackground = new Color(0.2f, 0.2f, 0.2f);
    Color cText = new Color(.8f, .8f, .8f);
    Color cHover = new Color(0.2f, 0.4f, 0.2f);
    Color cClick = new Color(0.2f, 0.8f, 0.2f);

    public MenuItem() {
        super();
        setup();
    }

    public MenuItem(String name) {
        super(name);
        setup();
    }

    private void setup() {
        this.setBorder(null);
        this.setOpaque(true);
        this.setForeground(this.cText);
        this.setBackground(this.cBackground);
        this.repaint();
    }
}