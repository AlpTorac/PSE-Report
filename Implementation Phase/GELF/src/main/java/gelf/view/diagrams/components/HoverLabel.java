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
	private double width;
	private double height;
	private Component component;
	private static HoverLabel hoverLabel = new HoverLabel();

	private HoverLabel() {

	}

	private void setComponentSize() {
		Dimension size = new Dimension();
		size.setSize(width, height);
		this.component.setSize(size);
	}

	private void setComponentLocation() {
		Point p = new Point();
		p.setLocation(position.getXPos(), position.getYPos());
		this.component.setLocation(null);
	}

	private void setComponentColor() {
		this.component.setBackground(color);
	}

	private void setComponentCaption(float x, float y) {
		Graphics2D g = (Graphics2D) this.component.getGraphics();
		g.drawString(this.caption, x, y);
	}

	public String getCaption() {
		return this.caption;
	}

	public void setCaption(String caption) {
		this.caption = caption;
		this.setComponentCaption(0, 0);
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
		this.setComponentLocation();
	}

	public double getYPos() {
		return this.position.getYPos();
	}

	public void setYPos(double yPos) {
		this.position.setYPos(yPos);
		this.setComponentLocation();
	}

	public double getWidth() {
		return this.width;
	}

	public void setWidth(double width) {
		this.width = width;
		this.setComponentSize();
	}

	public double getHeight() {
		return this.height;
	}

	public void setHeight(double height) {
		this.height = height;
		this.setComponentSize();
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
}
