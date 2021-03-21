package gelf.view.composites;


import java.awt.Color;
import java.util.ArrayList;

import javax.swing.BoxLayout;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;

import gelf.model.elements.Element;
import gelf.model.elements.InputPin;
import gelf.model.elements.attributes.PowerGroup;
import gelf.model.elements.attributes.TimingGroup;
import gelf.model.elements.attributes.TimingSense;
import gelf.model.elements.attributes.TimingType;
import gelf.view.components.Panel;
import gelf.view.composites.Visualizer.Attribute;

/**
 * SubWindowArea
 */
public class SubWindowArea extends Panel {
    private JScrollPane pane;
    private Panel windowPanel;
    private int maxSubWindows = 10;
    public ArrayList<SubWindow> subWindows = new ArrayList<>();
    // colors
    private Color cBackground = ColorTheme.frame;

    public SubWindowArea(int width, int height) {
        super(width, height);
        this.setBackground(cBackground);
        this.setLayout(new BoxLayout(this, BoxLayout.LINE_AXIS));
        Border margin = new EmptyBorder(3, 5, 3, 3);
        this.setBorder(margin);
        // window panel
        this.windowPanel = new Panel(100, 100);
        this.windowPanel.setBackground(cBackground);
        this.windowPanel.setLayout(new BoxLayout(this.windowPanel, BoxLayout.LINE_AXIS));
        this.windowPanel.setVisible(true);
        // pane
        this.pane = new JScrollPane(this.windowPanel, JScrollPane.VERTICAL_SCROLLBAR_NEVER,
                JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        this.pane.setBorder(null);
        this.pane.setBackground(cBackground);
        this.pane.setVisible(true);
        this.add(pane);
        // SWA
        this.revalidate();
        this.repaint();
    }

    public void addSubWindow(SubWindow window) {
        if (subWindows.size() < maxSubWindows) {
            subWindows.add(window);
            this.windowPanel.add(window);
            this.revalidate();
            this.repaint();
        } else {
            JOptionPane.showMessageDialog(this, "Cannot open Element: Maximum number of open Elements reached.");
        }
    }

    public void removeSubWindow(SubWindow window) {
        if (subWindows.contains(window)) {
            subWindows.remove(window);
            this.windowPanel.remove(window);
            this.revalidate();
            this.repaint();
        } else {
            // TODO error handling
        }
    }
    
}
