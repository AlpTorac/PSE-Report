package gelf.view.diagrams;

import java.awt.Container;
import java.util.Collection;

public interface IDiagramWizard {
	public IDiagram makeBarChart(Container container, Collection<?> data);
	
	public IDiagram makeHeatMap(Container container, Collection<?> data);
	
	public IDiagram makeHistogram(Container container, Collection<?> data);
	
	public IDiagram makeFunctionGraph(Container container, Collection<?> data);
}
