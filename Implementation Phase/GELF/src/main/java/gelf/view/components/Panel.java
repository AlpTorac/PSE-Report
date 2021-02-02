package gelf.view.components;

import javax.swing.JPanel;
import java.awt.*;
import java.awt.event.ComponentEvent;
import java.util.*;
import java.util.function.BiConsumer;

/**
 * Panel
 */
public class Panel extends JPanel implements AutoResizing {
    private Map<Component, Resizer> componentResizers = new HashMap<>();
    private Panel _this = this;
    private int width;
    private int height;

    public Panel(int width, int height) {
        super();
        this.setLayout(null);
        this.setSize(width, height);
        this.width = width;
        this.height = height;
        this.setBackground(Color.blue);
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
                c.repaint();
            }
        };
        componentResizers.forEach(resizeAction);
        //update size for future resizes
        width = _this.getWidth();
        height = _this.getHeight();
        this.repaint();
    }
    @Override
    public void componentHidden(ComponentEvent e) {}
    @Override
    public void componentMoved(ComponentEvent e) {}
    @Override
    public void componentShown(ComponentEvent e) {}    
}