package gelf.view.diagrams;

import java.awt.Container;
import java.util.Collection;

import gelf.view.diagrams.builder.BarChartBuilder;
import gelf.view.diagrams.builder.DiagramBuilder;
import gelf.view.diagrams.builder.HeatMapBuilder;
import gelf.view.diagrams.builder.HistogramBuilder;
import gelf.view.diagrams.indicator.DiagramViewHelper;
import gelf.view.diagrams.indicator.DiagramViewHelperFactory;
import gelf.view.diagrams.overlayer.BarChartOverlayStrategy;
import gelf.view.diagrams.overlayer.DiagramOverlayer;
import gelf.view.diagrams.overlayer.HistogramOverlayStrategy;
import gelf.view.diagrams.type.BarChart;
import gelf.view.diagrams.type.HeatMap;
import gelf.view.diagrams.type.Histogram;

/**
 * The class that acts as a facade for creating {@link Diagram} instances.
 * 
 * @author Alp Torac Genc
 *
 */
public class DiagramWizard implements IDiagramWizard {
	/**
	 * {@link DiagramDirector#instance}
	 */
	private DiagramDirector director = DiagramDirector.getDiagramDirector();
	/**
	 * {@link DiagramViewHelperFactory#instance}
	 */
	private DiagramViewHelperFactory viewHelperFactory = DiagramViewHelperFactory.getInstance();
	
	public DiagramWizard() {
		
	}
	/**Attaches the given diagram to the given container.
	 * 
	 * @param container the container, to which the given diagram will be attached.
	 * @param diagram a given diagram.
	 */
	private void attachAndShowAxes(Container container, IDiagram diagram) {
		diagram.attachToContainer(container);
		diagram.showAxes();
	}
	
	/**
	 * @param container the container, for which the diagram will be made.
	 * @param data a collection of values needed to build a bar chart.
	 * @return the built bar chart.
	 */
	@Override
	public BarChart makeBarChart(Container container, Collection<?> data) {
		DiagramBuilder builder = new BarChartBuilder(container);
		builder.receiveDiagramData(data, 0);
		director.setBuilder(builder);
		BarChart diagram = (BarChart) director.build();
		return diagram;
	}

	/**
	 * @param container the container, for which the diagram will be made.
	 * @param data a collection of values needed to build a heat map.
	 * @return the built heat map.
	 */
	@Override
	public HeatMap makeHeatMap(Container container, Collection<?> data) {
		DiagramBuilder builder = new HeatMapBuilder(container);
		builder.receiveDiagramData(data, 2);
		director.setBuilder(builder); 
		HeatMap diagram = (HeatMap) director.build();
		return diagram;
	}

	/**
	 * @param container the container, for which the diagram will be made.
	 * @param data a collection of values needed to build a histogram.
	 * @return the built histogram.
	 */
	@Override
	public Histogram makeHistogram(Container container, Collection<?> data) {
		DiagramBuilder builder = new HistogramBuilder(container);
		builder.receiveDiagramData(data, 1);
		director.setBuilder(builder);
		Histogram diagram = (Histogram) director.build();
		return diagram;
	}
	
	/** Builds a bar chart and attaches it to the given container.
	 * @param container the container, to which the made diagram will be attached.
	 * @param data a collection of values needed to build a bar chart.
	 * @return the built bar chart.
	 */
	@Override
	public BarChart makeAndAttachBarChart(Container container, Collection<?> data) {
		BarChart diagram = this.makeBarChart(container, data);
		this.attachAndShowAxes(container, diagram);
		return diagram;
	}

	/** Builds a heat map and attaches it to the given container.
	 * @param container the container, to which the made diagram will be attached.
	 * @param data a collection of values needed to build a heat map.
	 * @return the built heat map.
	 */
	@Override
	public HeatMap makeAndAttachHeatMap(Container container, Collection<?> data) {
		HeatMap diagram = this.makeHeatMap(container, data);
		this.attachAndShowAxes(container, diagram);
		return diagram;
	}

	/** Builds a histogram and attaches it to the given container.
	 * @param container the container, to which the made diagram will be attached.
	 * @param data a collection of values needed to build a histogram.
	 * @return the built histogram.
	 */
	@Override
	public Histogram makeAndAttachHistogram(Container container, Collection<?> data) {
		Histogram diagram = this.makeHistogram(container, data);
		this.attachAndShowAxes(container, diagram);
		return diagram;
	}

	/** Overlays the given bar charts and attaches the result to the given container.
	 * @param container the container, to which the made diagram will be attached.
	 * @param barCharts bar charts to be overlaid.
	 * @return the bar chart made of overlaid bar charts.
	 */
	@Override
	public BarChart overlayAndAttachBarCharts(Container container, BarChart[] barCharts) {
		DiagramOverlayer overlayer = new DiagramOverlayer(barCharts);
		overlayer.setOverlayStrategy(new BarChartOverlayStrategy(container, barCharts));
		BarChart overlaidDiagram = (BarChart) overlayer.overlay();
		this.attachAndShowAxes(container, overlaidDiagram);
		return overlaidDiagram;
	}

	/** Overlays the given histograms and attaches the result to the given container.
	 * @param container the container, to which the made diagram will be attached.
	 * @param barCharts histograms to be overlaid.
	 * @return the histogram made of overlaid histograms.
	 */
	@Override
	public Histogram overlayAndAttachHistograms(Container container, Histogram[] histograms) {
		DiagramOverlayer overlayer = new DiagramOverlayer(histograms);
		overlayer.setOverlayStrategy(new HistogramOverlayStrategy(container, histograms));
		Histogram overlaidDiagram = (Histogram) overlayer.overlay();
		this.attachAndShowAxes(container, overlaidDiagram);
		return overlaidDiagram;
	}

	/** Creates a line, which shows the minimum value of the given diagram.
	 * @param diagram a given diagram.
	 * @return the said line.
	 */
	@Override
	public IDiagramViewHelper addMinDisplayer(IDiagram diagram) {
		DiagramViewHelper dvh = viewHelperFactory.createMinLineDisplayer(diagram);
		diagram.addDiagramViewHelper(dvh);
		return dvh;
	}

	/** Creates a line, which shows the maximum value of the given diagram.
	 * @param diagram a given diagram.
	 * @return the said line.
	 */
	@Override
	public IDiagramViewHelper addMaxDisplayer(IDiagram diagram) {
		DiagramViewHelper dvh = viewHelperFactory.createMaxLineDisplayer(diagram);
		diagram.addDiagramViewHelper(dvh);
		return dvh;
	}

	/** Creates a line, which shows the average value of the given diagram.
	 * @param diagram a given diagram.
	 * @return the said line.
	 */
	@Override
	public IDiagramViewHelper addAvgDisplayer(IDiagram diagram) {
		DiagramViewHelper dvh = viewHelperFactory.createAvgLineDisplayer(diagram);
		diagram.addDiagramViewHelper(dvh);
		return dvh;
	}

	/** Creates a line, which shows the median value of the given diagram.
	 * @param diagram a given diagram.
	 * @return the said line.
	 */
	@Override
	public IDiagramViewHelper addMedDisplayer(IDiagram diagram) {
		DiagramViewHelper dvh = viewHelperFactory.createMedLineDisplayer(diagram);
		diagram.addDiagramViewHelper(dvh);
		return dvh;
	}

	/** Builds a bar chart with the given descriptions.
	 * @param container the container, for which the diagram will be made.
	 * @param data a collection of values needed to build a bar chart.
	 * @param descriptions a collection of descriptions for the bar chart to be made.
	 * @return the built bar chart.
	 */
	@Override
	public BarChart makeBarChartWithDescriptions(Container container, Collection<?> data,
			Collection<?> descriptions) {
		DiagramBuilder builder = new BarChartBuilder(container);
		builder.receiveDiagramData(data, descriptions, 0);
		director.setBuilder(builder);
		BarChart diagram = (BarChart) director.build();
		return diagram;
	}
	
	/** Builds a bar chart with the given descriptions, which will be attached to the
	 * given container.
	 * @param container the container, to which the made diagram will be attached.
	 * @param data a collection of values needed to build a bar chart.
	 * @param descriptions a collection of descriptions for the bar chart to be made.
	 * @return the built bar chart.
	 */
	@Override
	public BarChart makeAndAttachBarChartWithDescriptions(Container container, Collection<?> data,
			Collection<?> descriptions) {
		BarChart diagram = this.makeBarChartWithDescriptions(container, data, descriptions);
		this.attachAndShowAxes(container, diagram);
		return diagram;
	}
}
