package gelf.view.composites;

import java.awt.Color;
import java.util.ArrayList;

import javax.swing.BoxLayout;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;

import gelf.view.components.Panel;

/**
 * SubWindowArea
 */
public class SubWindowArea extends Panel {
    private int maxSubWindows = 3;
    private ArrayList<SubWindow> subWindows = new ArrayList<>();
    //colors
    private Color cBackground = new Color(0.15f, 0.15f, 0.15f);

    public SubWindowArea(int width, int height) {
        super(width, height);
        this.setLayout(new BoxLayout(this, BoxLayout.LINE_AXIS));
        this.setBackground(cBackground);
        Border margin = new EmptyBorder(5, 5, 5, 5);
        this.setBorder(margin);
    }
    
    public void addSubWindow(SubWindow window) {
        if(subWindows.size() < maxSubWindows) {
            subWindows.add(window);
            this.add(window);
            this.revalidate();
            this.repaint();
        } else {
            //TODO error handling
        }
    }

    public void removeSubWindow(SubWindow window) {
        if(subWindows.contains(window)) {
            subWindows.remove(window);
            this.remove(window);
            this.revalidate();
            this.repaint();
        } else {
            //TODO error handling
        }
    }
}