package gelf.view.components;

import javax.swing.JMenuItem;
import java.awt.*;
import java.awt.event.*;

/**
 * MenuItem
 */
public class MenuItem extends JMenuItem implements MouseListener {
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

    private void setup(){
        this.setFocusPainted(false);
        this.setContentAreaFilled(false);
        this.setBorder(null);
        this.setOpaque(true);
        this.setForeground(this.cText);
        this.setBackground(this.cBackground);
        this.addMouseListener(this);
        this.repaint();
    }

    @Override
    public void mouseClicked(MouseEvent e) {
    }

    @Override
    public void mousePressed(MouseEvent e) {
        if(e.getSource() == this) {
            this.setBackground(this.cClick);
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        if(e.getSource() == this) {
            this.setBackground(this.cHover);
        }
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        if(e.getSource() == this) {
            this.setBackground(this.cHover);
        }
    }

    @Override
    public void mouseExited(MouseEvent e) {
        if(e.getSource() == this) {
            this.setBackground(this.cBackground);
        }
    }
}