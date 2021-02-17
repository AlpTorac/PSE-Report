package gelf.view.diagrams;

import java.awt.Container;
import java.util.Collection;

import gelf.view.diagrams.type.BarChart;
import gelf.view.diagrams.type.FunctionGraph;
import gelf.view.diagrams.type.HeatMap;
import gelf.view.diagrams.type.Histogram;

public interface IDiagramWizard {
	public BarChart makeAndAttachBarChart(Container container, Collection<?> data);
	
	public HeatMap makeAndAttachHeatMap(Container container, Collection<?> data);
	
	public Histogram makeAndAttachHistogram(Container container, Collection<?> data);
	
	public FunctionGraph makeAndAttachFunctionGraph(Container container, Collection<?> data);
	
	public BarChart overlayAndAttachBarCharts(Container container, BarChart[] barCharts);
	
	public FunctionGraph overlayAndAttachFunctionGraphs(Container container, FunctionGraph[] functionGraphs);
	
	public Histogram overlayAndAttachHistograms(Container container, Histogram[] histograms);
	
	public void addMinDisplayer(IDiagram diagram);
	
	public void addMaxDisplayer(IDiagram diagram);
	
	public void addAvgDisplayer(IDiagram diagram);
	
	public void addMedDisplayer(IDiagram diagram);
	
	public void xAxisCoordinateDisplayer(IDiagram diagram);
	
	public void yAxisCoordinateDisplayer(IDiagram diagram);
}
