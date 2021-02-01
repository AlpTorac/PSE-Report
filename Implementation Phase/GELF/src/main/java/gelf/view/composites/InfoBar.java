package gelf.view.composites;

import java.util.Map;

import gelf.view.components.Label;
import gelf.view.components.Panel;

/**
 * InfoBar
 */
public class InfoBar extends Panel {
    private Map<InfoBarID, Label> labels;
    private Map<InfoBarID, String> labelPrefix;

    //Create new InfoBar with corresponding labels
    InfoBar() {
        for(InfoBarID id : InfoBarID.values()) {
            Label label = new Label();
            label.setVisible(true);
            this.add(label);
            labels.put(id, label);
        }
        labelPrefix.put(InfoBarID.VERSION, "Version");
        labelPrefix.put(InfoBarID.VERSION, "Error");
        labelPrefix.put(InfoBarID.VERSION, "Selected");
        labelPrefix.put(InfoBarID.VERSION, "Last Action");
    }
    //Set text of specified label withing the InfoBar
    public void setText(InfoBarID id, String text) {
        //generate final label text
        String finalText = "";
        if(!text.equals("")){
            finalText = labelPrefix.get(id) + ": " + text;
        }
        //set label text
        labels.get(id).setText(finalText);
    }
}