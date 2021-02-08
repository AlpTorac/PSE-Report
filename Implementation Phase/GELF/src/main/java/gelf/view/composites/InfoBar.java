package gelf.view.composites;

import java.util.HashMap;
import java.util.Map;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JPanel;
import javax.swing.GroupLayout.Alignment;
import java.awt.*;

import gelf.view.components.Label;
import gelf.view.components.Panel;
import gelf.view.components.ResizeMode;
import gelf.view.components.Resizer;

/**
 * InfoBar
 */
public class InfoBar extends Panel {
    private Map<InfoBarID, Label> labels = new HashMap<>();
    private Map<InfoBarID, String> labelPrefix = new HashMap<>();
    //colors
    private Color textColor = new Color(0.9f, 0.9f, 0.9f);
    private Color backgroundColor = new Color(0.3f, 0.3f, 0.3f);

    //Create new InfoBar with corresponding labels
    public InfoBar(int width, int height) {
        super(width, height);
        //set layout
        this.setLayout(new BoxLayout(this, BoxLayout.LINE_AXIS));
        this.revalidate();
        //set the style
        this.setBackground(backgroundColor);

        //label type names
        labelPrefix.put(InfoBarID.VERSION, "Version");
        labelPrefix.put(InfoBarID.ERROR, "Error");
        labelPrefix.put(InfoBarID.SELECTED, "Selected");
        labelPrefix.put(InfoBarID.LASTACTION, "Last Action");

        //add the labels
        for(InfoBarID id : InfoBarID.values()) {
            //generate label
            Label label = new Label("");
            label.setForeground(textColor);
            label.setVisible(true);
            label.setAlignmentY(Label.CENTER_ALIGNMENT);
            label.setMinimumSize(new Dimension(50, this.getHeight()));
            labels.put(id, label);
            this.setText(id, "");
            //add label and spacing
            this.add(Box.createRigidArea(new Dimension(5, 0)));
            this.add(label);
        }
        //add spacing to end, update
        this.add(Box.createHorizontalGlue());
        this.revalidate();
        this.repaint();
    }
    
    //Set text of specified label withing the InfoBar
    public void setText(InfoBarID id, String text) {
        //generate final label text
        String finalText = labelPrefix.get(id) + ": ";
        if(!text.equals("")){
            finalText += text;
        } else {
            finalText += "none";
        }
        //set label text
        labels.get(id).setText(finalText);
        this.repaint();
    }
}