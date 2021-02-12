package gelf.view.representation;

import gelf.view.composites.SubWindow;
import gelf.view.components.Label;
import gelf.view.components.Panel;
import gelf.model.elements.*;

import java.awt.Button;
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
import java.util.Map;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.OverlayLayout;
import javax.swing.ScrollPaneConstants;

/*
 * Displays the selected cell on the navigation panel.
 */
public class CellPanel extends Panel implements ActionListener, MouseListener, ItemListener{

    private Label imageLabel;
    private Panel mainPanel;
    private Panel lowerPanel;
    private JScrollPane scrollPane;
    private JPanel imagePanel;
    
    private HashMap<Pin, Label> buttonMap;
	private HashMap<Checkbox, InputPin> checkboxMap;
	private ArrayList<Checkbox> checkboxes;
	private JButton libButton;
	private JButton cellButton; 
	
    private Cell cell;
	private ArrayList<InputPin> inputPins;
	private ArrayList<OutputPin> outputPins;
	private int maxPins;
	private boolean pinTag;
	private ArrayList<InputPin> selectedPins;
	
	private BufferedImage cellImage;
	private CellImageGenerator imageGen;
	
	private SubWindow subwindow;
	private DataPanel dataPanel;

	/*
	 * Constructor
	 * @param element 
	 * @param subwindow
	 * @param dataPanel
	 * 
	 */
	public CellPanel(int width, int height, Element element, SubWindow subwindow, DataPanel dataPanel) {
		super(width, height);
		this.subwindow = subwindow;
		this.dataPanel = dataPanel;
		setElement(element);
		
		buttonMap = new HashMap<Pin, Label>();
		checkboxMap = new HashMap<Checkbox, InputPin>();
		checkboxes = new ArrayList<Checkbox>();
		
		mainPanel = new Panel(width, height);
		
		scrollPane = new JScrollPane(mainPanel, ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS,
				ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
		
		this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		this.setAlignmentX(Component.CENTER_ALIGNMENT);
		this.add(scrollPane);
		
		libButton.addActionListener(this);
		libButton.setSize(25, 20);
		
		cellButton.addActionListener(this);
		cellButton.setSize(35, 20);
		imageGen = new CellImageGenerator();
		
		BoxLayout innerLayout = new BoxLayout(mainPanel, BoxLayout.Y_AXIS);
		mainPanel.setLayout(innerLayout);
		
		imageLabel = new Label();
		
		mainPanel.add(libButton);
		mainPanel.add(Box.createRigidArea(new Dimension(0,5)));
		mainPanel.add(cellButton);
		mainPanel.add(Box.createRigidArea(new Dimension(0,5)));
		mainPanel.add(lowerPanel);
		scrollPane.setAlignmentX(Component.CENTER_ALIGNMENT);
		libButton.setAlignmentX(Component.CENTER_ALIGNMENT);
		mainPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
		cellButton.setAlignmentX(Component.CENTER_ALIGNMENT);
		imageLabel.setSize(120, 60);
		
		mainPanel.setBackground(new Color(0.3f, 0.3f, 0.3f));
		lowerPanel.setBackground(new Color(0.3f, 0.3f, 0.3f));
		imagePanel.setBackground(new Color(0.3f, 0.3f, 0.3f));
		
		createRepresentation();
		imageLabel.setVisible(true);
		highlightPin(element);
		
	}
	
	/*
	 * Sets the main element which will be shown in the panel.
	 * @param element Element to be set in the panel.
	 */
	public void setElement(Element element) {
		if (element instanceof Cell) {
			this.cell = (Cell) element;
			pinTag = false;
		}
		else if (element instanceof Pin) {
			Pin pin = (Pin) element;
			pinTag = true;
			this.cell = pin.getParent();
			
		}
		inputPins = cell.getInPins();
		selectedPins = new ArrayList<InputPin>();
		outputPins = cell.getOutPins();
		maxPins = (inputPins.size() < outputPins.size()) ? outputPins.size() : inputPins.size();
		libButton = new JButton(cell.getParentLibrary().getName());
		cellButton = new JButton(cell.getName());
		highlightPin(element);
	}
	
	
	/*
	 * Highlights the label of a pin if the pin is opened in the panel.
	 * @param element Pin to highlight.
	 */
	private void highlightPin(Element element) {
		if (element instanceof Pin) {
			for (InputPin input : inputPins) {
				if (input.getName().equals(element.getName())) {
					buttonMap.get(element).setBackground(new Color(100));
				}
			}
			for (OutputPin output : outputPins) {
				if (output.getName().equals(element.getName())) {
					buttonMap.get(element).setBackground(new Color(100));
				}
			}
			for (Checkbox checkbox : checkboxes) {
				checkbox.setEnabled(false);
			}
			
		}
		else {
			for (Checkbox checkbox : checkboxes) {
				checkbox.setEnabled(true);
			}
			return;
		}
	}
	
	
	/*
	 * Creates the cell image and the necessary components on the panel.
	 */
	private void createRepresentation() {
		FlowLayout layout = new FlowLayout();
		layout.setAlignment(FlowLayout.CENTER);
		layout.setVgap(0);
		layout.setHgap(0);
		lowerPanel.setLayout(layout);
		
		imagePanel = new JPanel();
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
		lowerPanel.add(leftCheckboxes);
		
		lowerPanel.add(imagePanel);
		//lowerPanel.add(rightCheckboxes);
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
				Label cellButton = new Label(inputPins.get(i).getName());
				buttonMap.put(inputPins.get(i), cellButton);
				cellButton.setForeground(Color.WHITE);
				cellButton.addMouseListener(this);
				Checkbox checkbox = new Checkbox();
				checkbox.setBackground(new Color(0.3f, 0.3f, 0.3f));
				checkbox.addItemListener(this);
				checkboxMap.put( checkbox, inputPins.get(i));
				checkboxes.add(checkbox);
				cellButton.setFont(new Font("Arial", Font.PLAIN, 10));
				checkbox.setPreferredSize(new Dimension(20, 20));
				leftButtons.add(Box.createVerticalStrut(10 / inputPins.size()));
				leftButtons.add(cellButton);
		    	leftCheckboxes.add(checkbox);
		    	leftCheckboxes.add(Box.createVerticalStrut(-3));
		    	
		    	
		    }
			imageLabel.setIcon(new ImageIcon(scaledImage));
			for(int i= 0; i < outputPins.size(); i++) {
				Label cellButton = new Label(outputPins.get(i).getName());
				cellButton.setForeground(Color.WHITE);
				cellButton.addMouseListener(this);
				buttonMap.put( outputPins.get(i), cellButton);
				cellButton.setVisible(true);
				//JCheckBox checkbox = new JCheckBox();
				//checkbox.addItemListener(this);
				//checkboxes.put(checkbox, outputPins.get(i));
				//checkbox.setPreferredSize(new Dimension(20, 20));
				//rightCheckboxes.add(Box.createVerticalStrut(-3));
			   // rightCheckboxes.add(checkbox);
				cellButton.setFont(new Font("Arial", Font.PLAIN, 10));
			    rightButtons.add(Box.createVerticalStrut(10 / outputPins.size()));
				rightButtons.add(cellButton);
					
			    }
		}
		else {
		    for(int i= 0; i < inputPins.size(); i++) {
				Label cellButton = new Label(inputPins.get(i).getName());
				cellButton.setForeground(Color.WHITE);
				cellButton.addMouseListener(this);
				buttonMap.put( inputPins.get(i), cellButton);
				Checkbox checkbox = new Checkbox();
				checkbox.setBackground(new Color(0.3f, 0.3f, 0.3f));
				checkbox.addItemListener(this);
				checkboxMap.put(checkbox, inputPins.get(i));
				cellButton.setFont(new Font("Arial", Font.PLAIN, 10));	
				checkboxes.add(checkbox);
				checkbox.setPreferredSize(new Dimension(20, 20));
				leftButtons.add(cellButton);
				leftButtons.add(Box.createVerticalStrut(3));
				leftCheckboxes.add(Box.createVerticalStrut(-4));
		    	leftCheckboxes.add(checkbox);
		    	
		    }
			imageLabel.setIcon(new ImageIcon(cellImage)); 
			for(int i= 0; i < outputPins.size(); i++) {
				Label cellButton = new Label(outputPins.get(i).getName());
				cellButton.setForeground(Color.WHITE);
				cellButton.addMouseListener(this);
				buttonMap.put( outputPins.get(i), cellButton);
				//JCheckBox checkbox = new JCheckBox();
				//checkbox.addItemListener(this);
				//checkboxes.put( checkbox, outputPins.get(i));
				//checkbox.setPreferredSize(new Dimension(20, 20));
			   // rightCheckboxes.add(checkbox);
			   // rightCheckboxes.add(Box.createVerticalStrut(-4));
			    cellButton.setFont(new Font("Arial", Font.PLAIN, 10));
			    rightButtons.add(cellButton);
			    rightButtons.add(Box.createVerticalStrut(3));
				
			 }
		}
		
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getActionCommand().equals(cell.getParentLibrary().getName())) {
			//subwindow.setElement(cell.getParentLibrary());
			dataPanel.setElement(cell.getParentLibrary());
			this.setVisible(false);
		}
		else {
			if (pinTag = true) {
				setElement(cell);
				for (InputPin input : inputPins) {
					buttonMap.get(input).setBackground(new Color(0.3f, 0.3f, 0.3f));
				}
				for (OutputPin output : outputPins) {
					buttonMap.get(output).setBackground(new Color(0.3f, 0.3f, 0.3f));
				}
				for (Checkbox checkbox : checkboxes) {
					checkbox.setEnabled(true);
				}
			}
			else {
				//subwindow.setDiagram();//cell button click when element is cell
			}
			
		}
		
	}
	

	@Override
	public void itemStateChanged(ItemEvent e) {
		if (e.getStateChange() == ItemEvent.SELECTED) {
			selectedPins.add(checkboxMap.get(e.getSource()));
		}
		else {
			selectedPins.remove(checkboxMap.get(e.getSource()));
		}
		dataPanel.updateSelectedPins(selectedPins);
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		for (InputPin input : inputPins) {
			if (input.getName().equals(e.getComponent().getName())) {
				pinTag = true;
				//subwindow.setElement(input);
				dataPanel.setElement(input);
				setElement(input);
				
			}
		}
		for (OutputPin output : outputPins) {
            if (output.getName().equals(e.getComponent().getName())) {
            	pinTag = true;
				//subwindow.setElement(output);
				dataPanel.setElement(output);
				setElement(output);
			}
		}
		
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		e.getComponent().setBackground(Color.YELLOW);
	}

	@Override
	public void mouseExited(MouseEvent e) {
		e.getComponent().setBackground(new Color(0.3f, 0.3f, 0.3f));
	}

	@Override
	public void mousePressed(MouseEvent arg0) {}

	@Override
	public void mouseReleased(MouseEvent arg0) {}

	
}
