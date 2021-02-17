package gelf.view.diagrams.type;

import java.awt.Component;
import java.util.ArrayList;
import java.util.EnumMap;

import javax.swing.JLayeredPane;

import gelf.view.diagrams.IDiagram;
import gelf.view.diagrams.components.DiagramAxis;
import gelf.view.diagrams.components.DiagramComponent;
import gelf.view.diagrams.components.DiagramValueDisplayComponent;
import gelf.view.diagrams.data.DiagramData;
import gelf.view.diagrams.indicator.DiagramViewHelper;
import gelf.view.diagrams.indicator.IndicatorIdentifier;

public abstract class Diagram implements IDiagram {
	private DiagramData data;
	private DiagramAxis[] axes;
	private DiagramValueDisplayComponent[] valueDisplayComponents;
	private DiagramComponent[] nonValueDisplayComponents;
	private EnumMap<IndicatorIdentifier, DiagramViewHelper> viewHelpers;
	
	private JLayeredPane containingElement;

	public Diagram(DiagramData data, DiagramAxis[] axes,
			DiagramValueDisplayComponent[] valueDisplayComponents,
			DiagramComponent[] nonValueDisplayComponents) {
		this.viewHelpers = new EnumMap<IndicatorIdentifier, DiagramViewHelper>(IndicatorIdentifier.class);
		this.containingElement = new JLayeredPane();
		this.containingElement.setLayout(null);
		
		this.data = data;
		this.axes = axes;
		this.valueDisplayComponents = valueDisplayComponents;
		this.nonValueDisplayComponents = nonValueDisplayComponents;
		
		this.attachComponents(this.axes);
		this.attachComponents(this.nonValueDisplayComponents);
		this.attachComponents(this.valueDisplayComponents);
	}
	
	private void attachComponents(DiagramComponent[] dcs) {
		if (dcs != null) {
			for (int i = 0; i < dcs.length; i++) {
				dcs[i].attachToDiagram(this);
			}
		}
	}
	
	public ArrayList<float[]> cloneData() {
		ArrayList<float[]> dataClone = new ArrayList<float[]>();
		
		ArrayList<float[]> indices = this.data.extractIndices();
		ArrayList<float[]> values = this.data.extractValues();
		
		for (float[] indexArr : indices) {
			dataClone.add(indexArr);
		}
		for (float[] valueArr : values) {
			dataClone.add(valueArr);
		}
		
		return dataClone;
	}
	
	public DiagramData cloneDiagramData() {
		return this.data.clone();
	}
	
	private void refreshAxes() {
		if (this.axes != null) {
			for (DiagramAxis axis : this.axes) {
				axis.hide();
				axis.show();
				axis.showValues();
			}
		}
	}
	
	private void refreshValueDisplayComponents() {
		if (this.valueDisplayComponents != null) {
			for (DiagramValueDisplayComponent dvdc : this.valueDisplayComponents) {
				dvdc.hide();
				dvdc.show();
			}
		}
	}
	
	private void refreshNonValueDisplayComponents() {
		if (this.nonValueDisplayComponents != null) {
			for (DiagramComponent dc : this.nonValueDisplayComponents) {
				dc.hide();
				dc.show();
			}
		}
	}
	
	public void refresh() {
		this.refreshAxes();
		this.refreshValueDisplayComponents();
		this.refreshNonValueDisplayComponents();
	}
	
	public boolean addDiagramViewHelper(DiagramViewHelper dvh) {
		boolean result = (this.viewHelpers.put(dvh.getID(), dvh) != null);
		
		return result;
	}
	
	public boolean removeDiagramViewHelper(IndicatorIdentifier id) {
		return (this.viewHelpers.remove(id) != null);
	}
	
	public void showDiagramViewHelper(IndicatorIdentifier id) {
		this.viewHelpers.get(id).show();
	}
	
	public void hideDiagramViewHelper(IndicatorIdentifier id) {
		this.viewHelpers.get(id).hide();
	}
	
	public DiagramComponent[] getNonValueDisplayDiagramComponentPrototype() {
		if (this.nonValueDisplayComponents == null) {
			return null;
		}
		
		int length = this.nonValueDisplayComponents.length;
		
		DiagramComponent[] clones = new DiagramComponent[length];
		
		for (int i = 0; i < length; i++) {
			clones[i] = this.nonValueDisplayComponents[i].clone();
		}
		
		return clones;
	}
	
	public DiagramValueDisplayComponent[] getDiagramValueDisplayComponentPrototypes() {
		if (this.valueDisplayComponents == null) {
			return null;
		}
		
		int length = this.valueDisplayComponents.length;
		
		DiagramValueDisplayComponent[] clones = new DiagramValueDisplayComponent[length];
		
		for (int i = 0; i < length; i++) {
			clones[i] = this.valueDisplayComponents[i].clone();
		}
		
		return clones;
	}
	
	public DiagramAxis[] getDiagramAxisPrototypes() {
		if (this.axes == null) {
			return null;
		}
		
		int length = this.axes.length;
		
		DiagramAxis[] clones = new DiagramAxis[length];
		
		for (int i = 0; i < length; i++) {
			clones[i] = (DiagramAxis) this.axes[i].clone();
		}
		
		return clones;
	}
	
	public void addComponent(Component dc, int layer) {
		this.containingElement.add(dc, Integer.valueOf(layer));
	}
	
	public void removeComponent(Component dc) {
		this.containingElement.remove(dc);
	}
	
	public JLayeredPane getContainingElement() {
		return this.containingElement;
	}
}
