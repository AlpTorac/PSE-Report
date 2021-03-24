package gelf.view.representation;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Label;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.BoxLayout;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;
import javax.swing.border.LineBorder;

import gelf.model.elements.Library;
import gelf.view.components.Button;
import gelf.view.components.Panel;
import gelf.view.composites.Comparer;
import gelf.view.composites.SubWindow;
import gelf.view.diagrams.SettingsProvider;

/**
 * Comparison panel for libraries.
 * @author Ege Uzhan
 *
 */
public class LibraryComparePanel extends Panel implements MouseListener{
	
	public HashMap<Button, Library> buttons;
	public ArrayList<Button> buttonList;
    private JPanel listPanel;
    private ArrayList<Library> selectedLibraries;
	private SubWindow subwindow;
    private JScrollPane scrollPane;
    private Comparer c;
    private HashMap<Button, Color> colorMap;
   
    /**
     * Initializes the panel.
     * @param library To be opened library.
     */
    public LibraryComparePanel(int width, int height, SubWindow subwindow, Comparer c, ArrayList<Library> libraries) {
    	super(width, height);
    	this.subwindow = subwindow;
    	selectedLibraries = libraries;
    	this.c = c;
    	this.colorMap = new HashMap<Button, Color>();
    	buttonList = new ArrayList<Button>();
    	this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
    	this.setBorder(new LineBorder(Color.BLACK));
        buttons = new HashMap<Button,Library>();
        listPanel = new JPanel(); 
        listPanel.setLayout(new GridLayout(0,2));
        listPanel.setBackground(new Color(0.3f, 0.3f, 0.3f));
        scrollPane = new JScrollPane(listPanel, ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED,
        		ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        this.add(scrollPane);
        scrollPane.setAlignmentX(Component.CENTER_ALIGNMENT);
        listPanel.setSize(width, height);
        boolean painted = false;
        Color color1 = SettingsProvider.getInstance().getValueDisplayComponentColorAt(0);
    	Color color2 = SettingsProvider.getInstance().getValueDisplayComponentColorAt(1);
         
        for (int i = 0; i < libraries.size(); i++) {
        	Button label = new Button();
        	label.setText(libraries.get(i).getName());
        	label.setFont(new Font("Arial", Font.PLAIN, 12));
        	buttonList.add(label);
        	label.setForeground(Color.WHITE);
        	listPanel.add(label);
        	label.addMouseListener(this);
        	buttons.put(label, libraries.get(i));
        	
        	if (!painted) {
				buttonList.get(i).setBackground(color1);
				painted = true;
				colorMap.put(buttonList.get(i), color1);
			} else {
				buttonList.get(i).setBackground(color2);
				colorMap.put(buttonList.get(i), color2);
			}
        }
        this.setVisible(true);

    }
    

	@Override
	public void mouseClicked(MouseEvent e) {}

	@Override
	public void mouseEntered(MouseEvent e) {}

	@Override
	public void mouseExited(MouseEvent e) {
		if (colorMap.containsKey((Button) e.getSource()))  {
			((Button) e.getSource()).setBackground(colorMap.get(((Button) e.getSource())));
		}
		
	}

	@Override
	public void mousePressed(MouseEvent e) {}

	@Override
	public void mouseReleased(MouseEvent e) {}
	
}

