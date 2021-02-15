package gelf.view.diagrams.overlayer;

import java.awt.Color;
import java.awt.Rectangle;

import gelf.view.diagrams.IDiagram;
import gelf.view.diagrams.SettingsProvider;
import gelf.view.diagrams.builder.IBarChartBuilder;
import gelf.view.diagrams.components.DiagramAxis;
import gelf.view.diagrams.components.DiagramComponent;
import gelf.view.diagrams.components.DiagramValueDisplayComponent;
import gelf.view.diagrams.data.DiagramData;
import gelf.view.diagrams.type.BarChart;

public class BarChartOverlayStrategy extends DiagramOverlayStrategy implements IBarChartBuilder {

	private BarChart[] barCharts;
	
	public BarChartOverlayStrategy() {
		
	}
	
	public BarChartOverlayStrategy(BarChart[] barCharts) {
		this.barCharts = barCharts;
	}

	@Override
	public void setDiagrams(IDiagram[] diagrams) {
		BarChart[] barCharts = new BarChart[diagrams.length];
		
		for (int i = 0; i < barCharts.length; i++) {
			barCharts[i] = (BarChart) diagrams[i];
		}
		
		this.barCharts = barCharts;
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

	@Override
	protected DiagramValueDisplayComponent[] makeValueDisplayComponentsForOneDiagram(DiagramData diagramData, int orderInSameDiagram,
			DiagramAxis[] axes, DiagramComponent[] nonValueDisplayComponents) {
		return this.buildValueDisplayComponentsForOneDiagram(diagramData, orderInSameDiagram, axes, nonValueDisplayComponents);
	}

	@Override
	protected void configureVisibilityAndColor(DiagramValueDisplayComponent[][] dvdcArray) {
		for (int j = 0, i = 0; j < dvdcArray[i].length; j++) {
			for (;i < dvdcArray.length - 1; i++) {
				DiagramValueDisplayComponent currentDvdc = dvdcArray[i][j];
				
				float currentVal = currentDvdc.getValue();
				
				for (int k = i + 1; k < dvdcArray.length; k++) {
					DiagramValueDisplayComponent dvdcToCompareTo = dvdcArray[k][j];
					
					float nextVal = dvdcToCompareTo.getValue();
					
					if (currentVal >= nextVal) {
						currentDvdc.decrementLayer();
						dvdcToCompareTo.setColor(SettingsProvider.getMixedColor(currentDvdc.getColor(), dvdcToCompareTo.getColor()));
					} else {
						dvdcToCompareTo.decrementLayer();
						currentDvdc.setColor(SettingsProvider.getMixedColor(currentDvdc.getColor(), dvdcToCompareTo.getColor()));
					}
				}
			}
			i = 0;
		}
	}
}
