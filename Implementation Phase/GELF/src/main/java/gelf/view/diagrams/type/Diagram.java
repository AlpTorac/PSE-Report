package gelf.view.diagrams.type;

import java.awt.Container;
import java.util.ArrayList;
import java.util.EnumMap;

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
	
	private Container containingElement;
	
	public Diagram(DiagramData data, DiagramAxis[] axes,
			DiagramValueDisplayComponent[] valueDisplayComponents,
			DiagramComponent[] nonValueDisplayComponents,
			Container containingElement) {
		this.data = data;
		this.axes = axes;
		this.valueDisplayComponents = valueDisplayComponents;
		this.nonValueDisplayComponents = nonValueDisplayComponents;
		this.containingElement = containingElement;
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
		return false;
	}
	
	public boolean removeDiagramViewHelper(IndicatorIdentifier id) {
		return false;
	}
	
	public boolean showDiagramViewHelper(IndicatorIdentifier id) {
		return false;
	}
	
	public boolean hideDiagramViewHelper(IndicatorIdentifier id) {
		return false;
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
}
