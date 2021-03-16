package gelf.view.components;

import java.awt.Color;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JMenu;
import javax.swing.event.MenuEvent;
import javax.swing.event.MenuListener;

import gelf.view.composites.ColorTheme;

public class Menu extends JMenu implements MouseListener, MenuListener {
    Color cBackground = new Color(0.2f, 0.2f, 0.2f);
    Color cText = new Color(.8f, .8f, .8f);
    Color cHover = ColorTheme.hover;
    Color cClick = ColorTheme.active;

    public Menu() {
        super();
        setup();
    }

    public Menu(String name) {
        super(name);
        setup();
    }

    private void setup() {
        this.setFocusPainted(false);
        this.setContentAreaFilled(false);
        this.setBorder(null);
        this.setOpaque(true);
        this.setForeground(this.cText);
        this.setBackground(this.cBackground);
        this.addMouseListener(this);
        this.addMenuListener(this);
        this.repaint();
    }

    @Override
    public void mouseClicked(MouseEvent e) {
    }

    @Override
    public void mousePressed(MouseEvent e) {
        if (e.getSource() == this) {
            this.setBackground(this.cClick);
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        if (e.getSource() == this) {
            this.setBackground(this.cHover);
        }
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        if (e.getSource() == this) {
            this.setBackground(this.cHover);
        }
    }

    @Override
    public void mouseExited(MouseEvent e) {
        if (e.getSource() == this) {
            this.setBackground(this.cBackground);
        }
    }

    @Override
    public void menuSelected(MenuEvent e) {
        if (e.getSource() == this) {
            this.setBackground(this.cClick);
        }
    }

    @Override
    public void menuDeselected(MenuEvent e) {
        if (e.getSource() == this) {
            this.setBackground(this.cBackground);
        }
    }

    @Override
    public void menuCanceled(MenuEvent e) {
        if (e.getSource() == this) {
            this.setBackground(this.cBackground);
        }
    }
}
