package gelf.view.diagrams.components;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Point;

public class HoverLabel {
	private String caption;
	private Color color;
	private PositionInFrame position;
	private Number width;
	private Number height;
	private Component component;
	private static HoverLabel hoverLabel = new HoverLabel();
	
	private HoverLabel() {
		
	}
	
	private void setComponentSize() {
		Dimension size = new Dimension();
		size.setSize(width.doubleValue(), height.doubleValue());
		this.component.setSize(size);
	}
	
	private void setComponentLocation() {
		Point p = new Point();
		p.setLocation(position.getXPos().doubleValue(), position.getYPos().doubleValue());
		this.component.setLocation(null);
	}
	
	private void setComponentColor() {
		this.component.setBackground(color);
	}
	
	private void setComponentCaption() {
		Graphics2D g = (Graphics2D) this.component.getGraphics();
		g.drawString(this.caption, 0, 0);
	}
	
	public String getCaption() {
		return caption;
	}

	public void setCaption(String caption) {
		this.caption = caption;
		this.setComponentCaption();
	}

	public Color getColor() {
		return color;
	}

	public void setColor(Color color) {
		this.color = color;
		this.setComponentColor();
	}

	public Number getXPos() {
		return position.getXPos();
	}

	public void setXPos(Number xPos) {
		this.position.setXPos(xPos);
		this.setComponentLocation();
	}
	
	public Number getYPos() {
		return position.getYPos();
	}

	public void setYPos(Number yPos) {
		this.position.setYPos(yPos);
		this.setComponentLocation();
	}

	public Number getWidth() {
		return width;
	}

	public void setWidth(Number width) {
		this.width = width;
		this.setComponentSize();
	}

	public Number getHeight() {
		return height;
	}

	public void setHeight(Number height) {
		this.height = height;
		this.setComponentSize();
	}

	public static HoverLabel getHoverLabel() {
		return hoverLabel;
	}
	
	
}
