package gelf.view.components;

import javax.swing.JCheckBox;

import gelf.view.composites.ColorTheme;

/**
 * Checkbox
 */
public class Checkbox extends JCheckBox {
    public Checkbox(String name) {
        super(name);
        this.setBackground(ColorTheme.interactable);
        this.setForeground(ColorTheme.text);
    }
}