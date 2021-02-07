package gelf.view.diagrams;

import java.awt.Color;
import java.awt.Component;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JFrame;

public class TestFrame extends JFrame {

	/**
	 * Generated version serial ID.
	 */
	private static final long serialVersionUID = -9194415774691250413L;
	
	public TestFrame() {
		super();
		this.setBounds(0, 0, 1000, 1000);
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		this.getContentPane().setLayout(null);
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
