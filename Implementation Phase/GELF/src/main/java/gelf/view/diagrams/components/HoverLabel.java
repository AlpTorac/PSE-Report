package gelf.view.diagrams.components;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Rectangle;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.border.Border;

import gelf.view.diagrams.IDiagram;
import gelf.view.diagrams.SettingsProvider;

public class HoverLabel implements HasAttachablePart {
	private String caption;
	private Color color;
	private PositionInFrame position;
	private IDiagram containingDiagram;
	private Component component;
	private static HoverLabel hoverLabel;
	private SettingsProvider sp = SettingsProvider.getInstance();
	
	private HoverLabel() {
		this.position = new PositionInFrame(0, 0);
		this.color = Color.WHITE;
		this.caption = "";
		this.component = new HoverLabelVisual(this);
	}
	
	private Rectangle calculateBounds() {
		Dimension d = this.component.getPreferredSize();
		Rectangle bounds = new Rectangle();
		bounds.setRect(this.getXPos(), this.getYPos() - d.getHeight(), d.getWidth(), d.getHeight());
		
		Component containingElement = this.containingDiagram.getContainingElement();
		
		if (bounds.getMaxX() >= containingElement.getWidth()) {
			bounds.x = (int) (containingElement.getBounds().getMaxX() - d.getWidth());
		}
		if (bounds.getMinY() <= 0) {
			bounds.y = 0;
		}
		
		return bounds;
	}
	
	private void setComponentBounds() {
		if (this.containingDiagram != null) {
			this.component.setBounds(this.calculateBounds());
			this.containingDiagram.getContainingElement().repaint();
		}
	}

	private void setComponentCaption() {
		this.setComponentBounds();
		this.component.repaint();
	}
	
	private String getCaptionForComponent() {
		String result = this.caption;
		result = "<html>" + result + "</html>";
		result = result.replaceAll("\n", "<br/>");
		return result;
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
		this.component.repaint();
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

	public void show() {
		this.component.setVisible(true);
	}

	public void hide() {
		this.component.setVisible(false);
	}

	public void attachToDiagram(IDiagram diagram) {
		if (this.containingDiagram != null) {
			this.hide();
			this.removeFromDiagram();
		}
			this.containingDiagram = diagram;
			this.containingDiagram.addComponent(this.component, sp.getDiagramHoverLabelLayer());
			this.setComponentBounds();
			this.containingDiagram.getContainingElement().repaint();
			this.show();
	}
	
	public void removeFromDiagram() {
		if (this.containingDiagram != null) {
			this.containingDiagram.removeComponent(this.component);
			this.containingDiagram.getContainingElement().repaint();
			this.containingDiagram = null;
			this.hide();
		}
	}
	
	public IDiagram getAttachedDiagram() {
		return this.containingDiagram;
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
			
			this.setText(this.label.getCaptionForComponent());
			this.setPreferredSize(null);
			
			this.setOpaque(true);
		}
		
		@Override
		protected void paintComponent(Graphics g) {
			super.paintComponent(g);
			this.setBackground(this.label.getColor());
			this.setText(this.label.getCaptionForComponent());
		}
	}
}
