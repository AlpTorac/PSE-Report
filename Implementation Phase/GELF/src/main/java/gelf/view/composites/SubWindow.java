package gelf.view.composites;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.border.Border;

import org.w3c.dom.Text;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import gelf.model.elements.Element;
import gelf.view.components.Button;
import gelf.view.components.Label;
import gelf.view.components.MenuBar;
import gelf.view.components.Panel;

/**
 * SubWindow
 */
public class SubWindow extends Panel {
    Panel bar;
    Button close;
    Label path;
    private Color cBackground = new Color(0.23f, 0.23f, 0.23f);
    private Color cBorder = new Color(0.15f, 0.15f, 0.15f);
    SubWindow _this = this;
    //element manipulators
    public enum ManipulatorType {
        VISUALIZER,
        TEXT_EDITOR,
    }
    private ElementManipulator activeManipulator;
    private Visualizer eVisualizer;
    private TextEditor eTextEditor;

    public SubWindow(Element e, SubWindowArea parent, int width, int height) {
        super(width, height);
        //element manipulators
        this.eVisualizer = new Visualizer(e, 200, 100);
        this.eVisualizer.setVisible(true);
        this.eTextEditor = new TextEditor(e, 200, 100);
        this.eTextEditor.setVisible(true);
        this.activeManipulator = eVisualizer;
        // style
        this.setLayout(new BorderLayout());
        this.setBackground(cBackground);
        Border margin = BorderFactory.createLineBorder(cBorder, 2);
        this.setBorder(margin);
        // menu
        this.bar = new Panel(this.getWidth(), 30);
        this.bar.setLayout(new BoxLayout(this.bar, BoxLayout.LINE_AXIS));
        this.bar.setVisible(true);
        // close button
        this.close = new Button("Close");
        this.close.setVisible(true);
        this.close.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                parent.removeSubWindow(_this);
            }
        });
        //path/name label
        this.path = new Label(e.toString());
        this.path.setVisible(true);

        this.bar.add(this.close);
        this.bar.add(this.path);
        this.bar.add(Box.createHorizontalGlue());

        this.add(this.bar, BorderLayout.PAGE_START);
        this.add(this.eVisualizer, BorderLayout.CENTER);
    }
    public void setElement(Element e) {

    }
    public void setManipulatorType(ManipulatorType m) {
        switch(m) {
            case VISUALIZER:
                
            break;
            case TEXT_EDITOR:
            break;
        }
    }
}