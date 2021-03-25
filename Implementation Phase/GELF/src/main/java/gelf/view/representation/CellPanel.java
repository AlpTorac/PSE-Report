package gelf.view.representation;

import java.awt.Checkbox;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.OverlayLayout;
import javax.swing.ScrollPaneConstants;
import javax.swing.border.LineBorder;

import gelf.model.elements.Cell;
import gelf.model.elements.Element;
import gelf.model.elements.InputPin;
import gelf.model.elements.OutputPin;
import gelf.model.elements.Pin;
import gelf.view.components.Button;
import gelf.view.components.Label;
import gelf.view.components.Panel;
import gelf.view.composites.ColorTheme;
import gelf.view.composites.SubWindow;
import gelf.view.composites.Visualizer;

/**
 * Displays the selected cell on the navigation panel.
 * @author Ege Uzhan
 */
public class CellPanel extends Panel implements MouseListener, ItemListener, ActionListener {

    private Label imageLabel;
    private Panel mainPanel;
    private JPanel lowerPanel;
    private JScrollPane scrollPane;
    private JPanel imagePanel;
    
    public HashMap<Pin, Button> buttonMap;
    public ArrayList<Button> buttonList;
	public HashMap<Checkbox, InputPin> checkboxMap;
	
	public ArrayList<Checkbox> checkboxes;
	public Button libButton;
	public Button cellButton; 
	
    private Cell cell;
	private ArrayList<InputPin> inputPins;
	private ArrayList<OutputPin> outputPins;
	private boolean pinTag;
	private Pin pin;
	private ArrayList<InputPin> selectedPins;
	
	private BufferedImage cellImage;
	private CellImageGenerator imageGen;
	
	private SubWindow subwindow;
	private DataPanel dataPanel;
	private Visualizer visualizer;
	
	private Element element;
	
	/**
	 * Initializes the panel
	 * @param width Width of the panel
	 * @param height Height of the panel
	 * @param element Element given to this panel
	 * @param subwindow Subwindow holding the panel
	 * @param dataPanel Data panel opened in the subwindow.
	 * 
	 */
	public CellPanel(int width, int height, Element element, SubWindow subwindow, Visualizer visualizer,  DataPanel dataPanel) {
		super(width, height);
		this.element = element;
		this.subwindow = subwindow;
		this.dataPanel = dataPanel;
		this.visualizer = visualizer;
		buttonList = new ArrayList<Button>();
		lowerPanel = new JPanel();
		mainPanel = new Panel(width, height);
		imageGen = new CellImageGenerator();
		imageLabel = new Label();
		imagePanel = new JPanel();
		imageLabel.setSize(150, 75);
		buttonMap = new HashMap<Pin, Button>();
		checkboxMap = new HashMap<Checkbox, InputPin>();
		checkboxes = new ArrayList<Checkbox>();
		if (element instanceof Cell) {
			this.cell = (Cell) element;
			pinTag = false;
		}
		else if (element instanceof Pin) {
			Pin newPin = (Pin) element;
			this.pin = newPin;
			pinTag = true;
			this.cell = pin.getParent();
			
		}
		inputPins = cell.getInPins();
		selectedPins = new ArrayList<InputPin>();
		outputPins = cell.getOutPins();
		libButton = new Button(cell.getParentLibrary().toString());
		cellButton = new Button(cell.getName());
		
		
		scrollPane = new JScrollPane(mainPanel, ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED,
				ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		scrollPane.setBorder(new LineBorder(ColorTheme.frame, 3));
		this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		this.setAlignmentX(Component.CENTER_ALIGNMENT);
		this.add(scrollPane);
		
		libButton.addMouseListener(this);
		libButton.addActionListener(this);
		libButton.setSize(25, 20);
		
		cellButton.addMouseListener(this);
		cellButton.addActionListener(this);
		cellButton.setSize(35, 20);
		//this.setBorder(new LineBorder(Color.BLACK));
		
		BoxLayout innerLayout = new BoxLayout(mainPanel, BoxLayout.Y_AXIS);
		mainPanel.setLayout(innerLayout);
		
		mainPanel.add(libButton);
		mainPanel.add(Box.createRigidArea(new Dimension(0,5)));
		if (cell.getInPins().size() > 5 || cell.getOutPins().size() > 3) {
			mainPanel.add(cellButton);
			mainPanel.add(Box.createRigidArea(new Dimension(0,5)));
		}
		mainPanel.add(lowerPanel);
		scrollPane.setAlignmentX(Component.CENTER_ALIGNMENT);
		libButton.setAlignmentX(Component.CENTER_ALIGNMENT);
		mainPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
		cellButton.setAlignmentX(Component.CENTER_ALIGNMENT);
		
		
		mainPanel.setBackground(new Color(0.3f, 0.3f, 0.3f));
		lowerPanel.setBackground(new Color(0.3f, 0.3f, 0.3f));
		imagePanel.setBackground(new Color(0.3f, 0.3f, 0.3f));
		createRepresentation();
		imageLabel.setVisible(true);
		highlightPin(this.element);
		
	}
	
	/**
	 * Returns the cell of the panel
	 * @return cell
	 */
	public Cell getCell() {
		return cell;
	}
	
	
	
	/**
	 * Highlights the label of a pin if the pin is opened in the panel.
	 * @param element Pin to highlight.
	 */
	private void highlightPin(Element element) {
		if (element instanceof Pin) {
			for (InputPin input : inputPins) {
				if (input.getName().equals(element.getName())) {
					buttonMap.get(element).setBackground(ColorTheme.highlight);
				}
			}
			for (OutputPin output : outputPins) {
				if (output.getName().equals(element.getName())) {
					
					buttonMap.get(element).setBackground(ColorTheme.highlight);
				}
			}
		}
		else {
			for (Checkbox checkbox : checkboxes) {
				checkbox.setEnabled(true);
			}
			return;
		}
	}
	
	
	/**
	 * Creates the cell image and the necessary components on the panel.
	 */
	private void createRepresentation() {
		FlowLayout layout = new FlowLayout();
		layout.setAlignment(FlowLayout.CENTER);
		layout.setVgap(0);
		layout.setHgap(0);
		lowerPanel.setLayout(layout);
		
		
		JPanel leftCheckboxes = new JPanel();
		JPanel rightButtons = new JPanel();
		JPanel leftButtons = new JPanel();
		
		leftButtons.setLayout(new BoxLayout(leftButtons	, BoxLayout.Y_AXIS));
		leftCheckboxes.setLayout(new BoxLayout(leftCheckboxes, BoxLayout.Y_AXIS));
		rightButtons.setLayout(new BoxLayout(rightButtons, BoxLayout.Y_AXIS));
		
		leftCheckboxes.setBackground(new Color(0.3f, 0.3f, 0.3f));
	    leftButtons.setBackground(new Color(0.3f, 0.3f, 0.3f));
	    rightButtons.setBackground(new Color(0.3f, 0.3f, 0.3f));
		
		lowerPanel.add(leftButtons);
		lowerPanel.add(Box.createHorizontalStrut(5));
		lowerPanel.add(leftCheckboxes);
		
		lowerPanel.add(imagePanel);
		lowerPanel.add(Box.createHorizontalStrut(5));
		lowerPanel.add(rightButtons);
		
		OverlayLayout overlay = new OverlayLayout(imagePanel);
		imagePanel.setLayout(overlay);
		
		cellButton.setAlignmentX( Component.CENTER_ALIGNMENT);
	    imageLabel.setAlignmentX( Component.CENTER_ALIGNMENT);
		
		cellImage = imageGen.buildCell(inputPins.size(), outputPins.size());
		Image scaledImage = cellImage.getScaledInstance(imageLabel.getWidth(), imageLabel.getHeight(), Image.SCALE_SMOOTH);
		imageLabel.setIcon(new ImageIcon(scaledImage));
		
		
		if (inputPins.size() < 6 && outputPins.size() < 4) {
			imagePanel.add(cellButton, Component.CENTER_ALIGNMENT);//
			imagePanel.add(imageLabel, Component.CENTER_ALIGNMENT);//
			for (int i = 0; i < inputPins.size(); i++) {
				Button cellButton = new Button(inputPins.get(i).getName());
				buttonMap.put(inputPins.get(i), cellButton);
				buttonList.add(cellButton);
				cellButton.setForeground(Color.WHITE);
				cellButton.addMouseListener(this);
				cellButton.addActionListener(this);
				leftButtons.add(cellButton);
				lowerPanel.add(Box.createVerticalStrut(2));
				if (!pinTag || pin instanceof OutputPin) {
					Checkbox checkbox = new Checkbox();
					checkbox.setBackground(new Color(0.3f, 0.3f, 0.3f));
					checkbox.addItemListener(this);
					checkboxMap.put( checkbox, inputPins.get(i));
					checkboxes.add(checkbox);checkbox.setPreferredSize(new Dimension(20, 20));
					leftCheckboxes.add(checkbox);
				}
				cellButton.setFont(new Font("Arial", Font.PLAIN, 10));
				
		    			    	
		    	
		    }
			imageLabel.setIcon(new ImageIcon(scaledImage));
			for(int i= 0; i < outputPins.size(); i++) {
				Button cellButton = new Button(outputPins.get(i).getName());
				cellButton.setForeground(Color.WHITE);
				cellButton.addMouseListener(this);
				cellButton.addActionListener(this);
				buttonMap.put( outputPins.get(i), cellButton);
				cellButton.setVisible(true);
				cellButton.setFont(new Font("Arial", Font.PLAIN, 10));
				rightButtons.add(cellButton);
					
			    }
		}
		else {
            imagePanel.add(Box.createVerticalStrut(5));
			imagePanel.add(cellButton, Component.CENTER_ALIGNMENT);//
			imagePanel.add(imageLabel, Component.CENTER_ALIGNMENT);//
		    for(int i= 0; i < inputPins.size(); i++) {
				Button cellButton = new Button(inputPins.get(i).getName());
				cellButton.setForeground(Color.WHITE);
				cellButton.addMouseListener(this);
				cellButton.addActionListener(this);
				leftButtons.add(cellButton);
				buttonMap.put( inputPins.get(i), cellButton);
				lowerPanel.add(Box.createVerticalStrut(2));
				if (!pinTag || pin instanceof OutputPin) {
					Checkbox checkbox = new Checkbox();
					checkbox.setBackground(new Color(0.3f, 0.3f, 0.3f));
					checkbox.addItemListener(this);
					checkboxMap.put(checkbox, inputPins.get(i));
					checkboxes.add(checkbox);
					checkbox.setPreferredSize(new Dimension(20, 20));
					leftCheckboxes.add(checkbox);
					
				}
				cellButton.setFont(new Font("Arial", Font.PLAIN, 10));	
				
		    	
		    	
		    }
			imageLabel.setIcon(new ImageIcon(cellImage)); 
			for(int i= 0; i < outputPins.size(); i++) {
				Button cellButton = new Button(outputPins.get(i).getName());
				cellButton.setForeground(Color.WHITE);
				cellButton.addMouseListener(this);
				cellButton.addActionListener(this);
				buttonMap.put( outputPins.get(i), cellButton);
			    cellButton.setFont(new Font("Arial", Font.PLAIN, 10));
			    rightButtons.add(cellButton);
				
			 }
			if (!pinTag && pin instanceof OutputPin) {
				checkboxes.get(0).setState(true);;
			}
		}
		
	}
	
	/**
	 * Returns the map of buttons.
	 * @return map
	 */
	public HashMap<Pin, Button> getButtonMap() {
		return buttonMap;
	}
	
	/**
	 * Returns the input pins
	 * @return list of input pins
	 */
	public ArrayList<InputPin> getInputPins() {
		return inputPins;
	}
	
	/**
	 * Returns output pins
	 * @return list of output pins
	 */
	public ArrayList<OutputPin> getOutputPins() {
		return outputPins;
	}
	
	/**
	 * Returns the cell button
	 * @return cell button
	 */
	public Button getCellButton() {
		return cellButton;
	}
	
	public ArrayList<InputPin> getSelectedInPins(){
		return selectedPins;
	}
	
	@Override
	public void itemStateChanged(ItemEvent e) {
		if (e.getStateChange() == ItemEvent.SELECTED) {
			selectedPins.add(checkboxMap.get(e.getSource()));
			if (pin != null && pin instanceof OutputPin) {
				for(Checkbox checkbox: checkboxes) {
					if (!checkbox.equals((Checkbox) e.getSource())) {
						checkbox.setEnabled(false);
					}
				}
				visualizer.updateDiagram(selectedPins.get(0));
				
			}
		}
		else {
			selectedPins.remove(checkboxMap.get(e.getSource()));
			if (pin != null && pin instanceof OutputPin) {
				for(Checkbox checkbox: checkboxes) {
					if (!checkbox.equals((Checkbox) e.getSource())) {
						checkbox.setEnabled(true);
					}
				}
				visualizer.updateDiagram(null);
			}
		}
		dataPanel.updateSelectedPins(selectedPins);
		
	}

	
	@Override
	public void mousePressed(MouseEvent e) {}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (((Button) e.getSource()).equals(libButton)) {
			subwindow.setElement(cell.getParentLibrary());
			this.setVisible(false);
			return;
			
		}
		else if (((Button) e.getSource()).equals(cellButton)){
			if (pinTag = true) {
				subwindow.setElement(cell);
				
				pinTag = false;
				for (InputPin input : inputPins) {
					buttonMap.get(input).setBackground(ColorTheme.interactable);
				}
				for (OutputPin output : outputPins) {
					buttonMap.get(output).setBackground(ColorTheme.interactable);
				}
				return;
			}
			else {
				//subwindow.setDiagram();//cell button click when element is cell
				return;
			}
			
		}
		for (InputPin input : inputPins) {
			if (input.getName() == (((Button)e.getSource()).getText())) {
				pinTag = true;
				subwindow.setElement(input);
				((Button)e.getSource()).setBackground(ColorTheme.interactable);
				
			}
		}
		for (OutputPin output : outputPins) {
            if (output.getName() == (((Button)e.getSource()).getText())) {
            	pinTag = true;
				subwindow.setElement(output);
				((Button)e.getSource()).setBackground(ColorTheme.interactable);
			}
		}
		
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		if (e.getSource() instanceof Button) {
			e.getComponent().setBackground(ColorTheme.hover);
		}
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		if (e.getSource() instanceof Button) {
			e.getComponent().setBackground(ColorTheme.interactable);
			if (pinTag) {
				buttonMap.get(pin).setBackground(ColorTheme.highlight);
			}
		}
		
	}

	@Override
	public void mouseClicked(MouseEvent arg0) {}

	@Override
	public void mouseReleased(MouseEvent arg0) {}

	
}
