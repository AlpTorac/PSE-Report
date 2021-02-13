package gelf.view.diagrams.overlayer;

import java.awt.Color;
import java.util.ArrayList;

import gelf.view.diagrams.IDiagram;
import gelf.view.diagrams.components.BarChartBar;
import gelf.view.diagrams.components.DiagramAxis;
import gelf.view.diagrams.components.DiagramComponent;
import gelf.view.diagrams.components.DiagramValueDisplayComponent;
import gelf.view.diagrams.components.PositionIn2DDiagram;
import gelf.view.diagrams.data.DiagramData;
import gelf.view.diagrams.type.BarChart;

public class BarChartOverlayStrategy extends DiagramOverlayStrategy {

	private BarChart[] barCharts;
	
	public BarChartOverlayStrategy() {
		
	}
	
	public BarChartOverlayStrategy(BarChart[] barCharts) {
		this.barCharts = barCharts;
	}

	@Override
	public void setDiagrams(IDiagram[] diagrams) {
		this.barCharts = (BarChart[]) diagrams;
	}

	@Override
	public DiagramValueDisplayComponent[] makeValueDisplayComponents(DiagramAxis[] axes, DiagramComponent[] nonValueDisplayComponents, DiagramData[] diagramData) {
		ArrayList<DiagramValueDisplayComponent> dvdcList = new ArrayList<DiagramValueDisplayComponent>();
		
		for (int index = 0; index < diagramData.length; index++) {
			DiagramData data = diagramData[index];
			float[] values = data.extractValues().get(index);
			int dvdcCount = values.length;
			
			Color barColor = settingsProvider.getValueDisplayComponentColorAt(index);
			int thickness = settingsProvider.getBarBorderThickness();
			
			for (int i = 0; i < dvdcCount; i++) {
				PositionIn2DDiagram topLeft = factory.makePositionInDiagram(axes[0], i, axes[1], values[i]);
				PositionIn2DDiagram bottomRight = factory.makePositionInDiagram(axes[0], i + 1, axes[1], 0);
				
				BarChartBar bar = factory.createBarChartBar(barColor, values[i], topLeft, bottomRight, thickness);
				dvdcList.add(bar);
			}
		}
		
		return (DiagramValueDisplayComponent[]) dvdcList.toArray();
	}

	@Override
	public DiagramComponent[] makeNonValueDisplayComponents(DiagramData[] diagramData) {
		return null;
	}

	@Override
	public BarChart buildDiagram(DiagramData data, DiagramAxis[] axes, DiagramValueDisplayComponent[] valueDisplayComponents,
			DiagramComponent[] nonValueDisplayComponents) {
		return new BarChart(data, axes, valueDisplayComponents, nonValueDisplayComponents);
	}

	@Override
	protected IDiagram[] getAllDiagrams() {
		return this.barCharts;
	}
}
