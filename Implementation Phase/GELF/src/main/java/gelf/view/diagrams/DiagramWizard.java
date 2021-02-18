package gelf.view.diagrams;

import java.awt.Container;
import java.util.Collection;

import gelf.view.diagrams.builder.BarChartBuilder;
import gelf.view.diagrams.builder.DiagramBuilder;
import gelf.view.diagrams.builder.FunctionGraphBuilder;
import gelf.view.diagrams.builder.HeatMapBuilder;
import gelf.view.diagrams.builder.HistogramBuilder;
import gelf.view.diagrams.indicator.DiagramViewHelper;
import gelf.view.diagrams.indicator.DiagramViewHelperFactory;
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
	private DiagramViewHelperFactory viewHelperFactory = DiagramViewHelperFactory.getInstance();
	
	public DiagramWizard() {
		
	}
	
	private void attachAndShowAxes(Container container, IDiagram diagram) {
		diagram.attachToContainer(container);
		diagram.showAxes();
	}
	
	@Override
	public BarChart makeAndAttachBarChart(Container container, Collection<?> data) {
		DiagramBuilder builder = new BarChartBuilder(container);
		builder.receiveDiagramData(data, 0);
		director.setBuilder(builder);
		BarChart diagram = (BarChart) director.build();
		this.attachAndShowAxes(container, diagram);
		return diagram;
	}

	@Override
	public HeatMap makeAndAttachHeatMap(Container container, Collection<?> data) {
		DiagramBuilder builder = new HeatMapBuilder(container);
		builder.receiveDiagramData(data, 2);
		director.setBuilder(builder); 
		HeatMap diagram = (HeatMap) director.build();
		this.attachAndShowAxes(container, diagram);
		return diagram;
	}

	@Override
	public Histogram makeAndAttachHistogram(Container container, Collection<?> data) {
		DiagramBuilder builder = new HistogramBuilder(container);
		builder.receiveDiagramData(data, 1);
		director.setBuilder(builder);
		Histogram diagram = (Histogram) director.build();
		this.attachAndShowAxes(container, diagram);
		return diagram;
	}

	@Override
	public FunctionGraph makeAndAttachFunctionGraph(Container container, Collection<?> data) {
		DiagramBuilder builder = new FunctionGraphBuilder(container);
		builder.receiveDiagramData(data, 1);
		director.setBuilder(builder);
		FunctionGraph diagram = (FunctionGraph) director.build();
		this.attachAndShowAxes(container, diagram);
		return diagram;
	}

	@Override
	public BarChart overlayAndAttachBarCharts(Container container, BarChart[] barCharts) {
		DiagramOverlayer overlayer = new DiagramOverlayer(barCharts);
		overlayer.setOverlayStrategy(new BarChartOverlayStrategy());
		BarChart overlaidDiagram = (BarChart) overlayer.overlay();
		this.attachAndShowAxes(container, overlaidDiagram);
		return overlaidDiagram;
	}

	@Override
	public FunctionGraph overlayAndAttachFunctionGraphs(Container container, FunctionGraph[] functionGraphs) {
		DiagramOverlayer overlayer = new DiagramOverlayer(functionGraphs);
		overlayer.setOverlayStrategy(new FunctionGraphOverlayStrategy());
		FunctionGraph overlaidDiagram = (FunctionGraph) overlayer.overlay();
		this.attachAndShowAxes(container, overlaidDiagram);
		return overlaidDiagram;
	}

	@Override
	public Histogram overlayAndAttachHistograms(Container container, Histogram[] histograms) {
		DiagramOverlayer overlayer = new DiagramOverlayer(histograms);
		overlayer.setOverlayStrategy(new HistogramOverlayStrategy());
		Histogram overlaidDiagram = (Histogram) overlayer.overlay();
		this.attachAndShowAxes(container, overlaidDiagram);
		return overlaidDiagram;
	}

	@Override
	public IDiagramViewHelper addMinDisplayer(IDiagram diagram) {
		DiagramViewHelper dvh = viewHelperFactory.createMinLineDisplayer(diagram);
		diagram.addDiagramViewHelper(dvh);
		return dvh;
	}

	@Override
	public IDiagramViewHelper addMaxDisplayer(IDiagram diagram) {
		DiagramViewHelper dvh = viewHelperFactory.createMaxLineDisplayer(diagram);
		diagram.addDiagramViewHelper(dvh);
		return dvh;
	}

	@Override
	public IDiagramViewHelper addAvgDisplayer(IDiagram diagram) {
		DiagramViewHelper dvh = viewHelperFactory.createAvgLineDisplayer(diagram);
		diagram.addDiagramViewHelper(dvh);
		return dvh;
	}

	@Override
	public IDiagramViewHelper addMedDisplayer(IDiagram diagram) {
		DiagramViewHelper dvh = viewHelperFactory.createMedLineDisplayer(diagram);
		diagram.addDiagramViewHelper(dvh);
		return dvh;
	}

	@Override
	public IDiagramViewHelper addXAxisCoordinateDisplayer(IDiagram diagram) {
		DiagramViewHelper dvh = viewHelperFactory.createXCoordinateGridDisplayer(diagram);
		diagram.addDiagramViewHelper(dvh);
		return dvh;
	}

	@Override
	public IDiagramViewHelper addYAxisCoordinateDisplayer(IDiagram diagram) {
		DiagramViewHelper dvh = viewHelperFactory.createYCoordinateGridDisplayer(diagram);
		diagram.addDiagramViewHelper(dvh);
		return dvh;
	}

	@Override
	public BarChart makeAndAttachBarChartWithDescriptions(Container container, Collection<?> data,
			Collection<?> descriptions) {
		DiagramBuilder builder = new BarChartBuilder(container);
		builder.receiveDiagramData(data, descriptions, 0);
		director.setBuilder(builder);
		BarChart diagram = (BarChart) director.build();
		this.attachAndShowAxes(container, diagram);
		return diagram;
	}
}
