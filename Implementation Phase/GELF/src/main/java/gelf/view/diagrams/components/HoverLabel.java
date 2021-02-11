package gelf.view.diagrams.components;

import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.border.Border;

public class HoverLabel implements HasAttachablePart {
	private String caption;
	private Color color;
	private PositionInFrame position;
	private double width;
	private double height;
	private Container containingElement;
	private Component component;
	private static HoverLabel hoverLabel;

	private HoverLabel() {
		this.width = 100;
		this.height = 50;
		this.position = new PositionInFrame(0, 0);
		this.color = new Color(255, 255, 255, 255);
		this.caption = "";
		this.component = new HoverLabelVisual(this);
		this.setComponentBounds();
	}

	private void setComponentBounds() {
		Rectangle bounds = new Rectangle();
		bounds.setRect(this.getXPos(), this.getYPos(), this.getWidth(), this.getHeight());
		
		this.component.setBounds(bounds);
		if (this.containingElement != null) {
			this.containingElement.repaint();
		}
	}

	private void setComponentColor() {
		this.component.repaint();
	}

	private void setComponentCaption() {
		this.component.repaint();
	}

	public String getCaption() {
		return this.caption;
	}

	public void setCaption(String caption) {
		this.caption = caption;
		this.setComponentCaption();
	}

	public Color getColor() {
		return this.color;
	}

	public void setColor(Color color) {
		this.color = color;
		this.setComponentColor();
	}

	public double getXPos() {
		return this.position.getXPos();
	}

	public void setXPos(double xPos) {
		this.position.setXPos(xPos);
		this.setComponentBounds();
	}

	public double getYPos() {
		return this.position.getYPos();
	}

	public void setYPos(double yPos) {
		this.position.setYPos(yPos);
		this.setComponentBounds();
	}

	public double getWidth() {
		return this.width;
	}

	public void setWidth(double width) {
		this.width = width;
		this.setComponentBounds();
	}

	public double getHeight() {
		return this.height;
	}

	public void setHeight(double height) {
		this.height = height;
		this.setComponentBounds();
	}

	public void show() {
		this.component.setVisible(true);
	}

	public void hide() {
		this.component.setVisible(false);
	}

	public void attachToContainer(Container container) {
		if (this.containingElement != container) {
			this.hide();
			this.removeFromContainer();
		}
		this.containingElement = container;
		this.containingElement.add(this.component);
		this.containingElement.repaint();
		this.show();
	}
	
	public void removeFromContainer() {
		if (this.containingElement != null) {
			this.containingElement.remove(this.component);
			this.containingElement.repaint();
			this.containingElement = null;
			this.hide();
			this.setXPos(0);
			this.setYPos(0);
		}
	}
	
	public static HoverLabel getHoverLabel() {
		if (hoverLabel == null) {
			hoverLabel = new HoverLabel();
		}
		return hoverLabel;
	}
	
	private class HoverLabelVisual extends JLabel {
		/**
		 * Generated serial version ID.
		 */
		private static final long serialVersionUID = 6228060598904141126L;
		private HoverLabel label;
		
		private HoverLabelVisual(HoverLabel label) {
			this.label = label;
			this.setBackground(this.label.getColor());
			
			Border b = BorderFactory.createLineBorder(Color.BLACK, 1);
			this.setBorder(b);
			
			this.setHorizontalAlignment(CENTER);
			this.setVerticalAlignment(CENTER);
			this.setText(this.label.getCaption());
			
			this.setOpaque(true);
		}
		
		@Override
		protected void paintComponent(Graphics g) {
//			this.setBackground(this.label.getColor());
			super.paintComponent(g);
//			Graphics2D graphs = (Graphics2D) g;
//			Border b = BorderFactory.createLineBorder(Color.BLACK, 1);
//			this.setBorder(b);
//			this.setText(this.label.getCaption());
		}
	}
}
