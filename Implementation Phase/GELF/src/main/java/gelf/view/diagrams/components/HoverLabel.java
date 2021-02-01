package gelf.view.diagrams.components;

import java.awt.Color;
import java.awt.Component;
import java.awt.Rectangle;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.border.Border;

public class HoverLabel {
	private String caption;
	private Color color;
	private PositionInFrame position;
	private double width;
	private double height;
	private Component component;
	private static HoverLabel hoverLabel = new HoverLabel();

	private HoverLabel() {
		this.component = new HoverLabelVisual();
	}

	private void setComponentBounds() {
		Rectangle bounds = new Rectangle();
		bounds.setRect(this.getXPos(), this.getYPos(), this.getWidth(), this.getHeight());
		
		this.component.setBounds(bounds);
	}

	private void setComponentColor() {
		this.component.setBackground(this.color);
	}

	private void setComponentCaption() {
		((HoverLabelVisual) this.component).setText(getCaption());
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

	public static HoverLabel getHoverLabel() {
		return hoverLabel;
	}
	
	private class HoverLabelVisual extends JLabel {
		/**
		 * Generated serial version ID.
		 */
		private static final long serialVersionUID = 6228060598904141126L;
		HoverLabel label = HoverLabel.getHoverLabel();
		
		private HoverLabelVisual() {
			this.setBackground(this.label.getColor());
			this.label.setComponentBounds();
			
			Border b = BorderFactory.createLineBorder(Color.BLACK, 5);
			this.setBorder(b);
		}
	}
}
