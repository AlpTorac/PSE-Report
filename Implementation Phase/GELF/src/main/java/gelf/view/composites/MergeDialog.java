package gelf.view.composites;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;

import gelf.model.commands.ConflictData;
import gelf.model.commands.ResolutionMethod;
import gelf.model.elements.Cell;
import gelf.view.components.Button;
import gelf.view.components.InputBox;
import gelf.view.components.Label;
import gelf.view.components.Panel;

/**
 * Dialog for the merge conflicts.
 * @author Ege Uzhan
 */
public class MergeDialog extends JDialog implements ActionListener, WindowListener{
	
	private Panel panel;
	private Label prompt;
	private Label leftCell;
	private Label rightCell;
	private InputBox leftBox;
	private InputBox rightBox;
	private Button renameLeft;
	private Button renameRight;
	private Button keepLeft;
	private Button keepRight;
	private ConflictData conflictData;
	
	/*
	 * Initializes the merge dialog.
	 */
	public MergeDialog() {
		this.setSize(new Dimension(450, 200));
	}
	  
	/**
	 * Creates the dialog 
	 * @param cell1 Left cell
	 * @param cell2 Right cell
	 */
	private void setup(Cell cell1, Cell cell2) {
		this.setModal(true);
	    panel = new Panel(this.getWidth(), this.getWidth());
	    panel.setLayout(new GridBagLayout());
	    GridBagConstraints c = new GridBagConstraints();
	    Border margin = new EmptyBorder(5, 5, 5, 5);
	    			
	    prompt = new Label("Choose an element to keep or rename one to keep both.");
	    prompt.setBackground(new Color(0.4f, 0.4f, 0.4f));
	    prompt.setForeground(Color.WHITE);
	    c.fill = GridBagConstraints.HORIZONTAL;
	    c.gridwidth = 10;
	    c.ipady = 0;
	    c.gridx = 1;
	    c.gridy = 0;
	    	
	    panel.add(prompt, c);
	    	
	    leftCell = new Label(cell1.getParentLibrary().getName() + "//" + cell1.getName());
	    c.fill = GridBagConstraints.HORIZONTAL;
	    leftCell.setForeground(Color.WHITE);
	    c.weightx = 0.5;
	    c.weighty = 0.5;
	    c.gridwidth = 4;
	    c.gridx = 1;
	    c.gridy = 1;
	    panel.add(leftCell, c);
	    	
	    rightCell = new Label(cell1.getParentLibrary().getName() + "//" + cell1.getName());
	    rightCell.setForeground(Color.WHITE);
	    c.weightx = 0.5;
	    c.weighty = 0.5;
	    c.gridwidth = 4;
	    c.gridx = 7;
	    c.gridy = 1;
	    panel.add(rightCell, c);
	    	
	    leftBox = new InputBox();
	    leftBox.setBackground(new Color(0.5f, 0.5f, 0.5f));
	    leftBox.setForeground(Color.WHITE);
	    c.weightx = 0.5;
	    c.weighty = 0.5;
	    c.gridwidth = 3;
	    c.gridx = 1;
	    c.gridy = 2;
	    leftBox.setBorder(margin);
	    panel.add(leftBox, c);
	    	
	    renameLeft = new Button("Rename");
	    renameLeft.setBackground(new Color(0.4f, 0.4f, 0.4f));
	    renameLeft.setForeground(Color.WHITE);
	    c.weightx = 0.5;
	    c.weighty = 0.5;
	    c.gridwidth = 1;
	    c.gridx = 4;
	    c.gridy = 2;
	    renameLeft.setBorder(margin);
	    panel.add(renameLeft, c);
	    c.ipadx = 1;
	    	
	    rightBox = new InputBox();
	    rightBox.setBackground(new Color(0.5f, 0.5f, 0.5f));
	    rightBox.setForeground(Color.WHITE);
	    c.weightx = 0.5;
	    c.weighty = 0.5;
	    c.gridwidth = 3;
	    c.gridx = 7;
	    c.gridy = 2;
	    rightBox.setBorder(margin);
	    panel.add(rightBox, c);
	    	
	    renameRight = new Button("Rename");
	    renameRight.setBackground(new Color(0.4f, 0.4f, 0.4f));
	    renameRight.setForeground(Color.WHITE);
	    c.weightx = 0.5;
	    c.weighty = 0.5;
	    c.gridwidth = 1;
	    c.gridx = 10;
	    c.gridy = 2;
	    renameRight.setBorder(margin);
	    panel.add(renameRight, c);

	    keepLeft = new Button("Keep this element");
	    keepLeft.setBackground(new Color(0.4f, 0.4f, 0.4f));
	    keepLeft.setForeground(Color.WHITE);
	    c.weightx = 0.5;
	    c.weighty = 0.5;
	    c.gridwidth = 2;
	    c.gridx = 1;
	    c.gridy = 3;
	    panel.add(keepLeft, c);
	    	
	    keepRight = new Button("Keep this element");
	    keepRight.setBackground(new Color(0.4f, 0.4f, 0.4f));
	    keepRight.setForeground(Color.WHITE);
	    c.weightx = 0.5;
	    c.weighty = 0.5;
	    c.gridwidth = 2;
	    c.gridx = 7;
	    c.gridy = 3;
	    panel.add(keepRight, c);
	    	
	    	
	    this.add(panel);
	    panel.setBackground(new Color(0.3f, 0.3f, 0.3f));
	    	
	    this.addWindowListener(this);
	    renameLeft.addActionListener(this);
	    renameRight.addActionListener(this);
	    keepLeft.addActionListener(this);
	    keepRight.addActionListener(this);
	    	
    	
    }
    	
    /**
     * Opens the dialog when a merge conflict emerges.
     * @return Data for resolving the conflict.
     */
	public ConflictData open(Cell cell1, Cell cell2) {
		setup(cell1, cell2);
		this.setVisible(true);
		return conflictData;
	}
    	
    	
	@Override
    public void actionPerformed(ActionEvent e) {
		JButton button = (JButton) e.getSource();
		if (button.equals(renameLeft)) {
			conflictData = new ConflictData(ResolutionMethod.RENAMELEFT, leftBox.getText());
			
		}
		else if (button.equals(renameRight)) {
			conflictData = new ConflictData(ResolutionMethod.RENAMERIGHT, rightBox.getText());
			
		}
		else if (button.equals(keepLeft)) {
			conflictData = new ConflictData(ResolutionMethod.KEEPLEFT, null);
			
		}
		else if (button.equals(keepRight)) {
			conflictData = new ConflictData(ResolutionMethod.KEEPRIGHT,null);
	
		}
		setVisible(false);
		dispose();
	}
	    
    @Override
	public void windowClosing(WindowEvent e) {
    	conflictData = new ConflictData(ResolutionMethod.CANCEL, null);
		setVisible(false);
		JOptionPane.showMessageDialog(new JFrame(), "Merging cancelled.", "", JOptionPane.CANCEL_OPTION);
		dispose();
		
	}
	    
	    
	@Override
	public void windowClosed(WindowEvent e) {}

	@Override
	public void windowActivated(WindowEvent e) {}
	
	@Override
	public void windowDeactivated(WindowEvent e) {}

	@Override
	public void windowDeiconified(WindowEvent e) {}

	@Override
	public void windowIconified(WindowEvent e) {}

    @Override
	public void windowOpened(WindowEvent e) {}

	  	
    	
}