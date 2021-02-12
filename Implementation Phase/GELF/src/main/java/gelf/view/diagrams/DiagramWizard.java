package gelf.view.diagrams;

import java.awt.Container;
import java.util.Collection;

import gelf.view.diagrams.builder.BarChartBuilder;
import gelf.view.diagrams.builder.DiagramBuilder;
import gelf.view.diagrams.builder.FunctionGraphBuilder;
import gelf.view.diagrams.builder.HeatMapBuilder;
import gelf.view.diagrams.builder.HistogramBuilder;

public class DiagramWizard implements IDiagramWizard {
	
	private DiagramDirector director = DiagramDirector.getDiagramDirector();
	
	public DiagramWizard() {
		
	}
	
	@Override
	public IDiagram makeBarChart(Container container, Collection<?> data) {
		DiagramBuilder builder = new BarChartBuilder(container);
		builder.receiveDiagramData(data, 0);
		director.setBuilder(builder);
		return director.build();
	}

	@Override
	public IDiagram makeHeatMap(Container container, Collection<?> data) {
		DiagramBuilder builder = new HeatMapBuilder(container);
		builder.receiveDiagramData(data, 2);
		director.setBuilder(builder);
		return director.build();
	}

	@Override
	public IDiagram makeHistogram(Container container, Collection<?> data) {
		DiagramBuilder builder = new HistogramBuilder(container);
		builder.receiveDiagramData(data, 1);
		director.setBuilder(builder);
		return director.build();
	}

	@Override
	public IDiagram makeFunctionGraph(Container container, Collection<?> data) {
		DiagramBuilder builder = new FunctionGraphBuilder(container);
		builder.receiveDiagramData(data, 1);
		director.setBuilder(builder);
		return director.build();
	}

}
