package gelf.view.diagrams;

import java.awt.Container;
import java.util.Collection;

import gelf.view.diagrams.builder.BarChartBuilder;
import gelf.view.diagrams.builder.DiagramBuilder;
import gelf.view.diagrams.builder.FunctionGraphBuilder;
import gelf.view.diagrams.builder.HeatMapBuilder;
import gelf.view.diagrams.builder.HistogramBuilder;
import gelf.view.diagrams.overlayer.BarChartOverlayStrategy;
import gelf.view.diagrams.overlayer.DiagramOverlayer;
import gelf.view.diagrams.overlayer.FunctionGraphOverlayStrategy;
import gelf.view.diagrams.overlayer.HistogramOverlayStrategy;
import gelf.view.diagrams.type.BarChart;
import gelf.view.diagrams.type.FunctionGraph;
import gelf.view.diagrams.type.HeatMap;
import gelf.view.diagrams.type.Histogram;

public class DiagramWizard implements IDiagramWizard {
	
	private DiagramDirector director = DiagramDirector.getDiagramDirector();
	
	public DiagramWizard() {
		
	}
	
	@Override
	public BarChart makeBarChart(Container container, Collection<?> data) {
		DiagramBuilder builder = new BarChartBuilder(container);
		builder.receiveDiagramData(data, 0);
		director.setBuilder(builder);
		return (BarChart) director.build();
	}

	@Override
	public HeatMap makeHeatMap(Container container, Collection<?> data) {
		DiagramBuilder builder = new HeatMapBuilder(container);
		builder.receiveDiagramData(data, 2);
		director.setBuilder(builder);
		return (HeatMap) director.build();
	}

	@Override
	public Histogram makeHistogram(Container container, Collection<?> data) {
		DiagramBuilder builder = new HistogramBuilder(container);
		builder.receiveDiagramData(data, 1);
		director.setBuilder(builder);
		return (Histogram) director.build();
	}

	@Override
	public FunctionGraph makeFunctionGraph(Container container, Collection<?> data) {
		DiagramBuilder builder = new FunctionGraphBuilder(container);
		builder.receiveDiagramData(data, 1);
		director.setBuilder(builder);
		return (FunctionGraph) director.build();
	}

	@Override
	public BarChart overlayBarCharts(BarChart[] barCharts) {
		DiagramOverlayer overlayer = new DiagramOverlayer(barCharts);
		overlayer.setOverlayStrategy(new BarChartOverlayStrategy());
		return (BarChart) overlayer.overlay();
	}

	@Override
	public FunctionGraph overlayFunctionGraphs(FunctionGraph[] functionGraphs) {
		DiagramOverlayer overlayer = new DiagramOverlayer(functionGraphs);
		overlayer.setOverlayStrategy(new FunctionGraphOverlayStrategy());
		return (FunctionGraph) overlayer.overlay();
	}

	@Override
	public Histogram overlayHistograms(Histogram[] histograms) {
		DiagramOverlayer overlayer = new DiagramOverlayer(histograms);
		overlayer.setOverlayStrategy(new HistogramOverlayStrategy());
		return (Histogram) overlayer.overlay();
	}
}
