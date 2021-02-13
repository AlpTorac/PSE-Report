package gelf.view.diagrams;

import java.awt.Container;
import java.util.Collection;

import gelf.view.diagrams.type.BarChart;
import gelf.view.diagrams.type.FunctionGraph;
import gelf.view.diagrams.type.HeatMap;
import gelf.view.diagrams.type.Histogram;

public interface IDiagramWizard {
	public BarChart makeBarChart(Container container, Collection<?> data);
	
	public HeatMap makeHeatMap(Container container, Collection<?> data);
	
	public Histogram makeHistogram(Container container, Collection<?> data);
	
	public FunctionGraph makeFunctionGraph(Container container, Collection<?> data);
	
	public BarChart overlayBarCharts(BarChart[] barCharts);
	
	public FunctionGraph overlayFunctionGraphs(FunctionGraph[] functionGraphs);
	
	public Histogram overlayHistograms(Histogram[] histograms);
}
