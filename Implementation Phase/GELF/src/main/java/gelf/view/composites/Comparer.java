package gelf.view.composites;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.util.ArrayList;

import javax.swing.BoxLayout;

import gelf.model.elements.Element;
import gelf.model.elements.Library;
import gelf.model.project.Project;
import gelf.view.components.Panel;
import gelf.view.representation.LibraryComparePanel;
import gelf.view.representation.PinComparePanel;

/**
 * Comparer
 */
public class Comparer extends ElementManipulator {
	Panel upperPanel;
	Panel lowerPanel;
	SubWindow w;
	
    public Comparer(ArrayList<Element> elements, Project p, SubWindow w,  int width, int height){
        super(elements, p, width, height);
        upperPanel = new Panel(width, height);
        this.w = w;
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        upperPanel.setLayout(new FlowLayout());
        this.add(upperPanel);
        upperPanel.setVisible(true);
        if (elements.get(0) instanceof Library) {
        	ArrayList<Library> libraries = new ArrayList<Library>();
        	for (Element element: elements) {
        		libraries.add((Library)element);
        	}
        	upperPanel.add(new LibraryComparePanel(width, height, w, libraries));
    
        }
        else {
        	upperPanel.add(new PinComparePanel(width, height, w, elements));
        }
        
        this.lowerPanel = new Panel(width, height);
		this.lowerPanel.setLayout(new BorderLayout());
		this.lowerPanel.setBackground(ColorTheme.subsection);
		this.lowerPanel.setVisible(true);
        this.setVisible(true);
        this.revalidate();
        this.repaint();
    }
    
}
