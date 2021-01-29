package gelf.view.diagrams.components;

import java.awt.Color;

public class HeatMapLabel extends DiagramValueLabel {

	protected HeatMapLabel(PositionIn2DDiagram topLeft, PositionIn2DDiagram bottomRight, Color color, double value,
			double borderThickness) {
		super(topLeft, bottomRight, color, value, borderThickness);
		// TODO Auto-generated constructor stub
	}


	@Override
	public void refreshValueRelevantAttributes() {
		// TODO Auto-generated method stub

	}

	@Override
	public DiagramComponent clone() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected void initVisualElement() {
		// TODO Auto-generated method stub

	}
}
