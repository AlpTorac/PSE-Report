package gelf.view.diagrams.components;

import java.awt.Container;

public class SolidAxis extends DiagramAxis {

	protected SolidAxis(SolidLine axisLine, float min, float max, int steps, Container containingElement) {
		super(axisLine, min, max, steps, containingElement);
	}

	@Override
	public SolidAxis clone() {
		return new SolidAxis((SolidLine) this.axisLine.clone(), this.getMin(), this.getMax(), this.getSteps(), this.containingElement);
	}
}
