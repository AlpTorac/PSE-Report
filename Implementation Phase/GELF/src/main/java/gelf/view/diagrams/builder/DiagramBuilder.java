package gelf.view.diagrams.builder;

import java.awt.Container;
import java.util.Collection;

import gelf.view.diagrams.IDiagram;
import gelf.view.diagrams.SettingsProvider;
import gelf.view.diagrams.components.DiagramAxis;
import gelf.view.diagrams.components.DiagramComponent;
import gelf.view.diagrams.components.DiagramComponentFactory;
import gelf.view.diagrams.components.DiagramValueDisplayComponent;
import gelf.view.diagrams.data.DiagramData;

/**
 * The superclass of all the classes, which encapsulate an algorithm to build a concrete
 * {@link gelf.view.diagrams.type.Diagram Diagram}.
 * @author Alp Torac Genc
 *
 */
public abstract class DiagramBuilder implements IDiagramBuilder {
	public static final SettingsProvider settingsProvider = SettingsProvider.getInstance();
	public static final DiagramComponentFactory factory = DiagramComponentFactory.getDiagramComponentFactory();
	/**
	 * The data needed to make a diagram.
	 */
	protected DiagramData data;
	/**
	 * The container, in respect to whose dimensions the diagram will be built.
	 */
	protected Container container;
	/**
	 * Initializes the builder with the given container, whose dimensions will be
	 * used to make the bar chart.
	 * @param container the container to be filled with the built diagram.
	 */
	public DiagramBuilder(Container container) {
		this.container = container;
	}
	
	private void setDiagramData(DiagramData data) {
		this.data = data;
	}
	/**
	 * The method, which contains the constructor of the diagram to be built.
	 * @param axes axes built with {@link #buildAxes() buildAxes}
	 * @param valueDisplayComponents components built with {@link #buildValueDisplayComponents buildValueDisplayComponents}
	 * @param diagramSpecificComponent components built with {@link #buildDiagramSpecificComponent buildDiagramSpecificComponent}
	 * @return The diagram specified in {@link #data}
	 */
	protected abstract IDiagram makeDiagram(DiagramAxis[] axes, DiagramValueDisplayComponent[] valueDisplayComponents,
			DiagramComponent[] diagramSpecificComponent);
	/**
	 * @return The built diagram.
	 */
	public IDiagram buildDiagram() {
		DiagramAxis[] axes = this.buildAxes();
		DiagramComponent[] dc = this.buildDiagramSpecificComponent();
		DiagramValueDisplayComponent[] dvdc = this.buildValueDisplayComponents(axes, dc);
		return this.makeDiagram(axes, dvdc, dc);
	}
	/**
	 * @return The built diagram specific components, which are not axes and are not responsible
	 * for showing values.
	 */
	protected DiagramComponent[] buildDiagramSpecificComponent() {
		return this.buildDiagramSpecificComponentForOneDiagram(this.getDiagramData());
	}
	/**
	 * @param axes axes built with {@link #buildAxes() buildAxes}
	 * @param diagramSpecificComponent components built in {@link #buildDiagramSpecificComponent}
	 * @return The build diagram components responsible for showing values.
	 */
	protected DiagramValueDisplayComponent[] buildValueDisplayComponents(DiagramAxis[] axes,
			DiagramComponent[] diagramSpecificComponent) {
		return this.buildValueDisplayComponentsForOneDiagram(this.getDiagramData(),
				axes, diagramSpecificComponent);
	}
	/**
	 * The method called to make and set {@link #data}.
	 * @param data a collection of values and indices needed to build the diagram
	 * @param numberOfIndices {@link gelf.view.diagrams.data.DiagramData#numberOfIndices numberOfIndices}
	 * @see {@link gelf.view.diagrams.data.DiagramData DiagramData}
	 */
	public void receiveDiagramData(Collection<?> data, int numberOfIndices) {
		DiagramData diagramData = new DiagramData(data, numberOfIndices);
		this.setDiagramData(diagramData);
	}
	/**
	 * The method called to make and set {@link #data}.
	 * @param data a collection of values and indices needed to build the diagram
	 * @param descriptions a collection of descriptions belonging to the parameter above.
	 * @param numberOfIndices {@link gelf.view.diagrams.data.DiagramData#numberOfIndices numberOfIndices}
	 * @see {@link gelf.view.diagrams.data.DiagramData DiagramData}
	 */
	public void receiveDiagramData(Collection<?> data, Collection<?> descriptions, int numberOfIndices) {
		DiagramData diagramData = new DiagramData(data, descriptions, numberOfIndices);
		this.setDiagramData(diagramData);
	}
	protected DiagramData getDiagramData() {
		return this.data;
	}
	@Override
	public Container getContainer() {
		return this.container;
	}
}
