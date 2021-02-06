package gelf.view.diagrams.components;

import java.awt.Color;
import java.awt.Container;

public class DescriptionLabel extends DiagramLabel {

	protected DescriptionLabel(PositionInFrame topLeft, PositionInFrame bottomRight, Color color, String caption,
			int borderThickness, Container containingElement) {
		super(topLeft, bottomRight, color, caption, borderThickness, containingElement);
	}

	@Override
	public DescriptionLabel clone() {
		Color newColor = new Color(this.getColor().getRGB());
		return new DescriptionLabel(this.getTopLeftInDiagram().clone(),
				this.getBottomRightInDiagram().clone(),
				newColor, this.getCaption(),
				this.getBorderThickness(),
				this.containingElement);
	}
}