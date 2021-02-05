package gelf.view.components;

import javax.swing.JButton;
import java.awt.*;
import java.awt.event.*;

public class Button extends JButton implements MouseListener {
    Color cBackground = new Color(0.2f, 0.2f, 0.2f);
    Color cText = new Color(.8f, .8f, .8f);
    Color cHover = new Color(0.2f, 0.4f, 0.2f);
    Color cClick = new Color(0.2f, 0.8f, 0.2f);

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
        this.setBorder(null);
        this.setOpaque(true);
        this.setForeground(this.cText);
        this.setBackground(this.cBackground);
        this.addMouseListener(this);
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
