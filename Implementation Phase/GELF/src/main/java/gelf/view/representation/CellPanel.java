package gelf.view.representation;
import gelf.view.composites.Panel;
import gelf.model.elements.*;

import java.awt.Button;
import java.awt.Checkbox;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Image;
import java.awt.Label;
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
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;

/*
 * Displays the selected cell on the navigation panel.
 */
public class CellPanel extends JPanel implements ActionListener, MouseListener, ItemListener, Resizable{

    private Label imageLabel;
    private Panel mainPanel;
    private Panel lowerPanel;
    private JScrollPane scrollPane;
    
    private HashMap<Pin, Label> buttons;
	private HashMap<Checkbox, Pin> checkboxes;
	private Button libButton;
	private Button cellButton; 
	
    private Cell cell;
	private ArrayList<InputPin> inputPins;
	private ArrayList<OutputPin> outputPins;
	private int maxPins;
	private Pin openedPin;
	private ArrayList<Pin> selectedPins;
	
	private BufferedImage cellImage;
	private CellImageGenerator imageGen;
	
	private LibraryPanel libraryPanel;
	private DataPanel dataPanel;

	/*
	 * Constructor
	 * @param cell Selected cell.
	 */
	public CellPanel(Cell cell) {
		setCell(cell);
		buttons = new HashMap<Pin, Label>();
		checkboxes = new HashMap<Checkbox, Pin>();
		mainPanel = new Panel();
		lowerPanel = new Panel();
		scrollPane = new JScrollPane(mainPanel, ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS,
				ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
		libButton = new Button(cell.getParentLibrary().getName());
		libButton.addActionListener(this);
		libButton.setSize(25, 20);
		cellButton = new Button(cell.getName());
		cellButton.addActionListener(this);
		cellButton.setSize(35, 20);
		imageGen = new CellImageGenerator();
		
		
		this.setSize(new Dimension(300, 300));
		this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		BoxLayout innerLayout = new BoxLayout(mainPanel, BoxLayout.Y_AXIS);
		mainPanel.setLayout(innerLayout);
		
		this.setAlignmentX(Component.CENTER_ALIGNMENT);
		this.add(scrollPane);
		
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
		mainPanel.setPreferredSize(new Dimension(300, 300));
		lowerPanel.setPreferredSize(new Dimension(300, (maxPins <= 5) ? 300 : 25 * maxPins));
		imageLabel.setSize(150, 75);
		
		createRepresentation();
		imageLabel.setVisible(true);
		
		//add default diagram calls
	
	}
	
	/*
	 * Constructor 
	 * @param pin Selected pin.
	 */
	public CellPanel(Pin pin) {
		this(pin.getParent());
		openedPin = pin;
		for (InputPin input : inputPins) {
			if (input.getName().equals(pin.getName())) {
				buttons.get(pin).setBackground(new Color(100));
			}
		}
		for (OutputPin output : outputPins) {
			if (output.getName().equals(pin.getName())) {
				buttons.get(pin).setBackground(new Color(100));
			}
		}
		//add default pin diagram calls
		
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
		
		JPanel leftCheckboxes = new JPanel();
		JPanel rightCheckboxes = new JPanel();
		JPanel rightButtons = new JPanel();
		JPanel leftButtons = new JPanel();
		
		leftButtons.setLayout(new BoxLayout(leftButtons	, BoxLayout.Y_AXIS));
		leftCheckboxes.setLayout(new BoxLayout(leftCheckboxes, BoxLayout.Y_AXIS));
		rightCheckboxes.setLayout(new BoxLayout(rightCheckboxes, BoxLayout.Y_AXIS));
		rightButtons.setLayout(new BoxLayout(rightButtons, BoxLayout.Y_AXIS));
		
		lowerPanel.add(leftButtons);
		lowerPanel.add(leftCheckboxes);
		lowerPanel.add(imageLabel);
		lowerPanel.add(rightCheckboxes);
		lowerPanel.add(rightButtons);
		
		cellImage = imageGen.buildCell(inputPins.size(), outputPins.size());
		Image scaledImage = cellImage.getScaledInstance(imageLabel.getWidth(), imageLabel.getHeight(), Image.SCALE_SMOOTH);
		imageLabel.setIcon(new ImageIcon(scaledImage));
		
		
		if (inputPins.size() < 6 && outputPins.size() < 4) {
			
			
			for (int i = 0; i < inputPins.size(); i++) {
				JLabel cellButton = new JLabel(inputPins.get(i).getName());
				buttons.put( inputPins.get(i), cellButton);
				cellButton.addMouseListener(this);
				Checkbox checkbox = new JCheckBox();
				checkbox.addItemListener(this);
				checkboxes.put( checkbox, inputPins.get(i));
				cellButton.setFont(new Font("Arial", Font.PLAIN, 10));
				checkbox.setPreferredSize(new Dimension(20, 20));
				leftButtons.add(Box.createVerticalStrut(10 / inputPins.size()));
				leftButtons.add(cellButton);
				
		    	leftCheckboxes.add(checkbox);
		    	leftCheckboxes.add(Box.createVerticalStrut(-3));
		    	
		    	
		    }
			imageLabel.setIcon(new ImageIcon(scaledImage));
			 for(int i= 0; i < outputPins.size(); i++) {
				 JLabel cellButton = new JLabel();
				 cellButton.addMouseListener(this);
				 buttons.put( outputPins.get(i), cellButton);
				 cellButton.setVisible(true);
					JCheckBox checkbox = new JCheckBox();
					checkbox.addItemListener(this);
					checkboxes.put(checkbox, outputPins.get(i));
					cellButton.setFont(new Font("Arial", Font.PLAIN, 10));
					
					checkbox.setPreferredSize(new Dimension(20, 20));
					rightCheckboxes.add(Box.createVerticalStrut(-3));
			    	rightCheckboxes.add(checkbox);
			    	
			    	
			    	rightButtons.add(Box.createVerticalStrut(10 / outputPins.size()));
					rightButtons.add(cellButton);
					
			    }
		}
		else {
			
			for(int i= 0; i < inputPins.size(); i++) {
				JLabel cellButton = new JLabel();
				cellButton.addMouseListener(this);
				buttons.put( inputPins.get(i), cellButton);
				JCheckBox checkbox = new JCheckBox();
				checkbox.addItemListener(this);
				checkboxes.put(checkbox, inputPins.get(i));
				cellButton.setFont(new Font("Arial", Font.PLAIN, 10));				
				checkbox.setPreferredSize(new Dimension(20, 20));
				
				leftButtons.add(cellButton);
				leftButtons.add(Box.createVerticalStrut(3));
				leftCheckboxes.add(Box.createVerticalStrut(-4));
		    	leftCheckboxes.add(checkbox);
		    	
		    }
			imageLabel.setIcon(new ImageIcon(cellImage));
			 
			for(int i= 0; i < outputPins.size(); i++) {
				 JLabel cellButton = new JLabel();
				 cellButton.addMouseListener(this);
				 buttons.put( outputPins.get(i), cellButton);
				
					JCheckBox checkbox = new JCheckBox();
					checkbox.addItemListener(this);
					checkboxes.put( checkbox, outputPins.get(i));
					cellButton.setFont(new Font("Arial", Font.PLAIN, 10));
					
					checkbox.setPreferredSize(new Dimension(20, 20));
					
			    	rightCheckboxes.add(checkbox);
			    	rightCheckboxes.add(Box.createVerticalStrut(-4));
			    	rightButtons.add(cellButton);
			    	rightButtons.add(Box.createVerticalStrut(3));
				
			 }
		}
		
	}
	
	/*
	 * Switches the navigation panel from a pin to a cell.
	 * @param cell Selected cell.
	 */
	public void switchToCell(Cell cell) {
		 //todo
		
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getActionCommand().equals(cell.getParentLibrary().getName())) {
			//open parent library in vis
		}
		else {
			if (openedPin != null) {
				for (InputPin input : inputPins) {
					buttons.get(input).setBackground(new Color(0));
				}
				for (OutputPin output : outputPins) {
					buttons.get(output).setBackground(new Color(0));
				}
				// diagram action
			}
			else {
				// diagram action
			}
			
		}
		
	}
	

	@Override
	public void itemStateChanged(ItemEvent e) {
		if (selectedPins.contains(checkboxes.get(e.getItemSelectable()))) {
			selectedPins.remove(checkboxes.get(e.getItemSelectable()));
		}
		else {
            selectedPins.add(checkboxes.get(e.getItemSelectable()));
			
		}
		// pin diagram & data panel action		
		
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		//to be opened pin 
		for (InputPin input : inputPins) {
			if (input.getName().equals(e.getComponent().getName())) {
				
			}
			
		}
		for (OutputPin output : outputPins) {
            if (output.getName().equals(e.getComponent().getName())) {
				
			}
			
		}
		
		
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		e.getComponent().setBackground(new Color(100));
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		e.getComponent().setBackground(new Color(0));
		
	}

	@Override
	public void mousePressed(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	
}
