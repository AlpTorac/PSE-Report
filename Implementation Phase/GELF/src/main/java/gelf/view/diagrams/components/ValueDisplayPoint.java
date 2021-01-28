package gelf.view.diagrams.components;

import java.awt.Color;
import java.awt.Component;

public class ValueDisplayPoint extends DiagramPoint {
	protected ValueDisplayPoint(Color color, Number value, Number size, PositionIn2DDiagram position) {
		super(position, color, value, size);
	}
	
	@Override
	public void show() {
		
	}
	@Override
	public void hide() {
		
	}
	@Override
	public DiagramComponent clone() {
		return null;
	}

	@Override
	public void addHoverListeners(Component[] components) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void refreshValueRelevantAttributes() {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void initVisualElement() {
		// TODO Auto-generated method stub
		
	}
}
