package gelf.view.diagrams;

import java.awt.Component;
import java.awt.Container;
import java.util.Collection;

import gelf.view.diagrams.components.DiagramAxis;
import gelf.view.diagrams.components.DiagramComponent;
import gelf.view.diagrams.components.DiagramValueDisplayComponent;
import gelf.view.diagrams.data.DiagramData;
import gelf.view.diagrams.indicator.DiagramViewHelper;
import gelf.view.diagrams.indicator.IndicatorIdentifier;

public interface IDiagram {
	public Collection<?> cloneData();
	
	public DiagramData cloneDiagramData();
	
	public void refresh();
	
	public void addComponent(Component visualElement, int layer);
	
	public void removeComponent(Component visualElement);
	
	public boolean addDiagramViewHelper(DiagramViewHelper dvh);
	
	public boolean removeDiagramViewHelper(IndicatorIdentifier id);
	
	public void showDiagramViewHelper(IndicatorIdentifier id);
	
	public void hideDiagramViewHelper(IndicatorIdentifier id);
	
	public DiagramComponent[] getNonValueDisplayDiagramComponentPrototype();
	
	public DiagramValueDisplayComponent[] getDiagramValueDisplayComponentPrototypes();
	
	public DiagramAxis[] getDiagramAxisPrototypes();
	
	public Component getContainingElement();
	
	public default void show() {
		this.getContainingElement().setVisible(true);
	}
	
	public default void hide() {
		this.getContainingElement().setVisible(false);
	}
	
	public default void attachToContainer(Container container) {
		container.add(this.getContainingElement());
		this.getContainingElement().setBounds(0, 0, container.getWidth(), container.getHeight());
	}
	
	public default void removeFromContainer() {
		this.getContainingElement().getParent().remove(this.getContainingElement());
	}
}
