package gelf.view.components;

import javax.swing.*;
import java.awt.event.*;
import java.awt.*;
import java.util.HashMap;
import java.util.Map;
import java.util.function.BiConsumer;


/**
 * Window
 */
public class Window extends JFrame implements AutoResizing {
    private Map<Component, Resizer> componentResizers = new HashMap<>();
    private Window _this = this;
    private int width;
    private int height;

    //constructor
    public Window(String name, int width, int height){
        super(name);
        this.setSize(width, height);
        this.setLayout(null);
        this.width = width;
        this.height = height;
        this.addComponentListener(this);
    }

    //from AutoResizing
    public void setResizer(Component c, Resizer r) {
        componentResizers.put(c, r);
    };

    //from ComponentListener
    @Override
    public void componentResized(ComponentEvent e) {
        //iterate over all components and resize them
        BiConsumer<Component, Resizer> resizeAction = new BiConsumer<Component,Resizer>(){
            @Override
            public void accept(Component c, Resizer r) {
                r.resize(c, width, height, _this.getWidth(), _this.getHeight());    //resize component
                c.revalidate();
            }
        };
        componentResizers.forEach(resizeAction);
        //update size for future resizes
        width = _this.getWidth();
        height = _this.getHeight();
        this.revalidate();
    }
    @Override
    public void componentHidden(ComponentEvent e) {}
    @Override
    public void componentMoved(ComponentEvent e) {}
    @Override
    public void componentShown(ComponentEvent e) {}    
}