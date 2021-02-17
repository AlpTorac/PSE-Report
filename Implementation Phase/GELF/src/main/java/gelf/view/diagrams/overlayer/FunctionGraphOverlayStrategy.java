package gelf.view.diagrams.overlayer;

import java.awt.Color;
import java.util.ArrayList;
import java.util.TreeSet;

import gelf.view.diagrams.IDiagram;
import gelf.view.diagrams.SettingsProvider;
import gelf.view.diagrams.builder.IFunctionGraphBuilder;
import gelf.view.diagrams.components.DiagramAxis;
import gelf.view.diagrams.components.DiagramComponent;
import gelf.view.diagrams.components.DiagramValueDisplayComponent;
import gelf.view.diagrams.components.ValueDisplayPoint;
import gelf.view.diagrams.data.DiagramData;
import gelf.view.diagrams.type.FunctionGraph;

public class FunctionGraphOverlayStrategy extends DiagramOverlayStrategy implements IFunctionGraphBuilder {

	private FunctionGraph[] functionGraphs;
	
	public FunctionGraphOverlayStrategy() {
		
	}
	
	public FunctionGraphOverlayStrategy(FunctionGraph[] functionGraphs) {
		this.functionGraphs = functionGraphs;
	}

	@Override
	public void setDiagrams(IDiagram[] diagrams) {
		FunctionGraph[] functionGraphs = new FunctionGraph[diagrams.length];
		
		for (int i = 0; i < functionGraphs.length; i++) {
			functionGraphs[i] = (FunctionGraph) diagrams[i];
		}
		
		this.functionGraphs = functionGraphs;
	}

	@Override
	protected IDiagram[] getAllDiagrams() {
		return this.functionGraphs;
	}

	@Override
	protected IDiagram buildDiagram(DiagramData data, DiagramAxis[] axes,
			DiagramValueDisplayComponent[] valueDisplayComponents, DiagramComponent[] nonValueDisplayComponents) {
		return new FunctionGraph(data, axes, valueDisplayComponents, nonValueDisplayComponents);
	}

	@Override
	protected DiagramValueDisplayComponent[] makeValueDisplayComponentsForOneDiagram(DiagramData diagramData, int orderInSameDiagram,
			DiagramAxis[] axes, DiagramComponent[] nonValueDisplayComponents) {
		return this.buildValueDisplayComponentsForOneDiagram(diagramData, orderInSameDiagram, axes, nonValueDisplayComponents);
	}
	
	protected ArrayList<TreeSet<Float>> unifyIndices(DiagramData[] diagramData) {
		return null;
	}
	
	@Override
	protected DiagramData[] unifyData(DiagramData[] diagramData, ArrayList<TreeSet<Float>> newIndices) {
		return diagramData;
	}
	
	
	@Override
	protected void overlapCovered(DiagramValueDisplayComponent currentDvdc, DiagramValueDisplayComponent dvdcToCompareTo) {
		if (this.covers(currentDvdc, dvdcToCompareTo)) {
			Color currentColor = currentDvdc.getColor();
			Color nextColor = dvdcToCompareTo.getColor();
			
			dvdcToCompareTo.setColor(SettingsProvider.getMixedColor(currentColor, nextColor));
			currentDvdc.setColor(SettingsProvider.getMixedColor(currentColor, nextColor));
		}
	}
	
	@Override
	protected boolean covers(DiagramValueDisplayComponent currentDvdc,
			DiagramValueDisplayComponent dvdcToCompareTo) {
		if (!(currentDvdc instanceof ValueDisplayPoint && dvdcToCompareTo instanceof ValueDisplayPoint)) {
			return false;
		}
		
		ValueDisplayPoint cDvdc = (ValueDisplayPoint) currentDvdc;
		ValueDisplayPoint nDvdc = (ValueDisplayPoint) dvdcToCompareTo;
		
		double minXCurrent = cDvdc.getPositionInDiagram().getXCoordinate();
		float valueCurrent = cDvdc.getValue();
		
		double minXNext = nDvdc.getPositionInDiagram().getXCoordinate();
		float valueNext = nDvdc.getValue();
		
		return (Math.abs(minXCurrent - minXNext) <= SettingsProvider.getTolerance()) &&
				(Math.abs(valueCurrent - valueNext) <= SettingsProvider.getTolerance());
	}
}
