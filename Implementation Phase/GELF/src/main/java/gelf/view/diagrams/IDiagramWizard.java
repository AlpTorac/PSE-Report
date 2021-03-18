package gelf.view.diagrams;

import java.awt.Container;
import java.util.Collection;

import gelf.view.diagrams.type.BarChart;
import gelf.view.diagrams.type.HeatMap;
import gelf.view.diagrams.type.Histogram;

/**
 * An interface implemented by facades that ease {@link IDiagram} related operations.
 * 
 * @author Alp Torac Genc
 *
 */
public interface IDiagramWizard {
	/**
	 * @param container the container, for which the diagram will be made.
	 * @param data a collection of values needed to build a bar chart.
	 * @return the built bar chart.
	 */
	public BarChart makeBarChart(Container container, Collection<?> data);
	/**
	 * @param container the container, for which the diagram will be made.
	 * @param data a collection of values needed to build a heat map.
	 * @return the built heat map.
	 */
	public HeatMap makeHeatMap(Container container, Collection<?> data);
	/**
	 * @param container the container, for which the diagram will be made.
	 * @param data a collection of values needed to build a histogram.
	 * @return the built histogram.
	 */
	public Histogram makeHistogram(Container container, Collection<?> data);
	/** Builds a bar chart and attaches it to the given container.
	 * @param container the container, to which the made diagram will be attached.
	 * @param data a collection of values needed to build a bar chart.
	 * @return the built bar chart.
	 */
	public BarChart makeAndAttachBarChart(Container container, Collection<?> data);
	/** Builds a heat map and attaches it to the given container.
	 * @param container the container, to which the made diagram will be attached.
	 * @param data a collection of values needed to build a heat map.
	 * @return the built heat map.
	 */
	public HeatMap makeAndAttachHeatMap(Container container, Collection<?> data);
	/** Builds a histogram and attaches it to the given container.
	 * @param container the container, to which the made diagram will be attached.
	 * @param data a collection of values needed to build a histogram.
	 * @return the built histogram.
	 */
	public Histogram makeAndAttachHistogram(Container container, Collection<?> data);
	/** Builds a bar chart with the given descriptions.
	 * @param container the container, for which the diagram will be made.
	 * @param data a collection of values needed to build a bar chart.
	 * @param descriptions a collection of descriptions for the bar chart to be made.
	 * @return the built bar chart.
	 */
	public BarChart makeBarChartWithDescriptions(Container container, Collection<?> data,
			Collection<?> descriptions);
	/** Builds a bar chart with the given descriptions, which will be attached to the
	 * given container.
	 * @param container the container, to which the made diagram will be attached.
	 * @param data a collection of values needed to build a bar chart.
	 * @param descriptions a collection of descriptions for the bar chart to be made.
	 * @return the built bar chart.
	 */
	public BarChart makeAndAttachBarChartWithDescriptions(Container container, Collection<?> data, Collection<?> descriptions);
	/** Overlays the given bar charts and attaches the result to the given container.
	 * @param container the container, to which the made diagram will be attached.
	 * @param barCharts bar charts to be overlaid.
	 * @return the bar chart made of overlaid bar charts.
	 */
	public BarChart overlayAndAttachBarCharts(Container container, BarChart[] barCharts);
	/** Overlays the given histograms and attaches the result to the given container.
	 * @param container the container, to which the made diagram will be attached.
	 * @param barCharts histograms to be overlaid.
	 * @return the histogram made of overlaid histograms.
	 */
	public Histogram overlayAndAttachHistograms(Container container, Histogram[] histograms);
	/** Creates a line, which shows the minimum value of the given diagram.
	 * @param diagram a given diagram.
	 * @return the said line.
	 */
	public IDiagramViewHelper addMinDisplayer(IDiagram diagram);
	/** Creates a line, which shows the maximum value of the given diagram.
	 * @param diagram a given diagram.
	 * @return the said line.
	 */
	public IDiagramViewHelper addMaxDisplayer(IDiagram diagram);
	/** Creates a line, which shows the average value of the given diagram.
	 * @param diagram a given diagram.
	 * @return the said line.
	 */
	public IDiagramViewHelper addAvgDisplayer(IDiagram diagram);
	/** Creates a line, which shows the median value of the given diagram.
	 * @param diagram a given diagram.
	 * @return the said line.
	 */
	public IDiagramViewHelper addMedDisplayer(IDiagram diagram);
}
