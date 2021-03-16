package gelf.view.components;

import javax.swing.JFrame;

/**
 * Window
 */
public class Window extends JFrame {
    // constructor
    public Window(String name, int width, int height) {
        super(name);
        this.setSize(width, height);
        this.setLayout(null);
    }
}