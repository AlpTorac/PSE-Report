package gelf.view.diagrams.components;

import java.awt.Color;
import java.awt.Component;

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

	public String getCaption() {
		return caption;
	}

	public void setCaption(String caption) {
		this.caption = caption;
	}

	public Color getColor() {
		return color;
	}

	public void setColor(Color color) {
		this.color = color;
	}

	public Number getXPos() {
		return position.getXPos();
	}

	public void setXPos(Number xPos) {
		this.position.setXPos(xPos);
	}
	
	public Number getYPos() {
		return position.getYPos();
	}

	public void setYPos(Number yPos) {
		this.position.setYPos(yPos);
	}

	public Number getWidth() {
		return width;
	}

	public void setWidth(Number width) {
		this.width = width;
	}

	public Number getHeight() {
		return height;
	}

	public void setHeight(Number height) {
		this.height = height;
	}

	public Component getComponent() {
		return component;
	}

	public void setComponent(Component component) {
		this.component = component;
	}

	public static HoverLabel getHoverLabel() {
		return hoverLabel;
	}
	
	
}
