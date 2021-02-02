package gelf.view.components;

import java.awt.Color;

import javax.swing.JLabel;

/**
 * Label
 */
public class Label extends JLabel {
    public Label() {
        super();
        this.setBackground(Color.green);
    }
    public Label(String s){
        super(s);
        this.setBackground(Color.green);
    }
}