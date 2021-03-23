package gelf.view.diagrams;

import java.awt.Component;
import java.awt.Container;
import java.util.Collection;

import gelf.view.diagrams.components.DiagramAxis;
import gelf.view.diagrams.components.DiagramComponent;
import gelf.view.diagrams.components.DiagramValueDisplayComponent;
import gelf.view.diagrams.data.DiagramData;
import gelf.view.diagrams.indicator.DiagramViewHelper;
import gelf.view.diagrams.indicator.IndicatorIdentifier;

/**
 * An Interface implemented by diagrams.
 * @author Alp Torac Genc
 *
 */
public interface IDiagram {
	/** Clones the values in the data stored within the implementing class.
	 * @return a deep copy of the said data within the implementing class.
	 */
	public Collection<?> cloneData();
	/** Clones the descriptions in the data stored within the implementing class.
	 * @return a deep copy of the said data within the implementing class.
	 */
	public Collection<?> cloneDescriptions();
	/** Clones the entire data stored within the implementing class.
	 * @return a deep copy of the data inside the implementing class.
	 */
	public DiagramData cloneDiagramData();
	/**
	 * Refreshes the visual parts of the implementing class.
	 */
	public void refresh();
	/**
	 * Adds a given component to the implementing class.
	 * @param visualElement a given component.
	 * @param layer how deep the component's visual will be. Must be a number {@code >= 0}.
	 * Components with a lower layer value will be visible, when overlapped by components with
	 * a higher layer value.
	 */
	public void addComponent(Component visualElement, int layer);
	/**
	 * Removes the given component from the implementing class.
	 * @param visualElement a given component that has been added to the implementing class.
	 */
	public void removeComponent(Component visualElement);
	/**
	 * Adds the given view helper to the implementing class.
	 * @param dvh a given view helper.
	 * @return True, if the given view helper has been added successfully. Otherwise, false.
	 */
	public boolean addDiagramViewHelper(DiagramViewHelper dvh);
	/**
	 * Removes the given {@link DiagramViewHelper} from the implementing class.
	 * @param id the unique identifier of the view helper to be removed.
	 * @return True, if the given view helper has been removed successfully. Otherwise, false.
	 */
	public boolean removeDiagramViewHelper(IndicatorIdentifier id);
	/**
	 * Shows the visuals of the specified {@link DiagramViewHelper}.
	 * @param id the unique identifier of the view helper.
	 */
	public void showDiagramViewHelper(IndicatorIdentifier id);
	/**
	 * Hides the visuals of the specified {@link DiagramViewHelper}.
	 * @param id the unique identifier of the view helper.
	 */
	public void hideDiagramViewHelper(IndicatorIdentifier id);
	/**
	 * Note: Methods connected to {@link gelf.view.diagrams.components.DiagramComponent#diagram diagram}
	 * will not work for the prototypes till they are attached to a diagram.
	 * @return An array of deep copied components of the implementing class, which
	 * are not the {@link DiagramAxis} and not responsible for displaying values.
	 */
	public DiagramComponent[] getNonValueDisplayDiagramComponentPrototype();
	/**
	 * Note: Methods connected to {@link gelf.view.diagrams.components.DiagramComponent#diagram diagram}
	 * will not work for the prototypes till they are attached to a diagram.
	 * @return An array of deep copied components of the implementing class, which
	 * are responsible for displaying values and not {@link DiagramAxis}.
	 */
	public DiagramValueDisplayComponent[] getDiagramValueDisplayComponentPrototypes();
	/**
	 * Note: Methods connected to {@link gelf.view.diagrams.components.DiagramComponent#diagram diagram}
	 * will not work for the prototypes till they are attached to a diagram.
	 * @return An array of deep copied {@link DiagramAxis} of the implementing class.
	 */
	public DiagramAxis[] getDiagramAxisPrototypes();
	/**
	 * @return The component, which contains the visuals of the implementing class.
	 */
	public Component getContainingElement();
	/**
	 * Shows the visuals of the {@link DiagramAxis} of the implementing class.
	 */
	public void showAxes();
	/**
	 * Hides the visuals of the {@link DiagramAxis} of the implementing class.
	 */
	public void hideAxes();
	/**
	 * Shows the visuals of the implementing class.
	 */
	public default void show() {
		this.getContainingElement().setVisible(true);
	}
	/**
	 * Hides the visuals of the implementing class.
	 */
	public default void hide() {
		this.getContainingElement().setVisible(false);
	}
	/**
	 * Attaches the implementing class' visuals to the given container.
	 * @param container a given container.
	 */
	public default void attachToContainer(Container container) {
		container.add(this.getContainingElement());
		this.getContainingElement().setBounds(0, 0, container.getWidth(), container.getHeight());
		this.getContainingElement().repaint();
	}
	/**
	 * Removes the implementing class' visuals from the given container.
	 * @param container a given container, which has the visuals of the implementing class.
	 */
	public default void removeFromContainer() {
		Container container = this.getContainingElement().getParent();
		container.remove(this.getContainingElement());
		container.repaint();
	}
	/**
	 * @param dvdc a given component stored in the implementing class, which is responsible
	 * for displaying a value and is not {@link DiagramAxis}.
	 * @return An array of the indices of the value displayed by the component above.
	 */
	public int[] getIndexPositionsOfComponent(DiagramValueDisplayComponent dvdc);
}
