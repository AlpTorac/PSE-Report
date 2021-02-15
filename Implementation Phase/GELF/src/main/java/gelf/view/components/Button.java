package gelf.view.components;

import javax.swing.JButton;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;

import gelf.view.composites.ColorTheme;

import java.awt.*;
import java.awt.event.*;

public class Button extends JButton implements MouseListener {
    Color cBackground = ColorTheme.interactable;
    Color cText = ColorTheme.text;
    Color cHover = ColorTheme.hover;
    Color cClick = ColorTheme.active;

    public Button(){
        super();
        this.setup();
    }
    public Button(String s){
        super(s);
        this.setup();
    }

    private void setup(){
        this.setFocusPainted(false);
        this.setContentAreaFilled(false);
        Border margin = new EmptyBorder(5, 7, 5, 7);
        this.setBorder(margin);
        this.setOpaque(true);
        this.setForeground(this.cText);
        this.setBackground(this.cBackground);
        this.addMouseListener(this);
    }

    @Override
    protected void paintComponent(Graphics g) {
        g.setColor(this.getBackground());
        g.fillRoundRect(0, 0, this.getWidth(), this.getHeight(), 10, 10);
        super.paintComponent(g);
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
