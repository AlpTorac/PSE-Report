package gelf.view.diagrams;

import java.awt.Color;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.border.Border;

public class TestPanel extends JPanel {
	/**
	 * Generated serial version ID.
	 */
	private static final long serialVersionUID = -3044679383663006138L;

	public TestPanel() {
		super();
		this.setBounds(0, 0, 1000, 1000);
		this.setLayout(null);
		
		Border border = BorderFactory.createLineBorder(Color.BLACK);
		this.setBorder(border);
		
		this.setBackground(new Color(200, 200, 200, 255));
//		this.addMouseListener(new MouseAdapter() {
//			@Override
//			public void mouseEntered(MouseEvent me) {
//				
//			}
//
//			@Override
//			public void mouseMoved(MouseEvent me) {
//				Component c = getComponentAt(me.getX(), me.getY());
//				
//				if (c != null) {
//					System.out.println(c.toString() + " location:" + " upper left corner: " + "(" + c.getX() + ", " + c.getY() + ")");
//					System.out.println(c.toString() + " location:" + " bottom right corner: " + "(" + c.getWidth() + ", " + c.getHeight() + ")");
//				}
//			}
//
//			@Override
//			public void mouseExited(MouseEvent me) {
//				
//			}
//		});
	}
}